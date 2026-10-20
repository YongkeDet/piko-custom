# 共享代码与工具

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 33 个文件。

多个应用共用的存储、设置、请求、界面辅助及通用补丁工具。部分通用实现来自外部依赖，不在此仓库内。

## `extensions`

| 文件名 | 作用 |
| --- | --- |
| [proguard-rules.pro](../../extensions/proguard-rules.pro) | 控制扩展代码压缩与保留，维持补丁调用所需的类和方法 |

## `extensions/shared`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../extensions/shared/build.gradle.kts) | 配置 extensions/shared 模块的依赖、编译及打包 |

## `extensions/shared/library`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../extensions/shared/library/build.gradle.kts) | 配置 extensions/shared/library 模块的依赖、编译及打包 |

## `extensions/shared/library/src/main/java/app/morphe/extension/crimera`

| 文件名 | 作用 |
| --- | --- |
| [CustomCrashHandler.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/CustomCrashHandler.java) | 收集扩展崩溃信息 |
| [ObjectBrowser.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/ObjectBrowser.java) | 提供对象字段浏览与调试界面 |
| [PikoUtils.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/PikoUtils.java) | 提供扩展共用操作辅助 |

## `extensions/shared/library/src/main/java/app/morphe/extension/crimera/constants`

| 文件名 | 作用 |
| --- | --- |
| [ExtensionStrings.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/constants/ExtensionStrings.java) | 定义扩展文字资源的访问常量 |
| [TooltipHelper.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/constants/TooltipHelper.java) | 提供操作提示辅助 |

## `extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader`

| 文件名 | 作用 |
| --- | --- |
| [DownloadFileNames.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/DownloadFileNames.java) | 生成旧下载器的保存文件名 |
| [DownloadMetadata.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/DownloadMetadata.java) | 表示下载媒体的元数据 |
| [DownloadRequest.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/DownloadRequest.java) | 表示旧下载器的一次下载请求 |
| [FolderPickerActivity.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/FolderPickerActivity.java) | 承载保存目录选择页面 |
| [MediaDownloader.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/MediaDownloader.java) | 执行旧版 Twitter / X 与 Instagram 共用的媒体传输和文件保存，提供阻塞下载、字节进度与 HTTP 失败信息 |
| [MediaType.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/MediaType.java) | 定义下载媒体类型 |
| [MetadataMuxer.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/MetadataMuxer.java) | 处理保存媒体的元数据封装 |
| [StorageUtils.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/legacydownloader/StorageUtils.java) | 提供共享存储与文件操作辅助 |

## `extensions/shared/library/src/main/java/app/morphe/extension/crimera/settings`

| 文件名 | 作用 |
| --- | --- |
| [BooleanSetting.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/settings/BooleanSetting.java) | 表示和读取布尔设置 |
| [Setting.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/settings/Setting.java) | 定义设置项的基础契约 |
| [StringSetting.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/settings/StringSetting.java) | 表示和读取文字设置 |

## `extensions/shared/library/src/main/java/app/morphe/extension/crimera/sharedPreference`

| 文件名 | 作用 |
| --- | --- |
| [BaseSharedPref.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/sharedPreference/BaseSharedPref.java) | 提供偏好配置读写的基础实现 |
| [SharedPref.java](../../extensions/shared/library/src/main/java/app/morphe/extension/crimera/sharedPreference/SharedPref.java) | 封装共享偏好配置访问 |

## `extensions/shared/library/src/main/java/app/morphe/extension/shared`

| 文件名 | 作用 |
| --- | --- |
| [MarkChatAsReadScope.java](../../extensions/shared/library/src/main/java/app/morphe/extension/shared/MarkChatAsReadScope.java) | 限定将聊天标记已读的操作范围 |
| [ShareLinkSanitizer.java](../../extensions/shared/library/src/main/java/app/morphe/extension/shared/ShareLinkSanitizer.java) | 清理分享链接中的追踪参数 |

## `extensions/shared/library/src/main/java/app/morphe/extension/shared/requests`

| 文件名 | 作用 |
| --- | --- |
| [Requester.java](../../extensions/shared/library/src/main/java/app/morphe/extension/shared/requests/Requester.java) | 执行共享网络请求 |
| [Route.java](../../extensions/shared/library/src/main/java/app/morphe/extension/shared/requests/Route.java) | 表示网络请求的路由 |

## `extensions/shared/library/src/main/java/app/morphe/extension/shared/settings/preference`

| 文件名 | 作用 |
| --- | --- |
| [PikoSharedPrefCategory.java](../../extensions/shared/library/src/main/java/app/morphe/extension/shared/settings/preference/PikoSharedPrefCategory.java) | 提供共享偏好设置分类控件 |

## `extensions/shared/library/src/main/java/app/morphe/extension/shared/ui`

| 文件名 | 作用 |
| --- | --- |
| [ListViewDragSelectionController.java](../../extensions/shared/library/src/main/java/app/morphe/extension/shared/ui/ListViewDragSelectionController.java) | 处理列表拖动选择 |

## `extensions/shared/media3`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../extensions/shared/media3/build.gradle.kts) | 配置 extensions/shared/media3 模块的依赖、编译及打包 |

## `extensions/shared/src/main`

| 文件名 | 作用 |
| --- | --- |
| [AndroidManifest.xml](../../extensions/shared/src/main/AndroidManifest.xml) | 声明本扩展或占位模块的 Android 组件、权限与应用信息 |

## `patches/src/main/kotlin/app/crimera/patches/utils`

| 文件名 | 作用 |
| --- | --- |
| [ScopedFingerprintMatching.kt](../../patches/src/main/kotlin/app/crimera/patches/utils/ScopedFingerprintMatching.kt) | 辅助在指定类或范围内匹配原生方法指纹 |

## `patches/src/main/kotlin/app/morphe/util/resource`

| 文件名 | 作用 |
| --- | --- |
| [CheckString.kt](../../patches/src/main/kotlin/app/morphe/util/resource/CheckString.kt) | 检查翻译字符串及格式占位符 |

## `patches/src/main/kotlin/app/revanced/patches/all/misc/activity/exportall`

| 文件名 | 作用 |
| --- | --- |
| [ExportAllActivitiesPatch.kt](../../patches/src/main/kotlin/app/revanced/patches/all/misc/activity/exportall/ExportAllActivitiesPatch.kt) | 通用补丁：调整 Activity 的导出状态；当前 NewX 发布包排除此旧通用补丁 |

## `patches/src/main/kotlin/app/revanced/patches/all/misc/versioncode`

| 文件名 | 作用 |
| --- | --- |
| [ChangeVersionCodePatch.kt](../../patches/src/main/kotlin/app/revanced/patches/all/misc/versioncode/ChangeVersionCodePatch.kt) | 通用补丁：修改应用版本代码；当前 NewX 发布包排除此旧通用补丁 |
