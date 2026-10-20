# 测试、验证与历史说明

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 242 个文件。

回归测试、安装包修补结果、失败日志、来源校验和发布说明；历史记录不等于当前版本的兼容承诺。

## 仓库根目录

| 文件名 | 作用 |
| --- | --- |
| [baseline_patch_times.txt](../../baseline_patch_times.txt) | 记录历史 X-Lite 12.17.3 alpha 补丁的耗时基线 |

## `docs`

| 文件名 | 作用 |
| --- | --- |
| [newx-12.34-coverage.md](../newx-12.34-coverage.md) | 对照旧版 73 项功能，列出新版迁移对应项与缺失项 |
| [newx-fixed-bar-and-auto-refresh.md](../newx-fixed-bar-and-auto-refresh.md) | 说明固定底栏、禁止自动刷新及手动刷新保留范围 |
| [newx-floating-glass-navbar.md](../newx-floating-glass-navbar.md) | 说明悬浮玻璃底栏的设计、开关和原生接入契约 |
| [newx-profile-bulk-download.md](../newx-profile-bulk-download.md) | 说明主页全部媒体下载、分页、取消和限制 |
| [newx-unified-download-banner.md](../newx-unified-download-banner.md) | 说明顶部完整进度、结果排队及多任务通知规则 |

## `docs/file-guide`

| 文件名 | 作用 |
| --- | --- |
| [legacy-instagram.md](legacy-instagram.md) | 中文文件用途分表：Instagram 代码 |
| [legacy-twitter.md](legacy-twitter.md) | 中文文件用途分表：旧版 Twitter / X 代码 |
| [newx-patches.md](newx-patches.md) | 中文文件用途分表：新版 X 补丁入口 |
| [newx-runtime.md](newx-runtime.md) | 中文文件用途分表：新版 X 运行时代码 |
| [project.md](project.md) | 中文文件用途分表：项目配置与维护 |
| [resources-assets.md](resources-assets.md) | 中文文件用途分表：图标、图片与布局资源 |
| [resources-strings.md](resources-strings.md) | 中文文件用途分表：文字、翻译与选项资源 |
| [shared.md](shared.md) | 中文文件用途分表：共享代码与工具 |
| [stubs.md](stubs.md) | 中文文件用途分表：编译期类型占位 |
| [validation.md](validation.md) | 中文文件用途分表：测试、验证与历史说明 |

## `docs/instagram-validation`

| 文件名 | 作用 |
| --- | --- |
| [2026-10-18-instagram-451-startup-verifyerror.md](../instagram-validation/2026-10-18-instagram-451-startup-verifyerror.md) | 记录451启动崩溃的设备异常、精确反例、根因、指令修复和实机复验边界 |
| [2026-10-19-instagram-label-mismatch.md](../instagram-validation/2026-10-19-instagram-label-mismatch.md) | 记录中文按钮错配、宿主语言包资源边界、恢复.14读取方式和实机复验范围 |
| [InstagramDownloadStaticReview.java](../instagram-validation/InstagramDownloadStaticReview.java) | 离线检查已修补 Instagram APK 的模型直接桥接、设置开关、主页分页、串行下载、顶部卡片与资源，不注入应用 |
| [InstagramVerifierAudit.java](../instagram-validation/InstagramVerifierAudit.java) | 扫描APK全部普通分支、switch目标和结果读取，捕获启动VerifyError涉及的非法跳转 |
| [README.md](../instagram-validation/README.md) | 说明 Instagram 主页下载的最新验证记录、历史基线、功能边界和手机试用范围 |
| [check-ig-string-context.py](../instagram-validation/check-ig-string-context.py) | 编译真实IgStr，验证443项中文绑定绕过宿主下载语言表，覆盖.15标签错配反例 |
| [instagram-439-upgrade-static.json](../instagram-validation/instagram-439-upgrade-static.json) | 记录439回归APK的模型桥接、设置开关、主页下载、顶部进度、暂停和冷却检查 |
| [instagram-451-contract-notes.md](../instagram-validation/instagram-451-contract-notes.md) | 说明439与451的原生类型、调用、持有者、故事调度及验证约束 |
| [instagram-451-crash-13-verifier.json](../instagram-validation/instagram-451-crash-13-verifier.json) | 保留.13无效跳转的失败反例，与手机VerifyError方法及地址对应 |
| [instagram-451-crash-fix-static.json](../instagram-validation/instagram-451-crash-fix-static.json) | 记录.14的快拍筛选对象流、主页下载和界面资源最终检查 |
| [instagram-451-crash-fix-verifier.json](../instagram-validation/instagram-451-crash-fix-verifier.json) | 记录.14全包分支目标和结果读取的结构审计结果 |
| [instagram-451-footer-locale-static.json](../instagram-validation/instagram-451-footer-locale-static.json) | 记录.15下载按钮插入顺序、点击桥接、全部中文资源与原有下载功能的最终检查 |
| [instagram-451-footer-locale-verifier.json](../instagram-validation/instagram-451-footer-locale-verifier.json) | 记录.15全包分支目标及结果读取的结构审计 |
| [instagram-451-label-fix-static.json](../instagram-validation/instagram-451-label-fix-static.json) | 记录.16资源读取边界、全部中文资源和左侧下载按钮最终检查 |
| [instagram-451-label-fix-verifier.json](../instagram-validation/instagram-451-label-fix-verifier.json) | 记录.16全包分支与结果读取结构检查 |
| [instagram-451-upgrade-static.json](../instagram-validation/instagram-451-upgrade-static.json) | 记录451最终APK的模型桥接、设置开关、主页下载、顶部进度、暂停和冷却检查 |
| [instagram-static-pause-fix.json](../instagram-validation/instagram-static-pause-fix.json) | 记录本轮 Instagram 最终 APK 的109项检查，包括分页冷却、准备暂停及晚到进度终态保护 |
| [instagram-static.json](../instagram-validation/instagram-static.json) | 记录上一轮 Instagram 最终 APK 的主页下载、串行下载、顶部进度及资源静态检查明细 |
| [validated-build-2026-10-18-pause-fix.json](../instagram-validation/validated-build-2026-10-18-pause-fix.json) | 记录本轮 Instagram .12 源码、373项测试、MPP与原版安装包散列、60项修补重建及109项检查结果 |
| [validated-build-2026-10-18.json](../instagram-validation/validated-build-2026-10-18.json) | 记录 Instagram 与 X 上一轮 combined 构建的确切源码、CI、MPP 与安装包散列、修补结果及检查统计 |
| [validated-instagram-451-crash-fix-2026-10-18.json](../instagram-validation/validated-instagram-451-crash-fix-2026-10-18.json) | 记录451启动闪退修复的源码、CI、MPP散列、补丁重建、测试及设备复验边界 |
| [validated-instagram-451-footer-locale-2026-10-18.json](../instagram-validation/validated-instagram-451-footer-locale-2026-10-18.json) | 记录.15的源码、构建工件、测试与451修补验证，并说明首次切中文退出尚未确诊 |
| [validated-instagram-451-label-fix-2026-10-19.json](../instagram-validation/validated-instagram-451-label-fix-2026-10-19.json) | 记录.16中文错配修复构建、来源、回归反例、451修补结果与实机边界 |
| [validated-instagram-upgrade-2026-10-18.json](../instagram-validation/validated-instagram-upgrade-2026-10-18.json) | 记录 Instagram .13 的源码、CI、测试、同一 MPP 对451与439的修补重建结果及使用限制 |
| [x-all-banner.json](../instagram-validation/x-all-banner.json) | 记录上一轮 combined 构建中 X 全选 APK 的完整下载通知与任务队列静态检查明细 |
| [x-all-bulk.json](../instagram-validation/x-all-bulk.json) | 记录上一轮 combined 构建中 X 全选 APK 的主页媒体下载与分页静态检查明细 |
| [x-all-fixed.json](../instagram-validation/x-all-fixed.json) | 记录上一轮 combined 构建中 X 全选 APK 的固定底栏和禁止自动刷新静态检查明细 |
| [x-all-glass.json](../instagram-validation/x-all-glass.json) | 记录上一轮 combined 构建中 X 全选 APK 的悬浮玻璃底栏静态检查明细 |

## `docs/newx-resolver-linter`

| 文件名 | 作用 |
| --- | --- |
| [2026-10-16-pinned-download-progress.md](../newx-resolver-linter/2026-10-16-pinned-download-progress.md) | 固定顶部进度和独立任务通知的实现、故障分析或验证记录 |
| [2026-10-17-full-download-banner.md](../newx-resolver-linter/2026-10-17-full-download-banner.md) | 完整下载信息与原生进度条的实现、故障分析或验证记录 |
| [BulkFixStaticReview.java](../newx-resolver-linter/BulkFixStaticReview.java) | 检查主页批量下载及中文资源的离线字节码验证程序，不注入应用 |
| [FixedRefreshStaticReview.java](../newx-resolver-linter/FixedRefreshStaticReview.java) | 检查固定底栏与自动刷新控制流的离线字节码验证程序，不注入应用 |
| [FullBannerStaticReview.java](../newx-resolver-linter/FullBannerStaticReview.java) | 检查完整文字、原生进度条及通知状态的离线字节码验证程序，不注入应用 |
| [GlassLabelRelocationRepro.java](../newx-resolver-linter/GlassLabelRelocationRepro.java) | 单独复现玻璃底栏注入的标签重定位问题的离线字节码验证程序，不注入应用 |
| [GlassNavStaticReview.java](../newx-resolver-linter/GlassNavStaticReview.java) | 检查悬浮玻璃底栏原生调用的离线字节码验证程序，不注入应用 |
| [PinnedBannerStaticReview.java](../newx-resolver-linter/PinnedBannerStaticReview.java) | 检查固定顶部进度及任务队列的离线字节码验证程序，不注入应用 |
| [UnifiedBannerStaticReview.java](../newx-resolver-linter/UnifiedBannerStaticReview.java) | 检查统一原生下载通知的离线字节码验证程序，不注入应用 |
| [glass-label-isolated.txt](../newx-resolver-linter/glass-label-isolated.txt) | 悬浮玻璃底栏的诊断输出 |
| [x12.34-author-all.json](../newx-resolver-linter/x12.34-author-all.json) | 按作者建立下载子文件夹（全选方案）的修补结果或验证数据 |
| [x12.34-author-default.json](../newx-resolver-linter/x12.34-author-default.json) | 按作者建立下载子文件夹（默认方案）的修补结果或验证数据 |
| [x12.34-author-folders-2026-10-15.md](../newx-resolver-linter/x12.34-author-folders-2026-10-15.md) | 按作者建立下载子文件夹的实现、故障分析或验证记录 |
| [x12.34-author-static-review.json](../newx-resolver-linter/x12.34-author-static-review.json) | 按作者建立下载子文件夹的静态检查结果 |
| [x12.34-auto-refresh-lint-2026-10-16.md](../newx-resolver-linter/x12.34-auto-refresh-lint-2026-10-16.md) | 禁止自动刷新的实现、故障分析或验证记录 |
| [x12.34-auto-refresh-register-2026-10-16.md](../newx-resolver-linter/x12.34-auto-refresh-register-2026-10-16.md) | 禁止自动刷新的实现、故障分析或验证记录 |
| [x12.34-auto-refresh-register-failure.json](../newx-resolver-linter/x12.34-auto-refresh-register-failure.json) | 禁止自动刷新的失败信息记录 |
| [x12.34-auto-refresh-register-failure.log](../newx-resolver-linter/x12.34-auto-refresh-register-failure.log) | 禁止自动刷新的失败修补日志 |
| [x12.34-banner-all-fixed-review.json](../newx-resolver-linter/x12.34-banner-all-fixed-review.json) | 统一顶部下载通知（全选方案）的静态检查结果 |
| [x12.34-banner-all-glass-review.json](../newx-resolver-linter/x12.34-banner-all-glass-review.json) | 统一顶部下载通知（全选方案）的静态检查结果 |
| [x12.34-banner-all-regression-review.json](../newx-resolver-linter/x12.34-banner-all-regression-review.json) | 统一顶部下载通知（全选方案）的静态检查结果 |
| [x12.34-banner-all-static-review.json](../newx-resolver-linter/x12.34-banner-all-static-review.json) | 统一顶部下载通知（全选方案）的静态检查结果 |
| [x12.34-banner-all.json](../newx-resolver-linter/x12.34-banner-all.json) | 统一顶部下载通知（全选方案）的修补结果或验证数据 |
| [x12.34-banner-all.log](../newx-resolver-linter/x12.34-banner-all.log) | 统一顶部下载通知（全选方案）的修补执行日志 |
| [x12.34-banner-default-fixed-review.json](../newx-resolver-linter/x12.34-banner-default-fixed-review.json) | 统一顶部下载通知（默认方案）的静态检查结果 |
| [x12.34-banner-default-glass-review.json](../newx-resolver-linter/x12.34-banner-default-glass-review.json) | 统一顶部下载通知（默认方案）的静态检查结果 |
| [x12.34-banner-default-regression-review.json](../newx-resolver-linter/x12.34-banner-default-regression-review.json) | 统一顶部下载通知（默认方案）的静态检查结果 |
| [x12.34-banner-default-static-review.json](../newx-resolver-linter/x12.34-banner-default-static-review.json) | 统一顶部下载通知（默认方案）的静态检查结果 |
| [x12.34-banner-default.json](../newx-resolver-linter/x12.34-banner-default.json) | 统一顶部下载通知（默认方案）的修补结果或验证数据 |
| [x12.34-banner-default.log](../newx-resolver-linter/x12.34-banner-default.log) | 统一顶部下载通知（默认方案）的修补执行日志 |
| [x12.34-banner-test-counts.json](../newx-resolver-linter/x12.34-banner-test-counts.json) | 统一顶部下载通知的测试统计记录 |
| [x12.34-baseline-errors.json](../newx-resolver-linter/x12.34-baseline-errors.json) | 初始适配基线的失败信息记录 |
| [x12.34-control-flow-errors.json](../newx-resolver-linter/x12.34-control-flow-errors.json) | 字节码控制流检查的失败信息记录 |
| [x12.34-download-success-toast-2026-10-16.md](../newx-resolver-linter/x12.34-download-success-toast-2026-10-16.md) | 保存成功提示去重的实现、故障分析或验证记录 |
| [x12.34-fixed-all-glass.json](../newx-resolver-linter/x12.34-fixed-all-glass.json) | 固定底栏与自动刷新（全选方案）的修补结果或验证数据 |
| [x12.34-fixed-all-regression.json](../newx-resolver-linter/x12.34-fixed-all-regression.json) | 固定底栏与自动刷新（全选方案）的静态检查结果 |
| [x12.34-fixed-all-review.json](../newx-resolver-linter/x12.34-fixed-all-review.json) | 固定底栏与自动刷新（全选方案）的静态检查结果 |
| [x12.34-fixed-all.json](../newx-resolver-linter/x12.34-fixed-all.json) | 固定底栏与自动刷新（全选方案）的修补结果或验证数据 |
| [x12.34-fixed-all.log](../newx-resolver-linter/x12.34-fixed-all.log) | 固定底栏与自动刷新（全选方案）的修补执行日志 |
| [x12.34-fixed-auto-refresh-2026-10-16.md](../newx-resolver-linter/x12.34-fixed-auto-refresh-2026-10-16.md) | 禁止自动刷新的实现、故障分析或验证记录 |
| [x12.34-fixed-bar-descriptor-2026-10-16.md](../newx-resolver-linter/x12.34-fixed-bar-descriptor-2026-10-16.md) | 固定底栏与自动刷新的实现、故障分析或验证记录 |
| [x12.34-fixed-default-glass.json](../newx-resolver-linter/x12.34-fixed-default-glass.json) | 固定底栏与自动刷新（默认方案）的修补结果或验证数据 |
| [x12.34-fixed-default-regression.json](../newx-resolver-linter/x12.34-fixed-default-regression.json) | 固定底栏与自动刷新（默认方案）的静态检查结果 |
| [x12.34-fixed-default-review.json](../newx-resolver-linter/x12.34-fixed-default-review.json) | 固定底栏与自动刷新（默认方案）的静态检查结果 |
| [x12.34-fixed-default.json](../newx-resolver-linter/x12.34-fixed-default.json) | 固定底栏与自动刷新（默认方案）的修补结果或验证数据 |
| [x12.34-fixed-default.log](../newx-resolver-linter/x12.34-fixed-default.log) | 固定底栏与自动刷新（默认方案）的修补执行日志 |
| [x12.34-fixed-test-counts.json](../newx-resolver-linter/x12.34-fixed-test-counts.json) | 固定底栏与自动刷新的测试统计记录 |
| [x12.34-floating-glass-2026-10-16.md](../newx-resolver-linter/x12.34-floating-glass-2026-10-16.md) | 悬浮玻璃底栏的实现、故障分析或验证记录 |
| [x12.34-full-all-banner-review.json](../newx-resolver-linter/x12.34-full-all-banner-review.json) | 完整下载信息与原生进度条（全选方案）的静态检查结果 |
| [x12.34-full-all-bulk-review.json](../newx-resolver-linter/x12.34-full-all-bulk-review.json) | 完整下载信息与原生进度条（全选方案）的静态检查结果 |
| [x12.34-full-all-fixed-review.json](../newx-resolver-linter/x12.34-full-all-fixed-review.json) | 完整下载信息与原生进度条（全选方案）的静态检查结果 |
| [x12.34-full-all-glass-review.json](../newx-resolver-linter/x12.34-full-all-glass-review.json) | 完整下载信息与原生进度条（全选方案）的静态检查结果 |
| [x12.34-full-all-result.json](../newx-resolver-linter/x12.34-full-all-result.json) | 完整下载信息与原生进度条（全选方案）的修补结果或验证数据 |
| [x12.34-full-all.log](../newx-resolver-linter/x12.34-full-all.log) | 完整下载信息与原生进度条（全选方案）的修补执行日志 |
| [x12.34-full-default-banner-review.json](../newx-resolver-linter/x12.34-full-default-banner-review.json) | 完整下载信息与原生进度条（默认方案）的静态检查结果 |
| [x12.34-full-default-bulk-review.json](../newx-resolver-linter/x12.34-full-default-bulk-review.json) | 完整下载信息与原生进度条（默认方案）的静态检查结果 |
| [x12.34-full-default-fixed-review.json](../newx-resolver-linter/x12.34-full-default-fixed-review.json) | 完整下载信息与原生进度条（默认方案）的静态检查结果 |
| [x12.34-full-default-glass-review.json](../newx-resolver-linter/x12.34-full-default-glass-review.json) | 完整下载信息与原生进度条（默认方案）的静态检查结果 |
| [x12.34-full-default-result.json](../newx-resolver-linter/x12.34-full-default-result.json) | 完整下载信息与原生进度条（默认方案）的修补结果或验证数据 |
| [x12.34-full-default.log](../newx-resolver-linter/x12.34-full-default.log) | 完整下载信息与原生进度条（默认方案）的修补执行日志 |
| [x12.34-full-first-failure.log](../newx-resolver-linter/x12.34-full-first-failure.log) | 完整下载信息与原生进度条的失败修补日志 |
| [x12.34-full-frame-failure.log](../newx-resolver-linter/x12.34-full-frame-failure.log) | 完整下载信息与原生进度条的失败修补日志 |
| [x12.34-full-provenance.json](../newx-resolver-linter/x12.34-full-provenance.json) | 完整下载信息与原生进度条的源码、构建和文件哈希来源记录 |
| [x12.34-glass-all-regression.json](../newx-resolver-linter/x12.34-glass-all-regression.json) | 悬浮玻璃底栏（全选方案）的静态检查结果 |
| [x12.34-glass-all-review.json](../newx-resolver-linter/x12.34-glass-all-review.json) | 悬浮玻璃底栏（全选方案）的静态检查结果 |
| [x12.34-glass-all.json](../newx-resolver-linter/x12.34-glass-all.json) | 悬浮玻璃底栏（全选方案）的修补结果或验证数据 |
| [x12.34-glass-default-regression.json](../newx-resolver-linter/x12.34-glass-default-regression.json) | 悬浮玻璃底栏（默认方案）的静态检查结果 |
| [x12.34-glass-default-review.json](../newx-resolver-linter/x12.34-glass-default-review.json) | 悬浮玻璃底栏（默认方案）的静态检查结果 |
| [x12.34-glass-default.json](../newx-resolver-linter/x12.34-glass-default.json) | 悬浮玻璃底栏（默认方案）的修补结果或验证数据 |
| [x12.34-glass-label-failure-2026-10-16.log](../newx-resolver-linter/x12.34-glass-label-failure-2026-10-16.log) | 悬浮玻璃底栏的失败修补日志 |
| [x12.34-glass-label-failure-2026-10-16.md](../newx-resolver-linter/x12.34-glass-label-failure-2026-10-16.md) | 悬浮玻璃底栏的实现、故障分析或验证记录 |
| [x12.34-glass-label-failure-provenance.json](../newx-resolver-linter/x12.34-glass-label-failure-provenance.json) | 悬浮玻璃底栏的源码、构建和文件哈希来源记录 |
| [x12.34-glass-label-failure-result.partial.txt](../newx-resolver-linter/x12.34-glass-label-failure-result.partial.txt) | 悬浮玻璃底栏的诊断输出 |
| [x12.34-glass-padding-failure-2026-10-16.log](../newx-resolver-linter/x12.34-glass-padding-failure-2026-10-16.log) | 悬浮玻璃底栏的失败修补日志 |
| [x12.34-glass-padding-failure-2026-10-16.md](../newx-resolver-linter/x12.34-glass-padding-failure-2026-10-16.md) | 悬浮玻璃底栏的实现、故障分析或验证记录 |
| [x12.34-glass-padding-failure-provenance.json](../newx-resolver-linter/x12.34-glass-padding-failure-provenance.json) | 悬浮玻璃底栏的源码、构建和文件哈希来源记录 |
| [x12.34-glass-padding-failure-result.json](../newx-resolver-linter/x12.34-glass-padding-failure-result.json) | 悬浮玻璃底栏的失败信息记录 |
| [x12.34-glass-register-failure-2026-10-16.log](../newx-resolver-linter/x12.34-glass-register-failure-2026-10-16.log) | 悬浮玻璃底栏的失败修补日志 |
| [x12.34-glass-register-failure-2026-10-16.md](../newx-resolver-linter/x12.34-glass-register-failure-2026-10-16.md) | 悬浮玻璃底栏的实现、故障分析或验证记录 |
| [x12.34-glass-register-failure-provenance.json](../newx-resolver-linter/x12.34-glass-register-failure-provenance.json) | 悬浮玻璃底栏的源码、构建和文件哈希来源记录 |
| [x12.34-glass-register-failure-result.json](../newx-resolver-linter/x12.34-glass-register-failure-result.json) | 悬浮玻璃底栏的失败信息记录 |
| [x12.34-glass-source-provenance.json](../newx-resolver-linter/x12.34-glass-source-provenance.json) | 悬浮玻璃底栏的源码、构建和文件哈希来源记录 |
| [x12.34-glass-tests.json](../newx-resolver-linter/x12.34-glass-tests.json) | 悬浮玻璃底栏的测试统计记录 |
| [x12.34-pinned-all-bulk.json](../newx-resolver-linter/x12.34-pinned-all-bulk.json) | 固定顶部进度和独立任务通知（全选方案）的修补结果或验证数据 |
| [x12.34-pinned-all-fixed.json](../newx-resolver-linter/x12.34-pinned-all-fixed.json) | 固定顶部进度和独立任务通知（全选方案）的修补结果或验证数据 |
| [x12.34-pinned-all-glass.json](../newx-resolver-linter/x12.34-pinned-all-glass.json) | 固定顶部进度和独立任务通知（全选方案）的修补结果或验证数据 |
| [x12.34-pinned-all-native.json](../newx-resolver-linter/x12.34-pinned-all-native.json) | 固定顶部进度和独立任务通知（全选方案）的修补结果或验证数据 |
| [x12.34-pinned-all.json](../newx-resolver-linter/x12.34-pinned-all.json) | 固定顶部进度和独立任务通知（全选方案）的修补结果或验证数据 |
| [x12.34-pinned-all.log](../newx-resolver-linter/x12.34-pinned-all.log) | 固定顶部进度和独立任务通知（全选方案）的修补执行日志 |
| [x12.34-pinned-apks.json](../newx-resolver-linter/x12.34-pinned-apks.json) | 固定顶部进度和独立任务通知的修补结果或验证数据 |
| [x12.34-pinned-default-bulk.json](../newx-resolver-linter/x12.34-pinned-default-bulk.json) | 固定顶部进度和独立任务通知（默认方案）的修补结果或验证数据 |
| [x12.34-pinned-default-fixed.json](../newx-resolver-linter/x12.34-pinned-default-fixed.json) | 固定顶部进度和独立任务通知（默认方案）的修补结果或验证数据 |
| [x12.34-pinned-default-glass.json](../newx-resolver-linter/x12.34-pinned-default-glass.json) | 固定顶部进度和独立任务通知（默认方案）的修补结果或验证数据 |
| [x12.34-pinned-default-native.json](../newx-resolver-linter/x12.34-pinned-default-native.json) | 固定顶部进度和独立任务通知（默认方案）的修补结果或验证数据 |
| [x12.34-pinned-default.json](../newx-resolver-linter/x12.34-pinned-default.json) | 固定顶部进度和独立任务通知（默认方案）的修补结果或验证数据 |
| [x12.34-pinned-default.log](../newx-resolver-linter/x12.34-pinned-default.log) | 固定顶部进度和独立任务通知（默认方案）的修补执行日志 |
| [x12.34-pinned-source-provenance.json](../newx-resolver-linter/x12.34-pinned-source-provenance.json) | 固定顶部进度和独立任务通知的源码、构建和文件哈希来源记录 |
| [x12.34-pinned-tests.json](../newx-resolver-linter/x12.34-pinned-tests.json) | 固定顶部进度和独立任务通知的测试统计记录 |
| [x12.34-port-2026-10-15.md](../newx-resolver-linter/x12.34-port-2026-10-15.md) | 12.34 版本适配的实现、故障分析或验证记录 |
| [x12.34-post-policy-errors.json](../newx-resolver-linter/x12.34-post-policy-errors.json) | 帖子策略适配的失败信息记录 |
| [x12.34-profile-bulk-2026-10-16.md](../newx-resolver-linter/x12.34-profile-bulk-2026-10-16.md) | 主页批量下载、分页与重试的实现、故障分析或验证记录 |
| [x12.34-profile-bulk-all-static-review.json](../newx-resolver-linter/x12.34-profile-bulk-all-static-review.json) | 主页批量下载、分页与重试（全选方案）的静态检查结果 |
| [x12.34-profile-bulk-all.json](../newx-resolver-linter/x12.34-profile-bulk-all.json) | 主页批量下载、分页与重试（全选方案）的修补结果或验证数据 |
| [x12.34-profile-bulk-default-static-review.json](../newx-resolver-linter/x12.34-profile-bulk-default-static-review.json) | 主页批量下载、分页与重试（默认方案）的静态检查结果 |
| [x12.34-profile-bulk-default.json](../newx-resolver-linter/x12.34-profile-bulk-default.json) | 主页批量下载、分页与重试（默认方案）的修补结果或验证数据 |
| [x12.34-profile-bulk-empty-retry-2026-10-16.md](../newx-resolver-linter/x12.34-profile-bulk-empty-retry-2026-10-16.md) | 主页批量下载、分页与重试的实现、故障分析或验证记录 |
| [x12.34-profile-bulk-fix-all-static-review.json](../newx-resolver-linter/x12.34-profile-bulk-fix-all-static-review.json) | 主页批量下载、分页与重试（全选方案）的静态检查结果 |
| [x12.34-profile-bulk-fix-all.json](../newx-resolver-linter/x12.34-profile-bulk-fix-all.json) | 主页批量下载、分页与重试（全选方案）的修补结果或验证数据 |
| [x12.34-profile-bulk-fix-baseline-comparison.json](../newx-resolver-linter/x12.34-profile-bulk-fix-baseline-comparison.json) | 主页批量下载、分页与重试的修补结果或验证数据 |
| [x12.34-profile-bulk-fix-default-static-review.json](../newx-resolver-linter/x12.34-profile-bulk-fix-default-static-review.json) | 主页批量下载、分页与重试（默认方案）的静态检查结果 |
| [x12.34-profile-bulk-fix-default.json](../newx-resolver-linter/x12.34-profile-bulk-fix-default.json) | 主页批量下载、分页与重试（默认方案）的修补结果或验证数据 |
| [x12.34-profile-bulk-fix-test-counts.json](../newx-resolver-linter/x12.34-profile-bulk-fix-test-counts.json) | 主页批量下载、分页与重试的测试统计记录 |
| [x12.34-profile-bulk-host-failure.json](../newx-resolver-linter/x12.34-profile-bulk-host-failure.json) | 主页批量下载、分页与重试的失败信息记录 |
| [x12.34-profile-bulk-host-failure.txt](../newx-resolver-linter/x12.34-profile-bulk-host-failure.txt) | 主页批量下载、分页与重试的诊断输出 |
| [x12.34-round6-errors.json](../newx-resolver-linter/x12.34-round6-errors.json) | NewX 适配与回归验证的失败信息记录 |
| [x12.34-static-review.json](../newx-resolver-linter/x12.34-static-review.json) | NewX 适配与回归验证的静态检查结果 |
| [x12.34-toast-all.json](../newx-resolver-linter/x12.34-toast-all.json) | 保存成功提示去重（全选方案）的修补结果或验证数据 |
| [x12.34-toast-default.json](../newx-resolver-linter/x12.34-toast-default.json) | 保存成功提示去重（默认方案）的修补结果或验证数据 |
| [x12.34-toast-merge-failure.json](../newx-resolver-linter/x12.34-toast-merge-failure.json) | 保存成功提示去重的失败信息记录 |
| [x12.34-toast-merge-failure.txt](../newx-resolver-linter/x12.34-toast-merge-failure.txt) | 保存成功提示去重的诊断输出 |
| [x12.34-toast-static-review.json](../newx-resolver-linter/x12.34-toast-static-review.json) | 保存成功提示去重的静态检查结果 |
| [x12.34-unified-banner-2026-10-16.md](../newx-resolver-linter/x12.34-unified-banner-2026-10-16.md) | 统一下载通知的实现、故障分析或验证记录 |
| [x12.34-verified-all.json](../newx-resolver-linter/x12.34-verified-all.json) | NewX 适配与回归验证（全选方案）的修补结果或验证数据 |
| [x12.34-verified-default.json](../newx-resolver-linter/x12.34-verified-default.json) | NewX 适配与回归验证（默认方案）的修补结果或验证数据 |
| [x12.34-video-model-errors.json](../newx-resolver-linter/x12.34-video-model-errors.json) | 视频模型适配的失败信息记录 |
| [x12.34-zh-2026-10-15.md](../newx-resolver-linter/x12.34-zh-2026-10-15.md) | 简体中文资源的实现、故障分析或验证记录 |
| [x12.34-zh-all.json](../newx-resolver-linter/x12.34-zh-all.json) | 简体中文资源（全选方案）的修补结果或验证数据 |
| [x12.34-zh-default.json](../newx-resolver-linter/x12.34-zh-default.json) | 简体中文资源（默认方案）的修补结果或验证数据 |
| [x12.34-zh-static-review.json](../newx-resolver-linter/x12.34-zh-static-review.json) | 简体中文资源的静态检查结果 |

## `docs/releases`

| 文件名 | 作用 |
| --- | --- |
| [v3.10.0-custom-newx.1.md](../releases/v3.10.0-custom-newx.1.md) | v3.10.0-custom-newx.1 的发布说明：Piko NewX Custom — X 12.34 arm64 |
| [v3.10.0-custom-newx.10.md](../releases/v3.10.0-custom-newx.10.md) | v3.10.0-custom-newx.10 的发布说明：下载进行时，原生顶部进度固定展示，只更新文字，不再每秒重新弹出。 |
| [v3.10.0-custom-newx.11.md](../releases/v3.10.0-custom-newx.11.md) | v3.10.0-custom-newx.11 的发布说明：恢复同一顶部通知中的完整下载进度：百分比、横向进度条、已传输/总大小和实时速度。 |
| [v3.10.0-custom-newx.12.md](../releases/v3.10.0-custom-newx.12.md) | v3.10.0-custom-newx.12 的发布说明：Instagram 主页媒体下载与统一顶部进度 |
| [v3.10.0-custom-newx.13.md](../releases/v3.10.0-custom-newx.13.md) | v3.10.0-custom-newx.13 的发布说明：Instagram 451 适配与主页批量下载 |
| [v3.10.0-custom-newx.14.md](../releases/v3.10.0-custom-newx.14.md) | v3.10.0-custom-newx.14 的发布说明：Instagram 451 启动闪退修复 |
| [v3.10.0-custom-newx.15.md](../releases/v3.10.0-custom-newx.15.md) | v3.10.0-custom-newx.15 的发布说明：Instagram 下载按钮与中文设置 |
| [v3.10.0-custom-newx.16.md](../releases/v3.10.0-custom-newx.16.md) | v3.10.0-custom-newx.16 的发布说明：修复 Instagram 中文按钮错配，保留左侧下载图标 |
| [v3.10.0-custom-newx.2.md](../releases/v3.10.0-custom-newx.2.md) | v3.10.0-custom-newx.2 的发布说明：Piko NewX Custom — 简体中文与功能补齐 |
| [v3.10.0-custom-newx.3.md](../releases/v3.10.0-custom-newx.3.md) | v3.10.0-custom-newx.3 的发布说明：Piko NewX Custom — 按作者分别保存媒体 |
| [v3.10.0-custom-newx.4.md](../releases/v3.10.0-custom-newx.4.md) | v3.10.0-custom-newx.4 的发布说明：Piko NewX Custom — 去掉下载成功的底部重复提示 |
| [v3.10.0-custom-newx.5.md](../releases/v3.10.0-custom-newx.5.md) | v3.10.0-custom-newx.5 的发布说明：Piko NewX Custom — 主页一键下载与设置分类优化 |
| [v3.10.0-custom-newx.6.md](../releases/v3.10.0-custom-newx.6.md) | v3.10.0-custom-newx.6 的发布说明：Piko NewX Custom — 修复主页批量下载分页失败 |
| [v3.10.0-custom-newx.7.md](../releases/v3.10.0-custom-newx.7.md) | v3.10.0-custom-newx.7 的发布说明：Piko NewX Custom — 悬浮玻璃底栏 |
| [v3.10.0-custom-newx.8.md](../releases/v3.10.0-custom-newx.8.md) | v3.10.0-custom-newx.8 的发布说明：Piko NewX Custom — 固定底栏、自动刷新限制与下载进度 |
| [v3.10.0-custom-newx.9.md](../releases/v3.10.0-custom-newx.9.md) | v3.10.0-custom-newx.9 的发布说明：下载进度与原生顶部提示合并 |

## `extensions/instagram/src/test/java/app/morphe/extension/instagram/patches/download`

| 文件名 | 作用 |
| --- | --- |
| [ProfileDownloadStateTest.java](../../extensions/instagram/src/test/java/app/morphe/extension/instagram/patches/download/ProfileDownloadStateTest.java) | 验证 Instagram 缓存与分页完成判断、并发到达页面的保留、异常游标拦截、拒绝冷却期限，以及暂停后停止读取轮播媒体信息 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/featureswitches`

| 文件名 | 作用 |
| --- | --- |
| [FeatureSwitchStoreTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/featureswitches/FeatureSwitchStoreTest.java) | 验证“保存实验功能开关的覆盖值”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/filteredreplies`

| 文件名 | 作用 |
| --- | --- |
| [FilteredRepliesStoreTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/filteredreplies/FilteredRepliesStoreTest.java) | 验证“保存被过滤回复的相关数据”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/misc`

| 文件名 | 作用 |
| --- | --- |
| [CanonicalUrlResolverTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/CanonicalUrlResolverTest.java) | 验证“解析规范原始链接”的回归测试 |
| [InlineActionBarSpacingTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/InlineActionBarSpacingTest.java) | 验证“调整帖子动作栏的间距”的回归测试 |
| [InlineActionFilterTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/InlineActionFilterTest.java) | 验证“按设置过滤帖子动作按钮”的回归测试 |
| [InlineDownloadButtonTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/InlineDownloadButtonTest.java) | 验证“处理帖子下载按钮的点击和媒体收集”的回归测试 |
| [MediaDiskCacheTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/MediaDiskCacheTest.java) | 验证“管理媒体缩略图的磁盘缓存”的回归测试 |
| [NewXCrashHandlerTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/NewXCrashHandlerTest.java) | 验证“收集和保存 NewX 扩展崩溃日志”的回归测试 |
| [NewXDownloadFoldersTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/NewXDownloadFoldersTest.java) | 验证“选择保存目录并生成按作者区分的子目录”的回归测试 |
| [NewXDownloaderTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/NewXDownloaderTest.java) | 验证“组织媒体下载请求、事件监听和完成处理”的回归测试 |
| [NewXPostOptionsTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/NewXPostOptionsTest.java) | 验证“提供帖子菜单的扩展选项”的回归测试 |
| [NewXShareImageHandlerTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/NewXShareImageHandlerTest.java) | 验证“处理帖子分享为图片的操作”的回归测试 |
| [PollResultsFormatterTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/PollResultsFormatterTest.java) | 验证“格式化投票结果和百分比”的回归测试 |
| [ProfileBulkPagerTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/ProfileBulkPagerTest.java) | 验证“控制主页分页、停滞判断、空页与失败重试”的回归测试 |
| [ProfilePostSortingResolverTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/ProfilePostSortingResolverTest.java) | 验证“解析主页帖子的默认排序设置”的回归测试 |
| [ProfileTabFilterTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/ProfileTabFilterTest.java) | 验证“按配置过滤主页标签页”的回归测试 |
| [ReplySortingResolverTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/ReplySortingResolverTest.java) | 验证“解析默认回复排序设置”的回归测试 |
| [SharingLinksTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/misc/SharingLinksTest.java) | 分享域名和链接清理的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/postfilter`

| 文件名 | 作用 |
| --- | --- |
| [PostFilterMatcherTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/postfilter/PostFilterMatcherTest.java) | 验证“判断帖子是否命中过滤规则”的回归测试 |
| [PostFilterRuleStoreTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/postfilter/PostFilterRuleStoreTest.java) | 验证“保存和读取帖子过滤规则”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/settings`

| 文件名 | 作用 |
| --- | --- |
| [NewXSettingsHostTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/settings/NewXSettingsHostTest.java) | 验证“组织设置页面、入口和页面切换”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/theme`

| 文件名 | 作用 |
| --- | --- |
| [DynamicColorPaletteTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/theme/DynamicColorPaletteTest.java) | 验证“生成和查询动态颜色调色板”的回归测试 |
| [TwitterThemeTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/theme/TwitterThemeTest.java) | 验证“定义 Twitter 风格主题配色”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/timeline`

| 文件名 | 作用 |
| --- | --- |
| [ForYouTopicFilterTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/timeline/ForYouTopicFilterTest.java) | 验证“保存并应用“为你推荐”主题筛选”的回归测试 |
| [NewXTimelineFilterTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/timeline/NewXTimelineFilterTest.java) | 验证“在运行时过滤时间线条目”的回归测试 |
| [TimelineFilterBaselineTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/timeline/TimelineFilterBaselineTest.java) | 时间线过滤基线的回归测试 |
| [TimelineScrollPositionStoreTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/timeline/TimelineScrollPositionStoreTest.java) | 验证“保存并恢复时间线滚动位置”的回归测试 |
| [TimelineTabFilterTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/timeline/TimelineTabFilterTest.java) | 验证“按配置过滤时间线标签页”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/ui`

| 文件名 | 作用 |
| --- | --- |
| [ThemeStyleResolutionTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/ui/ThemeStyleResolutionTest.java) | 主题样式解析的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/newx/utils`

| 文件名 | 作用 |
| --- | --- |
| [NewXUtilsTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/utils/NewXUtilsTest.java) | 验证“提供 NewX 上下文、页面及常用操作辅助”的回归测试 |
| [ToStringParserTest.java](../../extensions/newx/src/test/java/app/morphe/extension/newx/utils/ToStringParserTest.java) | 验证“从原生模型文字表示中解析语义字段”的回归测试 |

## `extensions/newx/src/test/java/app/morphe/extension/shared`

| 文件名 | 作用 |
| --- | --- |
| [Logger.java](../../extensions/newx/src/test/java/app/morphe/extension/shared/Logger.java) | 单元测试使用的日志或设置替身，不是打包进应用的功能代码 |

## `extensions/newx/src/test/java/app/morphe/extension/shared/settings`

| 文件名 | 作用 |
| --- | --- |
| [BooleanSetting.java](../../extensions/newx/src/test/java/app/morphe/extension/shared/settings/BooleanSetting.java) | 单元测试使用的日志或设置替身，不是打包进应用的功能代码 |
| [Setting.java](../../extensions/newx/src/test/java/app/morphe/extension/shared/settings/Setting.java) | 单元测试使用的日志或设置替身，不是打包进应用的功能代码 |
| [StringSetting.java](../../extensions/newx/src/test/java/app/morphe/extension/shared/settings/StringSetting.java) | 单元测试使用的日志或设置替身，不是打包进应用的功能代码 |

## `patches/src/test/kotlin/app/crimera/patches/instagram/filter/story`

| 文件名 | 作用 |
| --- | --- |
| [FilterStoriesPatchTest.kt](../../patches/src/test/kotlin/app/crimera/patches/instagram/filter/story/FilterStoriesPatchTest.kt) | 回归451启动崩溃：检查快拍对象来源、字段循环、空值路径和合法跳转，拒绝错误/歧义循环 |

## `patches/src/test/kotlin/app/crimera/patches/instagram/links/misc`

| 文件名 | 作用 |
| --- | --- |
| [SuggestionStringConstantsTest.kt](../../patches/src/test/kotlin/app/crimera/patches/instagram/links/misc/SuggestionStringConstantsTest.kt) | 验证R8纯文字开关表和常量参数解码，拒绝不符合契约的表或调用 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/bringbacktwitter`

| 文件名 | 作用 |
| --- | --- |
| [RestoreTwitterTopBarLogoPatchTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/bringbacktwitter/RestoreTwitterTopBarLogoPatchTest.kt) | 验证“恢复顶部 Twitter 鸟形标志”的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/dynamiccolor`

| 文件名 | 作用 |
| --- | --- |
| [XdsFactoryTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/dynamiccolor/XdsFactoryTest.kt) | 原生主题工厂定位的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/inlineactions`

| 文件名 | 作用 |
| --- | --- |
| [UnifiedDownloadNotificationPatchTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/inlineactions/UnifiedDownloadNotificationPatchTest.kt) | 验证“统一下载进度、完成、失败和取消的顶部通知及生命周期”的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/mediaquality`

| 文件名 | 作用 |
| --- | --- |
| [PreferredVideoResolutionTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/mediaquality/PreferredVideoResolutionTest.kt) | 验证“解析所选视频分辨率及其匹配规则”的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/mediatab`

| 文件名 | 作用 |
| --- | --- |
| [ProfileBulkHostResolverTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/mediatab/ProfileBulkHostResolverTest.kt) | 主页批量下载宿主定位的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/navbar`

| 文件名 | 作用 |
| --- | --- |
| [FloatingGlassNavBarResolverTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/navbar/FloatingGlassNavBarResolverTest.kt) | 玻璃底栏定位的回归测试 |
| [NavBarTabDataWrapperTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/navbar/NavBarTabDataWrapperTest.kt) | 导航标签数据包装的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/postdetails`

| 文件名 | 作用 |
| --- | --- |
| [HidePostReplyBarPatchTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/postdetails/HidePostReplyBarPatchTest.kt) | 验证“隐藏帖子详情底部回复栏并处理留白”的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/misc/profiletabs`

| 文件名 | 作用 |
| --- | --- |
| [ProfileTabsHookTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/misc/profiletabs/ProfileTabsHookTest.kt) | 主页标签字节码注入的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/settings`

| 文件名 | 作用 |
| --- | --- |
| [SettingsAggregateValidationSupport.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/settings/SettingsAggregateValidationSupport.kt) | 设置聚合校验的测试辅助代码 |
| [SettingsAggregateValidationTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/settings/SettingsAggregateValidationTest.kt) | 所有设置注册、默认值及资源的回归测试 |

## `patches/src/test/kotlin/app/crimera/patches/newx/timeline`

| 文件名 | 作用 |
| --- | --- |
| [PostPoliciesPatchTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/timeline/PostPoliciesPatchTest.kt) | 验证“接入帖子、社区附注及相关交互策略”的回归测试 |
| [RestoreTimelinePositionPatchTest.kt](../../patches/src/test/kotlin/app/crimera/patches/newx/timeline/RestoreTimelinePositionPatchTest.kt) | 验证“接入时间线滚动位置的保存与恢复”的回归测试 |

## `patches/src/test/kotlin/app/crimera/tools/newx`

| 文件名 | 作用 |
| --- | --- |
| [NewXResolverLinterTest.kt](../../patches/src/test/kotlin/app/crimera/tools/newx/NewXResolverLinterTest.kt) | 补丁定位规则检查器的回归测试 |

## `x-lite/alpha-12.17.3-compatibility`

| 文件名 | 作用 |
| --- | --- |
| [README.md](../../x-lite/alpha-12.17.3-compatibility/README.md) | 历史 12.17.3 alpha 适配说明，不代表当前支持范围 |
