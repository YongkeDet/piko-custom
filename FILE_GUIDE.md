# 文件用途索引

在下表中，文件名右侧就是中文用途；点击文件名可直接打开源码。

Kotlin 补丁在修补安装包时运行；Java 扩展在修补后的应用中运行；XML 和图片提供文字、图标及布局。当前 combined 构建包含 NewX 与 Instagram，不包含旧版 Twitter / X 实现。Instagram 分表沿用 legacy-instagram.md 文件名，文件名不表示该模块已停用。

## 按功能查找

| 要修改的功能 | 主要文件 | 配套文件 |
| --- | --- | --- |
| 图片、视频保存目录与作者子文件夹 | [NewXDownloadFolders.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXDownloadFolders.java) | [DownloadFileName.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadFileName.java) |
| 普通媒体下载按钮 | [InlineDownloadButtonPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/InlineDownloadButtonPatch.kt) | [InlineDownloadButton.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/InlineDownloadButton.java) |
| 下载进度条、完整文字与实时速度 | [DownloadBannerContentPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/DownloadBannerContentPatch.kt) | [DownloadProgressDisplay.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadProgressDisplay.java) |
| 多任务通知排队、去重与取消 | [DownloadBannerController.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadBannerController.java) | [DownloadBannerPolicy.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadBannerPolicy.java) |
| 原生顶部通知接入 | [UnifiedDownloadNotificationPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/UnifiedDownloadNotificationPatch.kt) | [NewXInAppNotification.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXInAppNotification.java) |
| 主页全部图片和视频下载 | [ProfileBulkDownloadPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/misc/mediatab/ProfileBulkDownloadPatch.kt) | [ProfileBulkDownload.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileBulkDownload.java) |
| 主页分页、空页和失败重试 | [ProfileBulkPager.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileBulkPager.java) | [ProfileBulkDownload.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileBulkDownload.java) |
| 禁止自动刷新新内容 | [DisableTimelineRefreshPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/timeline/DisableTimelineRefreshPatch.kt) | [TimelineRefreshGate.java](extensions/newx/src/main/java/app/morphe/extension/newx/timeline/TimelineRefreshGate.java) |
| 固定底栏 | [FixedBottomBarPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar/FixedBottomBarPatch.kt) | [BottomBarVisibility.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/BottomBarVisibility.java) |
| 悬浮玻璃底栏 | [FloatingGlassNavBarPatch.kt](patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar/FloatingGlassNavBarPatch.kt) | [FloatingGlassNavBar.java](extensions/newx/src/main/java/app/morphe/extension/newx/misc/FloatingGlassNavBar.java) |
| Piko 设置分类与排序 | [SettingsCategories.kt](patches/src/main/kotlin/app/crimera/patches/newx/settings/SettingsCategories.kt) | [NewXSettingsContributions.kt](patches/src/main/kotlin/app/crimera/patches/newx/settings/NewXSettingsContributions.kt) |
| 新版 X 简体中文文字 | [strings.xml](patches/src/main/resources/addresources/values-zh-rCN/newx/strings.xml) | [strings.xml](patches/src/main/resources/addresources/values/newx/strings.xml) |
| Instagram 主页全部图片和视频下载 | [ProfileDownloadPatch.kt](patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/ProfileDownloadPatch.kt) | [ProfileDownloader.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloader.java) |
| Instagram 帖子网格分页、缓存与结束判断 | [ProfilePageState.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfilePageState.java) | [NativeProfileBridge.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/NativeProfileBridge.java) |
| Instagram 已保存文件记录与手动继续 | [ProfileDownloadStore.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloadStore.java) | [ProfileDownloader.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloader.java) |
| Instagram 串行下载、暂停与拒绝冷却 | [InstagramDownloads.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/InstagramDownloads.java) | [DownloadRatePolicy.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadRatePolicy.java) |
| Instagram 顶部完整进度与结果排队 | [TopDownloadNotice.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/TopDownloadNotice.java) | [InstagramDownloads.java](extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/InstagramDownloads.java) |
| Instagram 简体中文文字 | [strings.xml](patches/src/main/resources/addresources/values-zh-rCN/instagram/strings.xml) | [strings.xml](patches/src/main/resources/addresources/values/instagram/strings.xml) |
| Morphe 补丁源和发布 | [patches-bundle.json](patches-bundle.json) | [publish-instagram-profile.yml](.github/workflows/publish-instagram-profile.yml) |

## 全部文件

共 **1,509 个文件**，按目录逐个标注，包含本索引和维护脚本。范围是 Git 管理的仓库文件，不含本地安装包、编译产物及缓存。

| 分组 | 文件数 | 内容 |
| --- | ---: | --- |
| [项目配置与维护](docs/file-guide/project.md) | 53 | 构建配置、发布流程、问题模板和仓库维护文件。 |
| [新版 X 补丁入口](docs/file-guide/newx-patches.md) | 86 | 修补安装包时运行的 Kotlin 代码：定位原生实现并注入调用。 |
| [新版 X 运行时代码](docs/file-guide/newx-runtime.md) | 109 | 注入安装包后随 X 运行的设置、下载、通知、过滤及界面实现。 |
| [旧版 Twitter / X 代码](docs/file-guide/legacy-twitter.md) | 207 | 保留的旧版补丁和扩展；当前 NewX 构建排除这些实现，不能据此判断 12.34 已支持相应功能。 |
| [Instagram 代码](docs/file-guide/legacy-instagram.md) | 309 | Instagram 补丁和扩展，包含主页媒体下载、原生分页接入、串行下载与顶部进度；combined 构建与 NewX 一同打包。分表文件名为历史沿用。 |
| [共享代码与工具](docs/file-guide/shared.md) | 33 | 多个应用共用的存储、设置、请求、界面辅助及通用补丁工具。部分通用实现来自外部依赖，不在此仓库内。 |
| [编译期类型占位](docs/file-guide/stubs.md) | 61 | 为编译提供宿主应用、Android 库及 Kotlin 类型签名；这些占位类不是实际功能实现。 |
| [文字、翻译与选项资源](docs/file-guide/resources-strings.md) | 186 | 按语言和应用分组的界面文字及选项资源；新版 X 简体中文位于 values-zh-rCN/newx。 |
| [图标、图片与布局资源](docs/file-guide/resources-assets.md) | 223 | 应用图标、品牌替换图片、设置布局和其他 Android 资源。 |
| [测试、验证与历史说明](docs/file-guide/validation.md) | 242 | 回归测试、安装包修补结果、失败日志、来源校验和发布说明；历史记录不等于当前版本的兼容承诺。 |

## 常用根目录文件

| 文件名 | 作用 |
| --- | --- |
| [README.md](README.md) | 项目首页：导入、功能、使用方法和当前验证范围 |
| [patches-bundle.json](patches-bundle.json) | Morphe 读取的稳定补丁源：版本、下载地址、发布时间和说明 |
| [patches-list.json](patches-list.json) | 供工具使用的补丁清单数据；当前可用项以发布包和 README 为准 |
| [gradle.properties](gradle.properties) | 保存补丁包版本和 Gradle 构建参数 |
| [settings.gradle.kts](settings.gradle.kts) | 声明模块、插件及依赖仓库 |
| [build.gradle.kts](build.gradle.kts) | 定义根项目插件和各扩展模块的构建规则 |
| [patch-twitter.sh](patch-twitter.sh) | 验证当前补丁包来源后修补显式指定的原始 APK / APKM，并检查失败与重建结果 |
| [patch-instagram.sh](patch-instagram.sh) | 构建或校验当前 combined 补丁包来源，修补显式指定的原始 Instagram APK / APKM，并核对修补与重建结果 |
| [dns-probe.sh](dns-probe.sh) | 在 Termux 等环境检查 X 媒体域名的 DNS 和 HTTPS 连通性 |
| [AGENTS.md](AGENTS.md) | 规定本仓库的修改、字节码注入、验证与设备操作规则 |
| [LICENSE](LICENSE) | 项目的 GPLv3 授权条款 |
| [NOTICE](NOTICE) | 说明上游来源、版权和授权声明 |

## 维护索引

用途说明和目录规则维护在 [update-file-guide.py](scripts/update-file-guide.py)。在仓库根目录运行：

```sh
python3 scripts/update-file-guide.py
```

新增源码文件先加入 Git，再更新对应说明并运行脚本。新版 X 的补丁和运行时文件缺少专门说明时，脚本会报错，避免把新功能只标成笼统的“源代码”。

功能是否已迁移到当前 X 版本，请看 [旧版功能迁移对照表](docs/newx-12.34-coverage.md)；文件存在本身不代表该版本可用。
