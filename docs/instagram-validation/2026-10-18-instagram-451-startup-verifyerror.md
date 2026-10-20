# Instagram 451 启动 VerifyError

用户设备为小米、Android 16 / SDK 36。原版目标为 `com.instagram.android` 451.0.0.53.80 / 385713073，补丁包为 `3.10.0-custom-newx.13`。应用启动后退出，异常被已有崩溃处理器保存。日志关键内容：

```text
java.lang.VerifyError: Verifier rejected class X.04a3:
java.lang.Object X.04a3.unsafeParseFromJson(X.04s3):
[0x146] invalid use of move-result-object as branch target at 0x14b
```

`.13` 的精确补丁源码为 `fff1e274a6f322bc98141aab6dcfc2ec844de494`，MPP SHA-256 为 `722918b02c062f7cc2f78069a05e043ef01e5f686481209d957d276a7df283ae`。对已保存的同份修补输出执行 `InstagramVerifierAudit`，在 831824 个方法中发现一处问题，与设备日志的方法及两个指令地址完全相同。原版 451 安装包的同项审计零错误。复现明细见 [`.13` 反例报告](instagram-451-crash-13-verifier.json)。

根因是 `FilterStoriesPatch`：`stringMatches[0]` 并不保证指向预期的业务字段，向前取最近的 `IF_EQZ` 选中了 `share_to_friends_story_pending_media` 字段名比较的整数结果。过滤调用因此接收错误类型；原来的 `ExternalLabel` 又以附近的 `getInstruction(index + 2)` 为目标，恰好指向插入后的结果读取指令。修补和 APK 重建能成功，但 Android 在加载 JSON 解析器时拒绝该方法，首屏网络解析就可能触发。

修复以 `tray` 字段、原生当前 token 与数组 token 的比较、ArrayList 分配、解析返回对象、空值检查、列表添加和同一个循环继续目标共同定位。字段不匹配可能向后跳至公共字段循环，因此数组边界取自原生数组检查，不能假设字段 guard 的目标在当前代码之后。

使用类型化插入，在原生对象空值检查前增加“空对象直接跳回原检查、非空对象过滤、读取对象结果”。保留原检查及其原生循环继续目标，并明确迁移进入该检查的标签。没有新的外部数字偏移或跳入结果读取的标签。回归用例覆盖字段顺序和向后分派、对象来源、空值路径、过滤后的跳过路径、结果指令跳转，以及错误类型和歧义循环拒绝。

`.13` 的 60 项修补成功与原有 248 项静态检查不足以发现本次运行时校验错误；新增整包审计检查普通分支、switch 目标及结果读取的相邻调用。修正版的确切源码、构建、补丁包散列、测试和最终检查以[修复验证记录](validated-instagram-451-crash-fix-2026-10-18.json)为准。

按用户要求，后续修补验证仅针对 451。没有安装、启动或控制手机；实际重新启动由用户复验。完整 Android 类型校验和设备运行并不能由离线分支审计替代。
