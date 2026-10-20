# 新版 X 运行时代码

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 109 个文件。

注入安装包后随 X 运行的设置、下载、通知、过滤及界面实现。

## `extensions/newx`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../extensions/newx/build.gradle.kts) | 配置 extensions/newx 模块的依赖、编译及打包 |

## `extensions/newx/src/main`

| 文件名 | 作用 |
| --- | --- |
| [AndroidManifest.xml](../../extensions/newx/src/main/AndroidManifest.xml) | 声明本扩展或占位模块的 Android 组件、权限与应用信息 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/featureswitches`

| 文件名 | 作用 |
| --- | --- |
| [FeatureSwitchAdapter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/featureswitches/FeatureSwitchAdapter.java) | 绘制实验功能开关列表 |
| [FeatureSwitchFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/featureswitches/FeatureSwitchFragment.java) | 提供实验功能开关的设置页面 |
| [FeatureSwitchImportExport.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/featureswitches/FeatureSwitchImportExport.java) | 导入和导出实验功能开关配置 |
| [FeatureSwitchStore.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/featureswitches/FeatureSwitchStore.java) | 保存实验功能开关的覆盖值 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/filteredreplies`

| 文件名 | 作用 |
| --- | --- |
| [FilteredRepliesDialog.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/filteredreplies/FilteredRepliesDialog.java) | 展示被过滤的回复 |
| [FilteredRepliesStore.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/filteredreplies/FilteredRepliesStore.java) | 保存被过滤回复的相关数据 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/misc`

| 文件名 | 作用 |
| --- | --- |
| [BottomBarVisibility.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/BottomBarVisibility.java) | 按设置保持底栏可见，保留其他原生隐藏条件 |
| [CanonicalUrlResolver.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/CanonicalUrlResolver.java) | 解析规范原始链接 |
| [CustomLinkActivity.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/CustomLinkActivity.java) | 接收自定义域名链接的 Android 页面入口 |
| [CustomLinkRouter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/CustomLinkRouter.java) | 把自定义链接转换并交给正确的原生页面 |
| [CustomSharingDomainValidator.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/CustomSharingDomainValidator.java) | 检查自定义分享域名的格式 |
| [DownloadBannerController.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadBannerController.java) | 驱动顶部下载通知及文字、进度条的原生状态更新 |
| [DownloadBannerPolicy.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadBannerPolicy.java) | 管理独立任务、通知去重、结果排队与取消归属 |
| [DownloadFileName.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadFileName.java) | 解析媒体文件名模板并生成保存名称 |
| [DownloadOptionsFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadOptionsFragment.java) | 提供下载方式、命名、画质等选项页面 |
| [DownloadProgressDisplay.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadProgressDisplay.java) | 把真实下载字节与速度更新为顶部进度；保留旧目标的进度视图 |
| [DownloadProgressState.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadProgressState.java) | 累计每个下载任务的大小、百分比、等待状态和速度 |
| [DownloadSettings.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DownloadSettings.java) | 读取并管理下载相关设置 |
| [DrawerCatalog.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DrawerCatalog.java) | 提供侧边抽屉可选条目的目录 |
| [DrawerEditorFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DrawerEditorFragment.java) | 提供侧边抽屉条目的编辑页面 |
| [DrawerItemFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DrawerItemFilter.java) | 按配置过滤侧边抽屉条目 |
| [DrawerTabOpener.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DrawerTabOpener.java) | 打开抽屉条目对应的原生标签页 |
| [DropIndicatorLayout.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/DropIndicatorLayout.java) | 在拖动排序时绘制插入位置提示 |
| [FilteredRepliesPostOptionHandler.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/FilteredRepliesPostOptionHandler.java) | 处理帖子菜单中的已过滤回复入口 |
| [FloatingGlassNavBar.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/FloatingGlassNavBar.java) | 读取玻璃底栏设置并调用原生视觉效果 |
| [GalleryCacheStatsFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/GalleryCacheStatsFragment.java) | 显示画廊缩略图缓存统计和管理入口 |
| [InlineActionBarSpacing.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/InlineActionBarSpacing.java) | 调整帖子动作栏的间距 |
| [InlineActionFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/InlineActionFilter.java) | 按设置过滤帖子动作按钮 |
| [InlineDownloadButton.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/InlineDownloadButton.java) | 处理帖子下载按钮的点击和媒体收集 |
| [MediaDiskCache.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaDiskCache.java) | 管理媒体缩略图的磁盘缓存 |
| [MediaLabels.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaLabels.java) | 生成媒体类型与项目的显示名称 |
| [MediaMerger.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaMerger.java) | 下载并合成长图等需要合并的媒体 |
| [MediaPickerDialog.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaPickerDialog.java) | 显示媒体选择、清晰度与操作对话框 |
| [MediaSheetFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaSheetFilter.java) | 按配置过滤媒体操作面板选项 |
| [MediaTabResolver.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaTabResolver.java) | 解析主页媒体标签页的选择 |
| [MediaThumbnailLoader.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/MediaThumbnailLoader.java) | 读取并显示媒体缩略图 |
| [NativeDownloadRouter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NativeDownloadRouter.java) | 把原生下载请求转交 Piko 下载流程 |
| [NavBarCatalog.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NavBarCatalog.java) | 提供可选底部导航条目 |
| [NavBarConfig.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NavBarConfig.java) | 保存底部导航的顺序与配置 |
| [NavBarEditorFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NavBarEditorFragment.java) | 提供底部导航编辑页面 |
| [NavBarFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NavBarFilter.java) | 按设置过滤底部导航项 |
| [NavBarReplacement.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NavBarReplacement.java) | 处理底部导航项目的替换 |
| [NewXCrashCopyReceiver.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXCrashCopyReceiver.java) | 处理崩溃日志的复制操作 |
| [NewXCrashHandler.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXCrashHandler.java) | 收集和保存 NewX 扩展崩溃日志 |
| [NewXCrashPostOptionHandler.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXCrashPostOptionHandler.java) | 处理帖子菜单中的崩溃日志操作 |
| [NewXDownloadFolders.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXDownloadFolders.java) | 选择保存目录并生成按作者区分的子目录 |
| [NewXDownloadTexts.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXDownloadTexts.java) | 把下载状态转换为本地化文字 |
| [NewXDownloader.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXDownloader.java) | 组织媒体下载请求、事件监听和完成处理 |
| [NewXInAppNotification.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXInAppNotification.java) | 连接原生顶部通知、任务队列和进度状态的桥接入口 |
| [NewXNotificationTexts.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXNotificationTexts.java) | 整理原生通知需要显示的文字 |
| [NewXObjectBrowserHandler.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXObjectBrowserHandler.java) | 处理原生对象浏览和调试操作 |
| [NewXPostOptionActions.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXPostOptionActions.java) | 分发扩展添加的帖子菜单操作 |
| [NewXPostOptions.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXPostOptions.java) | 提供帖子菜单的扩展选项 |
| [NewXShareImageHandler.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/NewXShareImageHandler.java) | 处理帖子分享为图片的操作 |
| [PollResultsFormatter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/PollResultsFormatter.java) | 格式化投票结果和百分比 |
| [PostOptionsFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/PostOptionsFilter.java) | 按设置过滤帖子菜单选项 |
| [ProfileBulkDownload.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileBulkDownload.java) | 执行主页全部媒体下载，管理会话、取消和结果汇总 |
| [ProfileBulkPager.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileBulkPager.java) | 控制主页分页、停滞判断、空页与失败重试 |
| [ProfilePhotosGallery.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfilePhotosGallery.java) | 构建主页照片画廊及媒体下载入口 |
| [ProfilePostSortingResolver.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfilePostSortingResolver.java) | 解析主页帖子的默认排序设置 |
| [ProfileTabFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileTabFilter.java) | 按配置过滤主页标签页 |
| [ProfileTabsCatalog.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileTabsCatalog.java) | 提供主页标签页的条目目录 |
| [ProfileTabsConfig.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileTabsConfig.java) | 保存主页标签页的顺序和选择配置 |
| [ProfileTabsEditorFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ProfileTabsEditorFragment.java) | 提供主页标签页编辑页面 |
| [ReplyBarInsets.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ReplyBarInsets.java) | 处理隐藏回复栏后的底部留白 |
| [ReplySortingResolver.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ReplySortingResolver.java) | 解析默认回复排序设置 |
| [ResolutionChooserDialog.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ResolutionChooserDialog.java) | 显示视频分辨率选择对话框 |
| [RowDragShadow.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/RowDragShadow.java) | 为列表条目拖动排序绘制拖动影像 |
| [ShareUrlResolver.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/ShareUrlResolver.java) | 整理分享链接及自定义分享域名 |
| [UpdateFont.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/misc/UpdateFont.java) | 载入并应用所选自定义字体 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/postfilter`

| 文件名 | 作用 |
| --- | --- |
| [AhoMatcher.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/AhoMatcher.java) | 使用多模式匹配查找帖子中的过滤词 |
| [PhraseMatcher.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/PhraseMatcher.java) | 执行短语匹配 |
| [PostFilterFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/PostFilterFragment.java) | 提供帖子过滤规则编辑页面 |
| [PostFilterMatcher.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/PostFilterMatcher.java) | 判断帖子是否命中过滤规则 |
| [PostFilterRule.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/PostFilterRule.java) | 表示一条帖子过滤规则 |
| [PostFilterRuleAdapter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/PostFilterRuleAdapter.java) | 绘制帖子过滤规则列表 |
| [PostFilterRuleStore.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/PostFilterRuleStore.java) | 保存和读取帖子过滤规则 |
| [VerifiedAccountWhitelistFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/VerifiedAccountWhitelistFragment.java) | 提供认证账号过滤白名单编辑页面 |
| [VerifiedAccountWhitelistStore.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/postfilter/VerifiedAccountWhitelistStore.java) | 保存认证账号过滤白名单 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/settings`

| 文件名 | 作用 |
| --- | --- |
| [BuiltInSettings.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/BuiltInSettings.java) | 注册内置设置及默认值 |
| [ChangelogAction.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/ChangelogAction.java) | 打开版本更新说明 |
| [ComposeSettingsHook.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/ComposeSettingsHook.java) | 把原生 Compose 设置操作连接到扩展页面 |
| [NewXCrashSettingsAction.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/NewXCrashSettingsAction.java) | 处理设置页中的崩溃日志入口 |
| [NewXLogger.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/NewXLogger.java) | 记录 NewX 扩展日志 |
| [NewXSettingsActivity.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/NewXSettingsActivity.java) | 承载 Piko 设置界面的 Android 页面 |
| [NewXSettingsHost.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/NewXSettingsHost.java) | 组织设置页面、入口和页面切换 |
| [PikoSettingsIcon.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/PikoSettingsIcon.java) | 绘制设置入口的 Piko 图标 |
| [ServerLogExportAction.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/settings/ServerLogExportAction.java) | 导出服务端日志 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/theme`

| 文件名 | 作用 |
| --- | --- |
| [DynamicColorPalette.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/theme/DynamicColorPalette.java) | 生成和查询动态颜色调色板 |
| [TwitterTheme.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/theme/TwitterTheme.java) | 定义 Twitter 风格主题配色 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/timeline`

| 文件名 | 作用 |
| --- | --- |
| [ForYouTopicFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/ForYouTopicFilter.java) | 保存并应用“为你推荐”主题筛选 |
| [ForYouTopicFilterFragment.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/ForYouTopicFilterFragment.java) | 提供主题筛选的完整设置页面 |
| [ForYouTopicFilterSheet.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/ForYouTopicFilterSheet.java) | 提供主题筛选的底部选择面板 |
| [NewXTimelineFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/NewXTimelineFilter.java) | 在运行时过滤时间线条目 |
| [PinnedHomeTabRestore.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/PinnedHomeTabRestore.java) | 保存并恢复固定的首页标签页 |
| [TimelineModelAccess.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/TimelineModelAccess.java) | 通过已注入的接口读取时间线模型 |
| [TimelineRefreshGate.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/TimelineRefreshGate.java) | 判断自动刷新是否应被阻止，放行手动刷新和分页 |
| [TimelineScrollPositionStore.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/TimelineScrollPositionStore.java) | 保存并恢复时间线滚动位置 |
| [TimelineTabFilter.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/timeline/TimelineTabFilter.java) | 按配置过滤时间线标签页 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/ui`

| 文件名 | 作用 |
| --- | --- |
| [BottomSheetView.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/ui/BottomSheetView.java) | 提供底部弹出面板视图 |
| [IconView.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/ui/IconView.java) | 提供图标显示控件 |
| [ListItem.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/ui/ListItem.java) | 表示设置或列表中的一条项目 |
| [LoadingIndicatorView.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/ui/LoadingIndicatorView.java) | 提供加载状态指示视图 |
| [NewXSettingsTheme.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/ui/NewXSettingsTheme.java) | 为 Piko 设置页面应用主题 |
| [Theme.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/ui/Theme.java) | 查询界面主题、文字色和强调色 |

## `extensions/newx/src/main/java/app/morphe/extension/newx/utils`

| 文件名 | 作用 |
| --- | --- |
| [NewXUtils.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/utils/NewXUtils.java) | 提供 NewX 上下文、页面及常用操作辅助 |
| [ToStringParser.java](../../extensions/newx/src/main/java/app/morphe/extension/newx/utils/ToStringParser.java) | 从原生模型文字表示中解析语义字段 |
