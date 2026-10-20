# 新版 X 补丁入口

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 86 个文件。

修补安装包时运行的 Kotlin 代码：定位原生实现并注入调用。

## `patches/src/main/kotlin/app/crimera/patches/newx/ads`

| 文件名 | 作用 |
| --- | --- |
| [HideAdsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/ads/HideAdsPatch.kt) | 隐藏新版 X 时间线中的广告条目 |
| [TimelineAdModelAdapterPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/ads/TimelineAdModelAdapterPatch.kt) | 把原生广告模型接入时间线广告过滤逻辑 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/blur`

| 文件名 | 作用 |
| --- | --- |
| [DisableBlurPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/blur/DisableBlurPatch.kt) | 关闭原生界面模糊效果 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/bringbacktwitter`

| 文件名 | 作用 |
| --- | --- |
| [RestoreTwitterBrandingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/bringbacktwitter/RestoreTwitterBrandingPatch.kt) | 把 X 品牌资源替换为 Twitter 名称和图标 |
| [RestoreTwitterTopBarLogoPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/bringbacktwitter/RestoreTwitterTopBarLogoPatch.kt) | 恢复顶部 Twitter 鸟形标志 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/browseobject`

| 文件名 | 作用 |
| --- | --- |
| [BrowseObjectPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/browseobject/BrowseObjectPatch.kt) | 在帖子菜单接入对象浏览与调试入口 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/canonicalurls`

| 文件名 | 作用 |
| --- | --- |
| [CanonicalUrlsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/canonicalurls/CanonicalUrlsPatch.kt) | 把规范原始链接解析接入原生链接打开流程 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/clone`

| 文件名 | 作用 |
| --- | --- |
| [ClonePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/clone/ClonePatch.kt) | 修改应用标识，为与原版共存创建克隆应用 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/crashlogs`

| 文件名 | 作用 |
| --- | --- |
| [CrashLogsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/crashlogs/CrashLogsPatch.kt) | 接入崩溃日志收集、复制和设置入口 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/customfont`

| 文件名 | 作用 |
| --- | --- |
| [CustomFontPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/customfont/CustomFontPatch.kt) | 接入自定义字体替换 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/customlinks`

| 文件名 | 作用 |
| --- | --- |
| [CustomLinksPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/customlinks/CustomLinksPatch.kt) | 接入自定义域名链接的处理和路由 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/customsharingdomain`

| 文件名 | 作用 |
| --- | --- |
| [CustomSharingDomainPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/customsharingdomain/CustomSharingDomainPatch.kt) | 接入自定义分享域名 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/drawer`

| 文件名 | 作用 |
| --- | --- |
| [CustomizeDrawerPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/drawer/CustomizeDrawerPatch.kt) | 修改侧边抽屉条目的显示、顺序和打开方式 |
| [DrawerRowAnalysis.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/drawer/DrawerRowAnalysis.kt) | 分析侧边抽屉条目模型及对应的原生调用 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/dynamiccolor`

| 文件名 | 作用 |
| --- | --- |
| [DynamicColorFingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/dynamiccolor/DynamicColorFingerprints.kt) | 定位动态配色需要的原生主题方法 |
| [DynamicColorPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/dynamiccolor/DynamicColorPatch.kt) | 把动态配色和主题调色板接入原生界面 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/extension`

| 文件名 | 作用 |
| --- | --- |
| [ExtensionPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/extension/ExtensionPatch.kt) | 把 NewX 扩展及初始化调用合入安装包 |
| [Hooks.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/extension/Hooks.kt) | 定义 NewX 扩展初始化所需的宿主调用位置 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/featureflags`

| 文件名 | 作用 |
| --- | --- |
| [FeatureFlagPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/featureflags/FeatureFlagPatch.kt) | 接入实验功能开关覆盖 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions`

| 文件名 | 作用 |
| --- | --- |
| [ClassicInlineActionSpacingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/ClassicInlineActionSpacingPatch.kt) | 接入帖子动作栏的经典间距 |
| [CoilThumbnailCachePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/CoilThumbnailCachePatch.kt) | 接入 Coil 图片缩略图缓存 |
| [CustomizeInlineActionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/CustomizeInlineActionsPatch.kt) | 调整帖子下方动作按钮的显示与顺序 |
| [DownloadBannerContentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/DownloadBannerContentPatch.kt) | 让下载通知完整换行，并在原生通知内绘制进度条 |
| [GlideThumbnailCachePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/GlideThumbnailCachePatch.kt) | 接入 Glide 图片缩略图缓存 |
| [InAppNotificationPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/InAppNotificationPatch.kt) | 接通 X 原生顶部通知的模型与发送入口 |
| [InlineDownloadButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/InlineDownloadButtonPatch.kt) | 在帖子动作栏加入媒体下载按钮 |
| [NativeDownloadRedirectPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/NativeDownloadRedirectPatch.kt) | 把 X 原生下载操作重定向到所选保存目录 |
| [ThumbnailCachePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/ThumbnailCachePatch.kt) | 协调缩略图缓存补丁的依赖与接入 |
| [UnifiedDownloadNotificationPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/inlineactions/UnifiedDownloadNotificationPatch.kt) | 统一下载进度、完成、失败和取消的顶部通知及生命周期 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/mediaquality`

| 文件名 | 作用 |
| --- | --- |
| [ForceHighestVideoQualityPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/mediaquality/ForceHighestVideoQualityPatch.kt) | 接入视频清晰度选择与高画质策略 |
| [PreferredVideoResolution.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/mediaquality/PreferredVideoResolution.kt) | 解析所选视频分辨率及其匹配规则 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/mediasheet`

| 文件名 | 作用 |
| --- | --- |
| [CustomizeMediaSheetOptionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/mediasheet/CustomizeMediaSheetOptionsPatch.kt) | 调整媒体操作面板中显示的选项 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/mediatab`

| 文件名 | 作用 |
| --- | --- |
| [DefaultMediaTabPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/mediatab/DefaultMediaTabPatch.kt) | 设置主页默认打开的媒体标签页 |
| [ProfileBulkDownloadPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/mediatab/ProfileBulkDownloadPatch.kt) | 接入主页全部图片、视频和 GIF 的批量下载入口 |
| [ProfilePhotosGalleryPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/mediatab/ProfilePhotosGalleryPatch.kt) | 把主页照片标签页接入照片画廊 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar`

| 文件名 | 作用 |
| --- | --- |
| [FixedBottomBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar/FixedBottomBarPatch.kt) | 固定手机主底栏的滚动可见状态 |
| [FloatingGlassNavBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar/FloatingGlassNavBarPatch.kt) | 为手机主底栏接入悬浮圆角、玻璃背景和边缘效果 |
| [NavBarCustomizationPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar/NavBarCustomizationPatch.kt) | 接入底部导航的隐藏、排序和替换 |
| [NavBarTabData.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/navbar/NavBarTabData.kt) | 识别和读取底部导航标签的数据结构 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/postdetails`

| 文件名 | 作用 |
| --- | --- |
| [HidePostReplyBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/postdetails/HidePostReplyBarPatch.kt) | 隐藏帖子详情底部回复栏并处理留白 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/postoptions`

| 文件名 | 作用 |
| --- | --- |
| [CustomizePostOptionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/postoptions/CustomizePostOptionsPatch.kt) | 接入帖子菜单选项的自定义过滤 |
| [PostOptionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/postoptions/PostOptionsPatch.kt) | 接通原生帖子菜单和扩展操作 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/profilesorting`

| 文件名 | 作用 |
| --- | --- |
| [DefaultProfilePostSortingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/profilesorting/DefaultProfilePostSortingPatch.kt) | 设置主页帖子的默认排序 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/profiletabs`

| 文件名 | 作用 |
| --- | --- |
| [ProfileTabsCustomizationPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/profiletabs/ProfileTabsCustomizationPatch.kt) | 接入主页标签页的显示、顺序和默认选择 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/replysorting`

| 文件名 | 作用 |
| --- | --- |
| [DefaultReplySortingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/replysorting/DefaultReplySortingPatch.kt) | 设置帖子的默认回复排序 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/serverlogging`

| 文件名 | 作用 |
| --- | --- |
| [ServerLoggingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/serverlogging/ServerLoggingPatch.kt) | 接入服务端响应日志收集与导出 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/shareimage`

| 文件名 | 作用 |
| --- | --- |
| [ShareImagePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/shareimage/ShareImagePatch.kt) | 接入把帖子分享为图片的原生功能 |

## `patches/src/main/kotlin/app/crimera/patches/newx/misc/videoscrolling`

| 文件名 | 作用 |
| --- | --- |
| [DisableVideoScrollingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/misc/videoscrolling/DisableVideoScrollingPatch.kt) | 关闭视频上下滑动切换 |

## `patches/src/main/kotlin/app/crimera/patches/newx/models`

| 文件名 | 作用 |
| --- | --- |
| [BooleanModelLabels.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/models/BooleanModelLabels.kt) | 按语义标签识别原生模型中的布尔属性 |
| [PostModels.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/models/PostModels.kt) | 识别帖子、作者、媒体及统计相关的原生模型 |
| [SemanticIntrospection.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/models/SemanticIntrospection.kt) | 提供按字段和方法语义分析原生模型的辅助逻辑 |
| [TimelineModels.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/models/TimelineModels.kt) | 识别时间线、游标、请求和分页结果模型 |

## `patches/src/main/kotlin/app/crimera/patches/newx/premium`

| 文件名 | 作用 |
| --- | --- |
| [DownloadFingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/premium/DownloadFingerprints.kt) | 定位原生下载权限与媒体下载相关方法 |
| [HidePremiumUpsellPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/premium/HidePremiumUpsellPatch.kt) | 隐藏 Premium 升级推广入口 |
| [UnlockColorCustomizationPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/premium/UnlockColorCustomizationPatch.kt) | 接入原生颜色自定义功能 |
| [UnlockDownloadsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/premium/UnlockDownloadsPatch.kt) | 解除原生媒体下载入口的限制 |

## `patches/src/main/kotlin/app/crimera/patches/newx/settings`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/Fingerprints.kt) | 定位新版设置页面及入口对应的原生方法 |
| [NewXSettingsContributions.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/NewXSettingsContributions.kt) | 汇总各项 NewX 功能向设置页注册的选项 |
| [PikoSettingsIcon.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/PikoSettingsIcon.kt) | 向设置入口提供 Piko 图标资源 |
| [SettingsCategories.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/SettingsCategories.kt) | 定义 Piko 设置的分类及排序 |
| [SettingsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/SettingsPatch.kt) | 把 Piko 设置入口接入 X 原生设置页面 |
| [SettingsResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/SettingsResourcePatch.kt) | 合入新版设置需要的文字和图标资源 |
| [SettingsRowModelHook.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/settings/SettingsRowModelHook.kt) | 把 Piko 设置行接入原生设置模型 |

## `patches/src/main/kotlin/app/crimera/patches/newx/timeline`

| 文件名 | 作用 |
| --- | --- |
| [CustomizeTimelineTabsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/CustomizeTimelineTabsPatch.kt) | 调整首页时间线标签页的显示和顺序 |
| [DisableAutomaticContentRefreshPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/DisableAutomaticContentRefreshPatch.kt) | 汇总禁止自动刷新所需的补丁，保留手动刷新与分页 |
| [DisableTimelineRefreshPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/DisableTimelineRefreshPatch.kt) | 在原生时间线请求中识别并拦截自动刷新 |
| [ForYouTopicFilterPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/ForYouTopicFilterPatch.kt) | 接入“为你推荐”主题筛选 |
| [HideAiGeneratedPostsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideAiGeneratedPostsPatch.kt) | 过滤 AI 生成的帖子 |
| [HideDiscoverMorePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideDiscoverMorePatch.kt) | 隐藏“发现更多”推荐内容 |
| [HideNewPostButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideNewPostButtonPatch.kt) | 隐藏新帖按钮 |
| [HideNewPostPillPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideNewPostPillPatch.kt) | 隐藏提示有新帖可读的浮动提示条 |
| [HidePostDividersPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HidePostDividersPatch.kt) | 隐藏帖子之间的分隔线 |
| [HideSpacesBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideSpacesBarPatch.kt) | 隐藏 Spaces 语音空间栏 |
| [HideTimelineTabsBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideTimelineTabsBarPatch.kt) | 隐藏时间线标签栏 |
| [HideWhoToFollowPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/HideWhoToFollowPatch.kt) | 隐藏推荐关注条目 |
| [NewXTimelineSuccessFingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/NewXTimelineSuccessFingerprint.kt) | 定位时间线成功响应的模型与方法 |
| [PostPoliciesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/PostPoliciesPatch.kt) | 接入帖子、社区附注及相关交互策略 |
| [RestorePinnedHomeTabPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/RestorePinnedHomeTabPatch.kt) | 恢复固定的首页时间线标签选择 |
| [RestoreTimelinePositionPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/RestoreTimelinePositionPatch.kt) | 接入时间线滚动位置的保存与恢复 |
| [ShowPollResultsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/ShowPollResultsPatch.kt) | 在投票相关界面接入结果显示 |
| [ShowSensitiveMediaPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/ShowSensitiveMediaPatch.kt) | 接入敏感媒体显示策略 |
| [TimelineFilterPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/TimelineFilterPatch.kt) | 汇总时间线条目过滤规则 |

## `patches/src/main/kotlin/app/crimera/patches/newx/timeline/postfilter`

| 文件名 | 作用 |
| --- | --- |
| [HideVerifiedPostsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/postfilter/HideVerifiedPostsPatch.kt) | 接入认证账号帖子过滤 |
| [PostFilterPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/postfilter/PostFilterPatch.kt) | 接入帖子文本与作者规则过滤 |
| [TimelineTextModelAdapterPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/timeline/postfilter/TimelineTextModelAdapterPatch.kt) | 把原生帖子文本模型接入规则过滤器 |

## `patches/src/main/kotlin/app/crimera/patches/newx/utils`

| 文件名 | 作用 |
| --- | --- |
| [Constants.kt](../../patches/src/main/kotlin/app/crimera/patches/newx/utils/Constants.kt) | 保存 NewX 补丁和扩展共用的描述符常量 |
