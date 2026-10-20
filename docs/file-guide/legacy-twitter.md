# 旧版 Twitter / X 代码

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 207 个文件。

保留的旧版补丁和扩展；当前 NewX 构建排除这些实现，不能据此判断 12.34 已支持相应功能。

## `extensions/twitter`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../extensions/twitter/build.gradle.kts) | 配置 extensions/twitter 模块的依赖、编译及打包 |

## `extensions/twitter/src/main`

| 文件名 | 作用 |
| --- | --- |
| [AndroidManifest.xml](../../extensions/twitter/src/main/AndroidManifest.xml) | 声明本扩展或占位模块的 Android 组件、权限与应用信息 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter`

| 文件名 | 作用 |
| --- | --- |
| [Pref.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/Pref.java) | 旧版 Twitter / X：读取和保存应用功能设置 |
| [Utils.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/Utils.java) | 旧版 Twitter / X：提供扩展共用的页面、资源和模型辅助操作 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/entity`

| 文件名 | 作用 |
| --- | --- |
| [Debug.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/Debug.java) | 旧版 Twitter / X：提供原生对象字段的调试访问 |
| [ExtMediaEntities.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/ExtMediaEntities.java) | 旧版 Twitter / X：帖子附加媒体数据的访问契约或注入适配 |
| [Media.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/Media.java) | 旧版 Twitter / X：媒体数据的访问契约或注入适配 |
| [Tweet.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/Tweet.java) | 旧版 Twitter / X：帖子数据的访问契约或注入适配 |
| [TweetInfo.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/TweetInfo.java) | 旧版 Twitter / X：帖子信息数据的访问契约或注入适配 |
| [TwitterUser.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/TwitterUser.java) | 旧版 Twitter / X：用户数据的访问契约或注入适配 |
| [Video.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/entity/Video.java) | 旧版 Twitter / X：视频数据的访问契约或注入适配 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches`

| 文件名 | 作用 |
| --- | --- |
| [Changelogs.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/Changelogs.java) | 旧版 Twitter / X：展示版本更新说明 |
| [DatabasePatch.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/DatabasePatch.java) | 旧版 Twitter / X：清理本地帖子数据库中的记录 |
| [DownloadPatch.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/DownloadPatch.java) | 旧版 Twitter / X：接通媒体下载操作 |
| [FeatureSwitchPatch.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/FeatureSwitchPatch.java) | 旧版 Twitter / X：读取并应用实验功能开关覆盖 |
| [InlineDownloadButton.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/InlineDownloadButton.java) | 旧版 Twitter / X：处理帖子动作栏下载按钮 |
| [TimelineEntry.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/TimelineEntry.java) | 旧版 Twitter / X：按设置过滤原生时间线条目 |
| [TimelinePosition.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/TimelinePosition.java) | 旧版 Twitter / X：保存和恢复时间线滚动位置 |
| [TweetInfo.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/TweetInfo.java) | 旧版 Twitter / X：处理帖子信息和来源显示 |
| [VersionCheckPatch.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/VersionCheckPatch.java) | 旧版 Twitter / X：识别宿主版本并向扩展提供版本判断 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise`

| 文件名 | 作用 |
| --- | --- |
| [Customise.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/Customise.java) | 旧版 Twitter / X：按配置调整导航、标签和搜索联想等界面条目 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/appIcon`

| 文件名 | 作用 |
| --- | --- |
| [AppIconManager.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/appIcon/AppIconManager.java) | 旧版 Twitter / X：“应用图标选择”的运行时处理 |
| [IconListAdapter.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/appIcon/IconListAdapter.java) | 旧版 Twitter / X：“应用图标选择”的列表适配器 |
| [IconListBuilder.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/appIcon/IconListBuilder.java) | 旧版 Twitter / X：“应用图标选择”的运行时处理 |
| [IconSelectorFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/appIcon/IconSelectorFragment.java) | 旧版 Twitter / X：“应用图标选择”的设置页面 |
| [RowItem.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/appIcon/RowItem.java) | 旧版 Twitter / X：“应用图标选择”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/font`

| 文件名 | 作用 |
| --- | --- |
| [FontPickerFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/font/FontPickerFragment.java) | 旧版 Twitter / X：“自定义字体”的设置页面 |
| [UpdateFont.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/customise/font/UpdateFont.java) | 旧版 Twitter / X：载入并应用所选自定义字体 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/links`

| 文件名 | 作用 |
| --- | --- |
| [ExternalDownloader.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/links/ExternalDownloader.java) | 旧版 Twitter / X：“链接处理”的运行时处理 |
| [HandleCustomDeepLinksPatch.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/links/HandleCustomDeepLinksPatch.java) | 旧版 Twitter / X：处理自定义域名链接的跳转 |
| [Urls.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/links/Urls.java) | 旧版 Twitter / X：“链接处理”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/loggers`

| 文件名 | 作用 |
| --- | --- |
| [ResponseLogger.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/loggers/ResponseLogger.java) | 旧版 Twitter / X：“日志记录”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/logintoken`

| 文件名 | 作用 |
| --- | --- |
| [ExportLoginTokenFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/logintoken/ExportLoginTokenFragment.java) | 旧版 Twitter / X：“登录令牌导入导出”的设置页面 |
| [ImportExportLoginTokenPatch.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/logintoken/ImportExportLoginTokenPatch.java) | 旧版 Twitter / X：接入登录令牌的导入与导出 |
| [ImportLoginTokenDialogFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/logintoken/ImportLoginTokenDialogFragment.java) | 旧版 Twitter / X：“登录令牌导入导出”的设置页面 |
| [ImportLoginTokenFromTextDialogFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/logintoken/ImportLoginTokenFromTextDialogFragment.java) | 旧版 Twitter / X：“登录令牌导入导出”的设置页面 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures`

| 文件名 | 作用 |
| --- | --- |
| [ButtonPressHandler.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/ButtonPressHandler.java) | 旧版 Twitter / X：分发原生菜单扩展按钮的点击操作 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/downloader`

| 文件名 | 作用 |
| --- | --- |
| [DownloadDialog.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/downloader/DownloadDialog.java) | 旧版 Twitter / X：“媒体下载”的运行时处理 |
| [DownloadItem.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/downloader/DownloadItem.java) | 旧版 Twitter / X：“媒体下载”的运行时处理 |
| [NativeDownloader.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/downloader/NativeDownloader.java) | 旧版 Twitter / X：“媒体下载”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/readerMode`

| 文件名 | 作用 |
| --- | --- |
| [ReaderModeFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/readerMode/ReaderModeFragment.java) | 旧版 Twitter / X：“原生阅读模式”的设置页面 |
| [ReaderModeTemplate.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/readerMode/ReaderModeTemplate.java) | 旧版 Twitter / X：“原生阅读模式”的运行时处理 |
| [ReaderModeUtils.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/readerMode/ReaderModeUtils.java) | 旧版 Twitter / X：“原生阅读模式”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/shareImage`

| 文件名 | 作用 |
| --- | --- |
| [ShareImageHandler.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/shareImage/ShareImageHandler.java) | 旧版 Twitter / X：“帖子分享为图片”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/shareMenu`

| 文件名 | 作用 |
| --- | --- |
| [BottomSheetAction.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/shareMenu/BottomSheetAction.java) | 旧版 Twitter / X：“分享菜单”的运行时处理 |
| [BottomSheetBuilder.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/shareMenu/BottomSheetBuilder.java) | 旧版 Twitter / X：“分享菜单”的运行时处理 |
| [BottomSheetHelper.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/shareMenu/BottomSheetHelper.java) | 旧版 Twitter / X：“分享菜单”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator`

| 文件名 | 作用 |
| --- | --- |
| [Constants.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/Constants.java) | 旧版 Twitter / X：定义扩展常量及宿主类型描述符 |
| [DialogBox.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/DialogBox.java) | 旧版 Twitter / X：“原生翻译”的运行时处理 |
| [NativeTranslator.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/NativeTranslator.java) | 旧版 Twitter / X：“原生翻译”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/providers`

| 文件名 | 作用 |
| --- | --- |
| [FxTwitterTranslate.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/providers/FxTwitterTranslate.java) | 旧版 Twitter / X：“翻译服务适配”的运行时处理 |
| [GTranslate.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/providers/GTranslate.java) | 旧版 Twitter / X：“翻译服务适配”的运行时处理 |
| [GTranslateV2.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/providers/GTranslateV2.java) | 旧版 Twitter / X：“翻译服务适配”的运行时处理 |
| [Translate.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/nativeFeatures/translator/providers/Translate.java) | 旧版 Twitter / X：“翻译服务适配”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/profile`

| 文件名 | 作用 |
| --- | --- |
| [MoreProfileInfo.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/profile/MoreProfileInfo.java) | 旧版 Twitter / X：“用户资料”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/tweet`

| 文件名 | 作用 |
| --- | --- |
| [TweetInfoAPI.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/patches/tweet/TweetInfoAPI.java) | 旧版 Twitter / X：“帖子数据”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/settings`

| 文件名 | 作用 |
| --- | --- |
| [ActivityHook.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/ActivityHook.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [DeepLink.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/DeepLink.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [ExternalSettingsRowHighlighter.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/ExternalSettingsRowHighlighter.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [PreferenceBuildTarget.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/PreferenceBuildTarget.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [ScreenBuilder.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/ScreenBuilder.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [Settings.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/Settings.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchColors.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchColors.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchIndex.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchIndex.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchMatcher.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchMatcher.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchNavigator.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchNavigator.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchOwnerTracker.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchOwnerTracker.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchSession.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchSession.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsSearchUIController.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsSearchUIController.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |
| [SettingsStatus.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/SettingsStatus.java) | 旧版 Twitter / X：“设置页面和配置”的运行时处理 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags`

| 文件名 | 作用 |
| --- | --- |
| [FeatureFlag.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlag.java) | 旧版 Twitter / X：“实验功能开关配置”的运行时处理 |
| [FeatureFlagAdapter.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlagAdapter.java) | 旧版 Twitter / X：“实验功能开关配置”的列表适配器 |
| [FeatureFlagAudit.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlagAudit.java) | 旧版 Twitter / X：“实验功能开关配置”的运行时处理 |
| [FeatureFlagCatalog.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlagCatalog.java) | 旧版 Twitter / X：“实验功能开关配置”的运行时处理 |
| [FeatureFlagSearchAdapter.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlagSearchAdapter.java) | 旧版 Twitter / X：“实验功能开关配置”的列表适配器 |
| [FeatureFlagSelection.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlagSelection.java) | 旧版 Twitter / X：“实验功能开关配置”的运行时处理 |
| [FeatureFlagsFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/featureflags/FeatureFlagsFragment.java) | 旧版 Twitter / X：“实验功能开关配置”的设置页面 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/fragments`

| 文件名 | 作用 |
| --- | --- |
| [BackupPrefFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/fragments/BackupPrefFragment.java) | 旧版 Twitter / X：“设置子页面”的设置页面 |
| [PageFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/fragments/PageFragment.java) | 旧版 Twitter / X：“设置子页面”的设置页面 |
| [RestorePrefFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/fragments/RestorePrefFragment.java) | 旧版 Twitter / X：“设置子页面”的设置页面 |
| [SettingsAboutFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/fragments/SettingsAboutFragment.java) | 旧版 Twitter / X：“设置子页面”的设置页面 |
| [SettingsFragment.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/fragments/SettingsFragment.java) | 旧版 Twitter / X：“设置子页面”的设置页面 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets`

| 文件名 | 作用 |
| --- | --- |
| [ButtonPref.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/ButtonPref.java) | 旧版 Twitter / X：“设置控件”的配置或状态存储 |
| [EditTextPref.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/EditTextPref.java) | 旧版 Twitter / X：“设置控件”的配置或状态存储 |
| [Helper.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/Helper.java) | 旧版 Twitter / X：“设置控件”的运行时处理 |
| [ListPref.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/ListPref.java) | 旧版 Twitter / X：“设置控件”的配置或状态存储 |
| [MultiSelectListPref.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/MultiSelectListPref.java) | 旧版 Twitter / X：“设置控件”的配置或状态存储 |
| [PikoSettingsButton.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/PikoSettingsButton.java) | 旧版 Twitter / X：“设置控件”的界面控件 |
| [SwitchPref.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/settings/widgets/SwitchPref.java) | 旧版 Twitter / X：“设置控件”的配置或状态存储 |

## `extensions/twitter/src/main/java/app/morphe/extension/twitter/utils`

| 文件名 | 作用 |
| --- | --- |
| [ViewUtils.java](../../extensions/twitter/src/main/java/app/morphe/extension/twitter/utils/ViewUtils.java) | 旧版 Twitter / X：“通用辅助逻辑”的运行时处理 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/ads/timelineEntryHook`

| 文件名 | 作用 |
| --- | --- |
| [HideAds.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/ads/timelineEntryHook/HideAds.kt) | 旧版 Twitter / X：过滤时间线广告 |
| [HideRecommendationItemsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/ads/timelineEntryHook/HideRecommendationItemsPatch.kt) | 旧版 Twitter / X：过滤推荐内容条目 |
| [TimelineEntryHookPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/ads/timelineEntryHook/TimelineEntryHookPatch.kt) | 旧版 Twitter / X：接通时间线条目读取与过滤 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/entity`

| 文件名 | 作用 |
| --- | --- |
| [EntityGenerator.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/EntityGenerator.kt) | 旧版 Twitter / X：“原生数据访问接口”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/entity/decoder`

| 文件名 | 作用 |
| --- | --- |
| [Decoder.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/decoder/Decoder.kt) | 旧版 Twitter / X：“原生数据解码”的模型接口或接口注入适配 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/decoder/Fingerprint.kt) | 旧版 Twitter / X：定位“原生数据解码”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/entity/extMedia`

| 文件名 | 作用 |
| --- | --- |
| [ExtMediaEntityPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/extMedia/ExtMediaEntityPatch.kt) | 旧版 Twitter / X：“帖子媒体实体”的模型接口或接口注入适配 |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/extMedia/Fingerprints.kt) | 旧版 Twitter / X：定位“帖子媒体实体”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/entity/tweet`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/tweet/Fingerprints.kt) | 旧版 Twitter / X：定位“帖子数据”对应的原生方法或字段 |
| [TweetEntityPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/tweet/TweetEntityPatch.kt) | 旧版 Twitter / X：“帖子数据”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/entity/tweetInfo`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/tweetInfo/Fingerprints.kt) | 旧版 Twitter / X：定位“帖子信息模型”对应的原生方法或字段 |
| [TweetInfoEntityPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/tweetInfo/TweetInfoEntityPatch.kt) | 旧版 Twitter / X：“帖子信息模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/entity/twitterUser`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/twitterUser/Fingerprints.kt) | 旧版 Twitter / X：定位“用户数据模型”对应的原生方法或字段 |
| [TwitterUserEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/entity/twitterUser/TwitterUserEntity.kt) | 旧版 Twitter / X：“用户数据模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag`

| 文件名 | 作用 |
| --- | --- |
| [DisableChirpFontPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/DisableChirpFontPatch.kt) | 旧版 Twitter / X：禁用 Chirp 字体 |
| [HideBookmarkInTimelinePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/HideBookmarkInTimelinePatch.kt) | 旧版 Twitter / X：隐藏时间线中的书签按钮 |
| [HideFABMenuButtonsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/HideFABMenuButtonsPatch.kt) | 旧版 Twitter / X：隐藏悬浮发布菜单中的指定按钮 |
| [HideImmersivePlayer.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/HideImmersivePlayer.kt) | 旧版 Twitter / X：隐藏播放器中上滑查看更多视频的入口 |
| [RemoveViewCountPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/RemoveViewCountPatch.kt) | 旧版 Twitter / X：隐藏帖子浏览次数 |
| [RestoreLegacyFollowerListsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/RestoreLegacyFollowerListsPatch.kt) | 旧版 Twitter / X：恢复旧式关注与粉丝列表 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/featureFlagPatch`

| 文件名 | 作用 |
| --- | --- |
| [FeatureFlagPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/featureFlagPatch/FeatureFlagPatch.kt) | 旧版 Twitter / X：“实验功能开关接入”的补丁实现 |
| [FeatureFlagResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/featureFlagPatch/FeatureFlagResourcePatch.kt) | 旧版 Twitter / X：提供实验功能开关界面资源 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/featureFlagPatch/fingerprints`

| 文件名 | 作用 |
| --- | --- |
| [FeatureFlagLoadFingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/featureFlag/featureFlagPatch/fingerprints/FeatureFlagLoadFingerprint.kt) | 旧版 Twitter / X：定位“实验功能开关接入”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/interaction/downloads/changedirectory`

| 文件名 | 作用 |
| --- | --- |
| [ChangeDownloadDirPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/interaction/downloads/changedirectory/ChangeDownloadDirPatch.kt) | 旧版 Twitter / X：接入自定义媒体保存目录 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/interaction/downloads/copyMediaLink`

| 文件名 | 作用 |
| --- | --- |
| [CopyMediaLink.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/interaction/downloads/copyMediaLink/CopyMediaLink.kt) | 旧版 Twitter / X：添加复制媒体链接操作 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/link/cleartrackingparams`

| 文件名 | 作用 |
| --- | --- |
| [ClearTrackingParamsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/cleartrackingparams/ClearTrackingParamsPatch.kt) | 旧版 Twitter / X：清理分享链接的追踪参数 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/link/customDeeplinks`

| 文件名 | 作用 |
| --- | --- |
| [CustomDeepLinksPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/customDeeplinks/CustomDeepLinksPatch.kt) | 旧版 Twitter / X：接入自定义域名的深层链接 |
| [HandleCustomDeepLinksPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/customDeeplinks/HandleCustomDeepLinksPatch.kt) | 旧版 Twitter / X：处理自定义域名链接的跳转 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/link/customsharingdomain`

| 文件名 | 作用 |
| --- | --- |
| [CustomSharingDomainPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/customsharingdomain/CustomSharingDomainPatch.kt) | 旧版 Twitter / X：替换分享链接使用的域名 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/link/handlemodernsharesheetlinks`

| 文件名 | 作用 |
| --- | --- |
| [HandleModernShareSheetLinks.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/handlemodernsharesheetlinks/HandleModernShareSheetLinks.kt) | 旧版 Twitter / X：处理新版分享面板生成的链接 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/link/legacyShareLinks`

| 文件名 | 作用 |
| --- | --- |
| [LegacyShareLinksPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/legacyShareLinks/LegacyShareLinksPatch.kt) | 旧版 Twitter / X：生成旧式分享链接 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/link/unshorten`

| 文件名 | 作用 |
| --- | --- |
| [NoShortenedUrlPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/link/unshorten/NoShortenedUrlPatch.kt) | 旧版 Twitter / X：展开短链接，使用原始地址 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/logging/responseLogging`

| 文件名 | 作用 |
| --- | --- |
| [ResponseLogging.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/logging/responseLogging/ResponseLogging.kt) | 旧版 Twitter / X：记录服务端响应用于调试 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/appicon`

| 文件名 | 作用 |
| --- | --- |
| [AppIconPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/appicon/AppIconPatch.kt) | 旧版 Twitter / X：接入应用图标选择 |
| [AppIconResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/appicon/AppIconResourcePatch.kt) | 旧版 Twitter / X：合入可选应用图标资源 |
| [XMLfiles.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/appicon/XMLfiles.kt) | 旧版 Twitter / X：列出图标替换涉及的 XML 资源 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/blockRedirectToXLite`

| 文件名 | 作用 |
| --- | --- |
| [BlockRedirectingToXLitePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/blockRedirectToXLite/BlockRedirectingToXLitePatch.kt) | 旧版 Twitter / X：阻止自动跳转到 X-Lite |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/blockUpdateScreen`

| 文件名 | 作用 |
| --- | --- |
| [BlockUpdateScreenPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/blockUpdateScreen/BlockUpdateScreenPatch.kt) | 旧版 Twitter / X：屏蔽阻止使用的更新页面 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/bringbacktwitter`

| 文件名 | 作用 |
| --- | --- |
| [BringBackTwitterPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/bringbacktwitter/BringBackTwitterPatch.kt) | 旧版 Twitter / X：恢复 Twitter 名称与品牌图标 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/crashLogs`

| 文件名 | 作用 |
| --- | --- |
| [CrashLogsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/crashLogs/CrashLogsPatch.kt) | 旧版 Twitter / X：接入崩溃日志收集 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/exploretabs`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseExploreTabsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/exploretabs/CustomiseExploreTabsPatch.kt) | 旧版 Twitter / X：自定义探索页标签 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/font`

| 文件名 | 作用 |
| --- | --- |
| [CustomEmojiFont.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/font/CustomEmojiFont.kt) | 旧版 Twitter / X：接入自定义表情字体 |
| [CustomFont.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/font/CustomFont.kt) | 旧版 Twitter / X：接入自定义文字字体 |
| [CustomiseFontHook.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/font/CustomiseFontHook.kt) | 旧版 Twitter / X：接通字体选择与原生字体调用 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/inlinebar`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseInlineBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/inlinebar/CustomiseInlineBarPatch.kt) | 旧版 Twitter / X：自定义帖子动作栏按钮 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/navbar`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseNavBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/navbar/CustomiseNavBarPatch.kt) | 旧版 Twitter / X：自定义底部导航条目 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/notificationtabs`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseNotificationTabsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/notificationtabs/CustomiseNotificationTabsPatch.kt) | 旧版 Twitter / X：自定义通知页标签 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/postFontSize`

| 文件名 | 作用 |
| --- | --- |
| [CustomizePostFontSize.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/postFontSize/CustomizePostFontSize.kt) | 旧版 Twitter / X：自定义帖子文字大小 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/profiletabs`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseProfileTabsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/profiletabs/CustomiseProfileTabsPatch.kt) | 旧版 Twitter / X：自定义用户主页标签 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/replySorting`

| 文件名 | 作用 |
| --- | --- |
| [DefaultReplySortingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/replySorting/DefaultReplySortingPatch.kt) | 旧版 Twitter / X：设置默认回复排序 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/searchtabs`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseSearchTabsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/searchtabs/CustomiseSearchTabsPatch.kt) | 旧版 Twitter / X：自定义搜索结果标签 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/sidebar`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseSideBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/sidebar/CustomiseSideBarPatch.kt) | 旧版 Twitter / X：自定义侧边栏条目 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/timelinetabs`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseTimelineTabsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/timelinetabs/CustomiseTimelineTabsPatch.kt) | 旧版 Twitter / X：自定义首页时间线标签 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/typeAheadResponse`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseTypeAheadResponsePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/customize/typeAheadResponse/CustomiseTypeAheadResponsePatch.kt) | 旧版 Twitter / X：自定义搜索输入联想结果 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/dynamiccolor`

| 文件名 | 作用 |
| --- | --- |
| [DynamicColorPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/dynamiccolor/DynamicColorPatch.kt) | 旧版 Twitter / X：“动态配色”的补丁实现 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/extension`

| 文件名 | 作用 |
| --- | --- |
| [ExtensionPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/extension/ExtensionPatch.kt) | 旧版 Twitter / X：把扩展合入安装包并接入初始化 |
| [Hooks.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/extension/Hooks.kt) | 旧版 Twitter / X：定义扩展初始化需要的原生调用位置 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/fab`

| 文件名 | 作用 |
| --- | --- |
| [HideFABPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/fab/HideFABPatch.kt) | 旧版 Twitter / X：隐藏悬浮发布按钮 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/login`

| 文件名 | 作用 |
| --- | --- |
| [ImportExportLoginTokenPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/login/ImportExportLoginTokenPatch.kt) | 旧版 Twitter / X：接入登录令牌的导入与导出 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/moreProfileInfo`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/moreProfileInfo/Fingerprints.kt) | 旧版 Twitter / X：定位“额外用户资料”对应的原生方法或字段 |
| [MoreProfileInfoPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/moreProfileInfo/MoreProfileInfoPatch.kt) | 旧版 Twitter / X：显示额外用户资料 |
| [MoreProfileInfoResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/moreProfileInfo/MoreProfileInfoResourcePatch.kt) | 旧版 Twitter / X：提供额外用户资料的界面资源 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/recommendedusers`

| 文件名 | 作用 |
| --- | --- |
| [HideRecommendedUsersPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/recommendedusers/HideRecommendedUsersPatch.kt) | 旧版 Twitter / X：隐藏推荐用户 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/roundOffNumbers`

| 文件名 | 作用 |
| --- | --- |
| [RoundOffNumbersPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/roundOffNumbers/RoundOffNumbersPatch.kt) | 旧版 Twitter / X：调整关注数等数字的缩写显示 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/searchsuggestions`

| 文件名 | 作用 |
| --- | --- |
| [PauseSearchSuggestionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/searchsuggestions/PauseSearchSuggestionsPatch.kt) | 旧版 Twitter / X：暂停搜索建议更新 |
| [RemoveSearchSuggestionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/searchsuggestions/RemoveSearchSuggestionsPatch.kt) | 旧版 Twitter / X：移除搜索建议 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/selectabletext`

| 文件名 | 作用 |
| --- | --- |
| [SelectableTextPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/selectabletext/SelectableTextPatch.kt) | 旧版 Twitter / X：允许选择和复制文字 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/settings`

| 文件名 | 作用 |
| --- | --- |
| [CheckCompatibilityPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/settings/CheckCompatibilityPatch.kt) | 旧版 Twitter / X：检查设置与宿主版本兼容情况 |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/settings/Fingerprints.kt) | 旧版 Twitter / X：定位“设置页面和配置”对应的原生方法或字段 |
| [SettingsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/settings/SettingsPatch.kt) | 旧版 Twitter / X：把 Piko 设置入口接入原生页面 |
| [SettingsResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/settings/SettingsResourcePatch.kt) | 旧版 Twitter / X：合入 Piko 设置页面资源 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/browseObject`

| 文件名 | 作用 |
| --- | --- |
| [BrowseTweetObjectPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/browseObject/BrowseTweetObjectPatch.kt) | 旧版 Twitter / X：在帖子菜单加入对象浏览入口 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/debugMenu`

| 文件名 | 作用 |
| --- | --- |
| [DebugMenu.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/debugMenu/DebugMenu.kt) | 旧版 Twitter / X：提供帖子调试菜单 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/externalDownloader`

| 文件名 | 作用 |
| --- | --- |
| [ExternalDownloaderPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/externalDownloader/ExternalDownloaderPatch.kt) | 旧版 Twitter / X：接入外部下载器操作 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/fingerprints`

| 文件名 | 作用 |
| --- | --- |
| [ActionEnumsFingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/fingerprints/ActionEnumsFingerprint.kt) | 旧版 Twitter / X：定位“分享菜单”对应的原生方法或字段 |
| [ShareMenuButtonFuncCallFingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/fingerprints/ShareMenuButtonFuncCallFingerprint.kt) | 旧版 Twitter / X：定位“分享菜单”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/hooks`

| 文件名 | 作用 |
| --- | --- |
| [ShareMenuButtonAddHook.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/hooks/ShareMenuButtonAddHook.kt) | 旧版 Twitter / X：“分享菜单”的原生调用接入 |
| [ShareMenuButtonInitHook.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/hooks/ShareMenuButtonInitHook.kt) | 旧版 Twitter / X：“分享菜单”的原生调用接入 |
| [ShareMenuButtonOnClickHook.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/hooks/ShareMenuButtonOnClickHook.kt) | 旧版 Twitter / X：“分享菜单”的原生调用接入 |
| [Util.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/hooks/Util.kt) | 旧版 Twitter / X：“分享菜单”的补丁实现 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeDownloader`

| 文件名 | 作用 |
| --- | --- |
| [InlineDownloadButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeDownloader/InlineDownloadButtonPatch.kt) | 旧版 Twitter / X：向帖子动作栏加入下载按钮 |
| [NativeDownloaderPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeDownloader/NativeDownloaderPatch.kt) | 旧版 Twitter / X：接入原生媒体下载流程 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeReaderMode`

| 文件名 | 作用 |
| --- | --- |
| [NativeReaderModePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeReaderMode/NativeReaderModePatch.kt) | 旧版 Twitter / X：接入原生阅读模式 |
| [NativeReaderModeResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeReaderMode/NativeReaderModeResourcePatch.kt) | 旧版 Twitter / X：提供阅读模式所需资源 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeShareImage`

| 文件名 | 作用 |
| --- | --- |
| [NativeShareImagePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeShareImage/NativeShareImagePatch.kt) | 旧版 Twitter / X：接入帖子分享为图片 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeShareMenu`

| 文件名 | 作用 |
| --- | --- |
| [NativeShareMenuPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeShareMenu/NativeShareMenuPatch.kt) | 旧版 Twitter / X：接入原生分享菜单扩展 |
| [NativeShareMenuResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeShareMenu/NativeShareMenuResourcePatch.kt) | 旧版 Twitter / X：提供原生分享菜单扩展资源 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeTranslator`

| 文件名 | 作用 |
| --- | --- |
| [NativeTranslatorPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/shareMenu/nativeTranslator/NativeTranslatorPatch.kt) | 旧版 Twitter / X：接入原生翻译操作 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/misc/showchangelogs`

| 文件名 | 作用 |
| --- | --- |
| [ShowChangelogsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/misc/showchangelogs/ShowChangelogsPatch.kt) | 旧版 Twitter / X：接入更新说明入口 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/premium/enableForcePip`

| 文件名 | 作用 |
| --- | --- |
| [EnableForcePipPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/premium/enableForcePip/EnableForcePipPatch.kt) | 旧版 Twitter / X：接入强制画中画功能 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/premium/redirectBMNavBar`

| 文件名 | 作用 |
| --- | --- |
| [RedirectBMTab.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/premium/redirectBMNavBar/RedirectBMTab.kt) | 旧版 Twitter / X：把导航项重定向到书签页 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/premium/undoposts`

| 文件名 | 作用 |
| --- | --- |
| [EnableUndoPostPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/premium/undoposts/EnableUndoPostPatch.kt) | 旧版 Twitter / X：接入撤回发帖功能 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/premium/unlockdownloads`

| 文件名 | 作用 |
| --- | --- |
| [DownloadPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/premium/unlockdownloads/DownloadPatch.kt) | 旧版 Twitter / X：接通媒体下载操作 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/banner`

| 文件名 | 作用 |
| --- | --- |
| [HideBannerPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/banner/HideBannerPatch.kt) | 旧版 Twitter / X：隐藏时间线提示横幅 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/deleteFromDatabase`

| 文件名 | 作用 |
| --- | --- |
| [DeleteFromDatabasePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/deleteFromDatabase/DeleteFromDatabasePatch.kt) | 旧版 Twitter / X：接入本地帖子数据库清理 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/disableAutoScroll`

| 文件名 | 作用 |
| --- | --- |
| [DisableAutoScrollPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/disableAutoScroll/DisableAutoScrollPatch.kt) | 旧版 Twitter / X：控制时间线的自动滚动 |
| [RestoreTimelinePosition.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/disableAutoScroll/RestoreTimelinePosition.kt) | 旧版 Twitter / X：接入时间线滚动位置恢复 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/enableVidAutoAdvance`

| 文件名 | 作用 |
| --- | --- |
| [EnableVidAutoAdvancePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/enableVidAutoAdvance/EnableVidAutoAdvancePatch.kt) | 旧版 Twitter / X：接入视频自动切换 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/forceHD`

| 文件名 | 作用 |
| --- | --- |
| [ForceHDPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/forceHD/ForceHDPatch.kt) | 旧版 Twitter / X：设置优先使用高清视频 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideCommunityBadge`

| 文件名 | 作用 |
| --- | --- |
| [HideCommunityBadge.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideCommunityBadge/HideCommunityBadge.kt) | 旧版 Twitter / X：隐藏社区徽章 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideHiddenReplies`

| 文件名 | 作用 |
| --- | --- |
| [HideHiddenRepliesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideHiddenReplies/HideHiddenRepliesPatch.kt) | 旧版 Twitter / X：隐藏折叠回复提示 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideNavbarBadges`

| 文件名 | 作用 |
| --- | --- |
| [HideNavBarBadges.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideNavbarBadges/HideNavBarBadges.kt) | 旧版 Twitter / X：隐藏底部导航角标 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideNudgeButtons`

| 文件名 | 作用 |
| --- | --- |
| [HideNudgeButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideNudgeButtons/HideNudgeButtonPatch.kt) | 旧版 Twitter / X：隐藏推广引导按钮 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hidePostMetrics`

| 文件名 | 作用 |
| --- | --- |
| [HidePostMetrics.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hidePostMetrics/HidePostMetrics.kt) | 旧版 Twitter / X：隐藏帖子互动统计 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideSocialProof`

| 文件名 | 作用 |
| --- | --- |
| [HideSoialProofPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/hideSocialProof/HideSoialProofPatch.kt) | 旧版 Twitter / X：隐藏社交关系推荐提示 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/live`

| 文件名 | 作用 |
| --- | --- |
| [HideLiveThreadsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/live/HideLiveThreadsPatch.kt) | 旧版 Twitter / X：隐藏直播串流内容 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/removePremiumUpsell`

| 文件名 | 作用 |
| --- | --- |
| [RemovePremiumUpsellPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/removePremiumUpsell/RemovePremiumUpsellPatch.kt) | 旧版 Twitter / X：隐藏 Premium 升级推广 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/sensitivemediasettings`

| 文件名 | 作用 |
| --- | --- |
| [ShowSensitiveMediaPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/sensitivemediasettings/ShowSensitiveMediaPatch.kt) | 旧版 Twitter / X：允许显示敏感媒体 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/showSourceLabel`

| 文件名 | 作用 |
| --- | --- |
| [ShowSourceLabel.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/showSourceLabel/ShowSourceLabel.kt) | 旧版 Twitter / X：显示帖子来源应用 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/showpollresults`

| 文件名 | 作用 |
| --- | --- |
| [ShowPollResultsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/showpollresults/ShowPollResultsPatch.kt) | 旧版 Twitter / X：显示投票结果 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/timeline/tweetinfo`

| 文件名 | 作用 |
| --- | --- |
| [ForceTranslate.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/tweetinfo/ForceTranslate.kt) | 旧版 Twitter / X：调整翻译入口显示策略 |
| [HideCommunityNotes.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/tweetinfo/HideCommunityNotes.kt) | 旧版 Twitter / X：隐藏社区附注 |
| [HidePromoteButton.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/tweetinfo/HidePromoteButton.kt) | 旧版 Twitter / X：隐藏帖子推广按钮 |
| [TweetInfoHook.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/timeline/tweetinfo/TweetInfoHook.kt) | 旧版 Twitter / X：把帖子信息读取接入原生模型 |

## `patches/src/main/kotlin/app/crimera/patches/twitter/utils`

| 文件名 | 作用 |
| --- | --- |
| [Constants.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/utils/Constants.kt) | 旧版 Twitter / X：定义扩展常量及宿主类型描述符 |
| [Utils.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/utils/Utils.kt) | 旧版 Twitter / X：提供扩展共用的页面、资源和模型辅助操作 |
| [VersionCheckPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/twitter/utils/VersionCheckPatch.kt) | 旧版 Twitter / X：识别宿主版本并向扩展提供版本判断 |
