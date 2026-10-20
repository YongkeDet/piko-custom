# Instagram 代码

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 309 个文件。

Instagram 补丁和扩展，包含主页媒体下载、原生分页接入、串行下载与顶部进度；combined 构建与 NewX 一同打包。分表文件名为历史沿用。

## `extensions/instagram`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../extensions/instagram/build.gradle.kts) | 配置 extensions/instagram 模块的依赖、编译及打包 |

## `extensions/instagram/src/main`

| 文件名 | 作用 |
| --- | --- |
| [AndroidManifest.xml](../../extensions/instagram/src/main/AndroidManifest.xml) | 声明本扩展或占位模块的 Android 组件、权限与应用信息 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/constants`

| 文件名 | 作用 |
| --- | --- |
| [Constants.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/constants/Constants.java) | Instagram：定义扩展常量及宿主类型描述符 |
| [PostType.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/constants/PostType.java) | Instagram：定义帖子与媒体类型 |
| [UI.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/constants/UI.java) | Instagram：提供图标常量、按钮与对话框等界面辅助 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/db`

| 文件名 | 作用 |
| --- | --- |
| [PikoInstantsDb.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/db/PikoInstantsDb.java) | Instagram：“本地消息与媒体数据库”的运行时处理 |
| [PikoMessageDb.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/db/PikoMessageDb.java) | Instagram：“本地消息与媒体数据库”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/entity`

| 文件名 | 作用 |
| --- | --- |
| [AudioMediaInterface.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/AudioMediaInterface.java) | Instagram：音频媒体数据的访问契约或注入适配 |
| [CommentData.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/CommentData.java) | Instagram：评论数据的访问契约或注入适配 |
| [DeveloperOptions.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/DeveloperOptions.java) | Instagram：开发者选项数据的访问契约或注入适配 |
| [DeveloperOptionsItem.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/DeveloperOptionsItem.java) | Instagram：开发者选项条目数据的访问契约或注入适配 |
| [DirectItem.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/DirectItem.java) | Instagram：私信条目数据的访问契约或注入适配 |
| [Entity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/Entity.java) | Instagram：通用原生实体数据的访问契约或注入适配 |
| [GifMediaInterface.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/GifMediaInterface.java) | Instagram：GIF 媒体数据的访问契约或注入适配 |
| [ImageData.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/ImageData.java) | Instagram：图片数据的访问契约或注入适配 |
| [InstagramButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/InstagramButton.java) | Instagram：原生按钮数据的访问契约或注入适配 |
| [InstagramButtonStyleEnum.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/InstagramButtonStyleEnum.java) | Instagram：原生按钮样式数据的访问契约或注入适配 |
| [InstagramDialogBox.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/InstagramDialogBox.java) | Instagram：原生对话框数据的访问契约或注入适配 |
| [MediaData.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/MediaData.java) | Instagram：媒体数据的访问契约或注入适配 |
| [MediaInterface.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/MediaInterface.java) | Instagram：媒体访问接口数据的访问契约或注入适配 |
| [MessageInfo.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/MessageInfo.java) | Instagram：消息信息数据的访问契约或注入适配 |
| [OriginalSoundDataIntf.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/OriginalSoundDataIntf.java) | Instagram：原始音频数据的访问契约或注入适配 |
| [ProfileInfo.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/ProfileInfo.java) | Instagram：用户资料数据的访问契约或注入适配 |
| [ReelResponseItem.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/ReelResponseItem.java) | Instagram：Reels 响应条目数据的访问契约或注入适配 |
| [TrackDataIntf.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/TrackDataIntf.java) | Instagram：音轨数据的访问契约或注入适配 |
| [UserData.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/UserData.java) | Instagram：用户数据的访问契约或注入适配 |
| [UserFriendshipStatus.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/UserFriendshipStatus.java) | Instagram：关注关系数据的访问契约或注入适配 |
| [VideoData.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/entity/VideoData.java) | Instagram：视频数据的访问契约或注入适配 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches`

| 文件名 | 作用 |
| --- | --- |
| [Block.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/Block.java) | Instagram：过滤推荐内容的解析字段并清理分析缓存 |
| [Links.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/Links.java) | Instagram：处理分享和打开链接 |
| [SuggestedProfiles.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/SuggestedProfiles.java) | Instagram：处理推荐用户条目的显示 |
| [WelcomeMessage.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/WelcomeMessage.java) | Instagram：在首次使用 Piko 时显示欢迎说明 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/actionbar`

| 文件名 | 作用 |
| --- | --- |
| [ActionBarPatch.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/actionbar/ActionBarPatch.java) | Instagram：“工具栏按钮”的运行时处理 |
| [HomeActionBarLayout.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/actionbar/HomeActionBarLayout.java) | Instagram：“工具栏按钮”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment`

| 文件名 | 作用 |
| --- | --- |
| [HandleCommentButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/HandleCommentButton.java) | Instagram：“评论操作”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/copyTextButton`

| 文件名 | 作用 |
| --- | --- |
| [CopyTextButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/copyTextButton/CopyTextButton.java) | Instagram：“评论文字复制按钮”的界面控件 |
| [InitCopyTextButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/copyTextButton/InitCopyTextButton.java) | Instagram：“评论文字复制按钮”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/debugButton`

| 文件名 | 作用 |
| --- | --- |
| [DebugButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/debugButton/DebugButton.java) | Instagram：“调试按钮”的界面控件 |
| [InitDebugButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/debugButton/InitDebugButton.java) | Instagram：“调试按钮”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/saveMediaButton`

| 文件名 | 作用 |
| --- | --- |
| [InitSaveMediaButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/saveMediaButton/InitSaveMediaButton.java) | Instagram：“评论媒体保存按钮”的界面控件 |
| [SaveMediaButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/comment/saveMediaButton/SaveMediaButton.java) | Instagram：“评论媒体保存按钮”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font`

| 文件名 | 作用 |
| --- | --- |
| [CustomFont.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFont.java) | Instagram：接入自定义文字字体 |
| [CustomFontButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontButton.java) | Instagram：“自定义字体”的界面控件 |
| [CustomFontCheckBox.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontCheckBox.java) | Instagram：“自定义字体”的界面控件 |
| [CustomFontContext.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontContext.java) | Instagram：“自定义字体”的运行时处理 |
| [CustomFontEditText.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontEditText.java) | Instagram：“自定义字体”的运行时处理 |
| [CustomFontTextAppearanceSpan.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontTextAppearanceSpan.java) | Instagram：“自定义字体”的运行时处理 |
| [CustomFontTextView.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontTextView.java) | Instagram：“自定义字体”的界面控件 |
| [CustomFontTypefaceSpan.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/CustomFontTypefaceSpan.java) | Instagram：“自定义字体”的运行时处理 |
| [FontStorage.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/customise/font/FontStorage.java) | Instagram：“自定义字体”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags`

| 文件名 | 作用 |
| --- | --- |
| [Flag.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags/Flag.java) | Instagram：“实验开关状态”的运行时处理 |
| [FlagState.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags/FlagState.java) | Instagram：“实验开关状态”的配置或状态存储 |
| [FlagType.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags/FlagType.java) | Instagram：“实验开关状态”的运行时处理 |
| [FlagsSharedPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags/FlagsSharedPref.java) | Instagram：“实验开关状态”的配置或状态存储 |
| [HookFlags.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags/HookFlags.java) | Instagram：“实验开关状态”的运行时处理 |
| [RecommendedFlags.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/devFlags/RecommendedFlags.java) | Instagram：“实验开关状态”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/dm`

| 文件名 | 作用 |
| --- | --- |
| [DeletedMessagesActivity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/dm/DeletedMessagesActivity.java) | Instagram：“私信及消息保留”的设置页面 |
| [EphemeralMediaPatch.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/dm/EphemeralMediaPatch.java) | Instagram：“私信及消息保留”的运行时处理 |
| [MarkChatAsRead.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/dm/MarkChatAsRead.java) | Instagram：“私信及消息保留”的运行时处理 |
| [SavedMessagesHook.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/dm/SavedMessagesHook.java) | Instagram：“私信及消息保留”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download`

| 文件名 | 作用 |
| --- | --- |
| [DownloadFileNameFormatter.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadFileNameFormatter.java) | Instagram：“媒体下载”的运行时处理 |
| [DownloadMapping.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadMapping.java) | Instagram：“媒体下载”的运行时处理 |
| [DownloadRatePolicy.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadRatePolicy.java) | Instagram：计算服务端拒绝后的至少 15 分钟冷却，并解析媒体文件请求的更长 Retry-After |
| [DownloadUtils.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadUtils.java) | Instagram：把单帖、头像、语音及私信媒体下载交给串行队列，并为主页媒体生成下载请求 |
| [FeedDownloadButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/FeedDownloadButton.java) | Instagram：在收藏左侧添加原生下载组件，按当前行的媒体、轮播位置及页面环境执行下载 |
| [InstagramDownloads.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/InstagramDownloads.java) | Instagram：串行执行媒体文件下载，管理独立任务、暂停、传输进度与跨任务冷却 |
| [MessageUtils.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/MessageUtils.java) | Instagram：“媒体下载”的运行时处理 |
| [NativeProfileBridge.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/NativeProfileBridge.java) | Instagram：提供修补时替换为原生直接调用的主页、账号、分页响应及下一页访问桥接 |
| [ProfileDownloadStore.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloadStore.java) | Instagram：按登录账号、目标主页和媒体编号保存已完成文件记录，继续前检查 SAF 文档是否仍存在 |
| [ProfileDownloader.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloader.java) | Instagram：控制前台主页批量下载，收集帖子网格媒体，串行保存后等待 30 秒再请求原生下一页 |
| [ProfilePageState.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfilePageState.java) | Instagram：管理原生主页分页状态，区分缓存与网络响应，检测重复游标、停滞及可靠的结束标记 |
| [TopDownloadNotice.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/TopDownloadNotice.java) | Instagram：绘制可换行的顶部下载卡片、百分比及进度条，管理暂停入口与各任务结果队列 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/feed`

| 文件名 | 作用 |
| --- | --- |
| [ChangeLikeAnimationPatch.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/feed/ChangeLikeAnimationPatch.java) | Instagram：替换点赞动画 |
| [LimitFeedToFollowingProfiles.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/feed/LimitFeedToFollowingProfiles.java) | Instagram：把首页内容限制为已关注用户 |
| [MoreOptionsOnPostPatch.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/feed/MoreOptionsOnPostPatch.java) | Instagram：向帖子加入更多操作 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/filter/story`

| 文件名 | 作用 |
| --- | --- |
| [FilterStory.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/filter/story/FilterStory.java) | Instagram：“快拍及已读状态”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/focusLock`

| 文件名 | 作用 |
| --- | --- |
| [FocusLock.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/focusLock/FocusLock.java) | Instagram：“专注锁定”的运行时处理 |
| [FocusLockDialogs.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/focusLock/FocusLockDialogs.java) | Instagram：“专注锁定”的运行时处理 |
| [FocusLockDuration.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/focusLock/FocusLockDuration.java) | Instagram：“专注锁定”的运行时处理 |
| [FocusLockTargets.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/focusLock/FocusLockTargets.java) | Instagram：“专注锁定”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/followList`

| 文件名 | 作用 |
| --- | --- |
| [FollowListHook.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/followList/FollowListHook.java) | Instagram：“关注列表”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/instants`

| 文件名 | 作用 |
| --- | --- |
| [InstantsDownloadHook.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/instants/InstantsDownloadHook.java) | Instagram：“Instants 媒体保存”的运行时处理 |
| [InstantsVaultActivity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/instants/InstantsVaultActivity.java) | Instagram：“Instants 媒体保存”的设置页面 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/navigation`

| 文件名 | 作用 |
| --- | --- |
| [NavigationBarPatch.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/navigation/NavigationBarPatch.java) | Instagram：接入导航栏自定义 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton`

| 文件名 | 作用 |
| --- | --- |
| [FeedButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/FeedButton.java) | Instagram：“更多菜单按钮”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels`

| 文件名 | 作用 |
| --- | --- |
| [AddReelButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/AddReelButton.java) | Instagram：“Reels 短视频”的界面控件 |
| [ReelOverflowButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/ReelOverflowButton.java) | Instagram：“Reels 短视频”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/buttons`

| 文件名 | 作用 |
| --- | --- |
| [DebugButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/buttons/DebugButton.java) | Instagram：“媒体菜单按钮”的界面控件 |
| [DownloadButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/buttons/DownloadButton.java) | Instagram：“媒体菜单按钮”的界面控件 |
| [ExternalDownloadButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/buttons/ExternalDownloadButton.java) | Instagram：“媒体菜单按钮”的界面控件 |
| [InfoButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/buttons/InfoButton.java) | Instagram：“媒体菜单按钮”的界面控件 |
| [ReelButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/overflowMenuButton/reels/buttons/ReelButton.java) | Instagram：“媒体菜单按钮”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story`

| 文件名 | 作用 |
| --- | --- |
| [PeopleCellDialogBox.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/PeopleCellDialogBox.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [StoryButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StoryButton.java) | Instagram：“快拍及已读状态”的界面控件 |
| [StorySeenBindingRetry.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenBindingRetry.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [StorySeenBindingUpdates.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenBindingUpdates.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [StorySeenBridge.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenBridge.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [StorySeenButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenButton.java) | Instagram：“快拍及已读状态”的界面控件 |
| [StorySeenButtonView.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenButtonView.java) | Instagram：“快拍及已读状态”的界面控件 |
| [StorySeenKey.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenKey.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [StorySeenRequestScope.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenRequestScope.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [StorySeenState.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StorySeenState.java) | Instagram：“快拍及已读状态”的配置或状态存储 |
| [StoryTimestamp.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/StoryTimestamp.java) | Instagram：“快拍及已读状态”的运行时处理 |
| [ViewStoryMentionsPatch.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/story/ViewStoryMentionsPatch.java) | Instagram：接入快拍提及信息查看 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/userprofile`

| 文件名 | 作用 |
| --- | --- |
| [FriendshipStatusIndicator.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/userprofile/FriendshipStatusIndicator.java) | Instagram：“用户主页操作”的运行时处理 |
| [ProfileMoreOption.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/userprofile/ProfileMoreOption.java) | Instagram：处理主页更多资料选项，包括下载此主页全部图片和视频的入口 |
| [ProfilePictureViewer.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/userprofile/ProfilePictureViewer.java) | Instagram：“用户主页操作”的运行时处理 |
| [UserProfileButton.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/userprofile/UserProfileButton.java) | Instagram：“用户主页操作”的界面控件 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings`

| 文件名 | 作用 |
| --- | --- |
| [ActivityHook.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/ActivityHook.java) | Instagram：“设置页面和配置”的运行时处理 |
| [Settings.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/Settings.java) | Instagram：“设置页面和配置”的运行时处理 |
| [SettingsActivity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsActivity.java) | Instagram：“设置页面和配置”的设置页面 |
| [SettingsRestart.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsRestart.java) | Instagram：“设置页面和配置”的运行时处理 |
| [SettingsShortcutActivity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsShortcutActivity.java) | Instagram：“设置页面和配置”的设置页面 |
| [SettingsStatus.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsStatus.java) | Instagram：“设置页面和配置”的运行时处理 |
| [SettingsTaskService.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/SettingsTaskService.java) | Instagram：“设置页面和配置”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference`

| 文件名 | 作用 |
| --- | --- |
| [Helper.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/Helper.java) | Instagram：“偏好设置界面”的运行时处理 |
| [ScreenBuilder.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/ScreenBuilder.java) | Instagram：“偏好设置界面”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/fragments`

| 文件名 | 作用 |
| --- | --- |
| [BackupPrefActivity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/fragments/BackupPrefActivity.java) | Instagram：“设置子页面”的设置页面 |
| [FragmentHook.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/fragments/FragmentHook.java) | Instagram：“设置子页面”的运行时处理 |
| [RestorePrefActivity.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/fragments/RestorePrefActivity.java) | Instagram：“设置子页面”的设置页面 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets`

| 文件名 | 作用 |
| --- | --- |
| [ButtonPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/ButtonPref.java) | Instagram：“设置控件”的配置或状态存储 |
| [CategoryPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/CategoryPref.java) | Instagram：“设置控件”的配置或状态存储 |
| [DownloadFileNameTemplatePref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/DownloadFileNameTemplatePref.java) | Instagram：“设置控件”的配置或状态存储 |
| [EditTextPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/EditTextPref.java) | Instagram：“设置控件”的配置或状态存储 |
| [FocusLockDurationPreference.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/FocusLockDurationPreference.java) | Instagram：“设置控件”的界面控件 |
| [InstagramPreferenceStyle.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/InstagramPreferenceStyle.java) | Instagram：“设置控件”的运行时处理 |
| [LikeAnimationPreference.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/LikeAnimationPreference.java) | Instagram：“设置控件”的界面控件 |
| [ListPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/ListPref.java) | Instagram：“设置控件”的配置或状态存储 |
| [MultiSelectListPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/MultiSelectListPref.java) | Instagram：“设置控件”的配置或状态存储 |
| [NavigationBarAdapter.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/NavigationBarAdapter.java) | Instagram：“设置控件”的列表适配器 |
| [NavigationBarEditorState.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/NavigationBarEditorState.java) | Instagram：“设置控件”的配置或状态存储 |
| [NavigationBarPreference.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/NavigationBarPreference.java) | Instagram：“设置控件”的界面控件 |
| [NavigationSettingsAccessPolicy.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/NavigationSettingsAccessPolicy.java) | Instagram：“设置控件”的运行时处理 |
| [NavigationSettingsAccessWarning.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/NavigationSettingsAccessWarning.java) | Instagram：“设置控件”的运行时处理 |
| [NavigationStartupPreference.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/NavigationStartupPreference.java) | Instagram：“设置控件”的界面控件 |
| [SwitchPref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/settings/preference/widgets/SwitchPref.java) | Instagram：“设置控件”的配置或状态存储 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/theme`

| 文件名 | 作用 |
| --- | --- |
| [MaterialYouState.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/theme/MaterialYouState.java) | Instagram：“主题配色”的配置或状态存储 |
| [MaterialYouTheme.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/theme/MaterialYouTheme.java) | Instagram：“主题配色”的运行时处理 |
| [MaterialYouThemeAPI31.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/theme/MaterialYouThemeAPI31.java) | Instagram：“主题配色”的运行时处理 |
| [ThemeMode.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/theme/ThemeMode.java) | Instagram：“主题配色”的运行时处理 |

## `extensions/instagram/src/main/java/app/morphe/extension/instagram/utils`

| 文件名 | 作用 |
| --- | --- |
| [IgStr.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/IgStr.java) | Instagram：“通用辅助逻辑”的运行时处理 |
| [InstaUtils.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/InstaUtils.java) | Instagram：“通用辅助逻辑”的运行时处理 |
| [InstagramCrashHandler.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/InstagramCrashHandler.java) | Instagram：通过系统Downloads保存崩溃报告，保留私有待发布副本并在下次启动重试 |
| [MediaUrls.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/MediaUrls.java) | Instagram：“通用辅助逻辑”的运行时处理 |
| [Pref.java](../../extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/Pref.java) | Instagram：读取和保存应用功能设置 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/ads`

| 文件名 | 作用 |
| --- | --- |
| [DisableAdsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/ads/DisableAdsPatch.kt) | Instagram：过滤广告内容 |
| [HideSuggestedContentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/ads/HideSuggestedContentPatch.kt) | Instagram：隐藏建议或推荐内容 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/commentDataEntity`

| 文件名 | 作用 |
| --- | --- |
| [CommentDataEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/commentDataEntity/CommentDataEntity.kt) | Instagram：“评论数据模型”的模型接口或接口注入适配 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/commentDataEntity/Fingerprint.kt) | Instagram：定位“评论数据模型”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/decoder`

| 文件名 | 作用 |
| --- | --- |
| [Decoder.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/decoder/Decoder.kt) | Instagram：“原生数据解码”的模型接口或接口注入适配 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/decoder/Fingerprint.kt) | Instagram：定位“原生数据解码”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/developerOptions`

| 文件名 | 作用 |
| --- | --- |
| [DeveloperOptionEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/developerOptions/DeveloperOptionEntity.kt) | Instagram：“开发者选项”的模型接口或接口注入适配 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/developerOptions/Fingerprint.kt) | Instagram：定位“开发者选项”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/dialogbox`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/dialogbox/Fingerprint.kt) | Instagram：定位“原生对话框接口”对应的原生方法或字段 |
| [InstagramDialogBoxEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/dialogbox/InstagramDialogBoxEntity.kt) | Instagram：“原生对话框接口”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/directItem`

| 文件名 | 作用 |
| --- | --- |
| [DirectItemEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/directItem/DirectItemEntity.kt) | Instagram：“私信条目模型”的模型接口或接口注入适配 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/directItem/Fingerprint.kt) | Instagram：定位“私信条目模型”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/instagramButton`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/instagramButton/Fingerprint.kt) | Instagram：定位“原生按钮接口”对应的原生方法或字段 |
| [InstagramButtonEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/instagramButton/InstagramButtonEntity.kt) | Instagram：“原生按钮接口”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/mediadata`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/mediadata/Fingerprint.kt) | Instagram：定位“媒体数据模型”对应的原生方法或字段 |
| [MediaDataEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/mediadata/MediaDataEntity.kt) | Instagram：“媒体数据模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/messageInfoEntity`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/messageInfoEntity/Fingerprint.kt) | Instagram：定位“消息信息模型”对应的原生方法或字段 |
| [MessageInfoEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/messageInfoEntity/MessageInfoEntity.kt) | Instagram：“消息信息模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/originalSoundDataIntf`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/originalSoundDataIntf/Fingerprint.kt) | Instagram：定位“原始音频数据接口”对应的原生方法或字段 |
| [OriginalSoundDataIntf.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/originalSoundDataIntf/OriginalSoundDataIntf.kt) | Instagram：原始音频数据的访问契约或注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/profileinfo`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/profileinfo/Fingerprint.kt) | Instagram：定位“用户资料模型”对应的原生方法或字段 |
| [ProfileInfoEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/profileinfo/ProfileInfoEntity.kt) | Instagram：“用户资料模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/reelResponseItem`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/reelResponseItem/Fingerprint.kt) | Instagram：定位“Reels 响应模型”对应的原生方法或字段 |
| [ReelResponseItemEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/reelResponseItem/ReelResponseItemEntity.kt) | Instagram：“Reels 响应模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/trackDataIntf`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/trackDataIntf/Fingerprint.kt) | Instagram：定位“音轨数据接口”对应的原生方法或字段 |
| [TrackDataIntf.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/trackDataIntf/TrackDataIntf.kt) | Instagram：音轨数据的访问契约或注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/userdata`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/userdata/Fingerprint.kt) | Instagram：定位“用户数据模型”对应的原生方法或字段 |
| [UserDataEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/userdata/UserDataEntity.kt) | Instagram：“用户数据模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/userfriendshipstatus`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/userfriendshipstatus/Fingerprint.kt) | Instagram：定位“关注关系模型”对应的原生方法或字段 |
| [UserFriendshipStatusEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/userfriendshipstatus/UserFriendshipStatusEntity.kt) | Instagram：“关注关系模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/entity/videoData`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/videoData/Fingerprint.kt) | Instagram：定位“视频数据模型”对应的原生方法或字段 |
| [VideoDataEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/entity/videoData/VideoDataEntity.kt) | Instagram：“视频数据模型”的模型接口或接口注入适配 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/filter/story`

| 文件名 | 作用 |
| --- | --- |
| [FilterStoriesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/filter/story/FilterStoriesPatch.kt) | Instagram：按规则过滤快拍 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/filter/story/Fingerprint.kt) | Instagram：定位“快拍及已读状态”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links`

| 文件名 | 作用 |
| --- | --- |
| [CustomSharingDomainPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/CustomSharingDomainPatch.kt) | Instagram：替换分享链接使用的域名 |
| [InterceptUriPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/InterceptUriPatch.kt) | Instagram：拦截并处理打开链接的请求 |
| [OpenLinksExternallyPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/OpenLinksExternallyPatch.kt) | Instagram：使用外部浏览器打开链接 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links/distractionFree`

| 文件名 | 作用 |
| --- | --- |
| [DisableCommentsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/distractionFree/DisableCommentsPatch.kt) | Instagram：关闭评论入口 |
| [DisableExplorePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/distractionFree/DisableExplorePatch.kt) | Instagram：隐藏探索内容 |
| [DisableHighlightsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/distractionFree/DisableHighlightsPatch.kt) | Instagram：隐藏精选快拍 |
| [DisableStoriesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/distractionFree/DisableStoriesPatch.kt) | Instagram：隐藏快拍内容 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links/misc`

| 文件名 | 作用 |
| --- | --- |
| [ActivityFeedSuggestedProfiles.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/ActivityFeedSuggestedProfiles.kt) | Instagram：隐藏动态页推荐用户 |
| [DisableAnalyticsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/DisableAnalyticsPatch.kt) | Instagram：关闭分析收集相关行为 |
| [DisableDiscoverPeoplePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/DisableDiscoverPeoplePatch.kt) | Instagram：隐藏发现用户入口 |
| [HideReshareButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/HideReshareButtonPatch.kt) | Instagram：隐藏转发分享按钮 |
| [ReelsSuggestedProfiles.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/ReelsSuggestedProfiles.kt) | Instagram：隐藏 Reels 推荐用户 |
| [SessionReelsClassifier.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/SessionReelsClassifier.kt) | Instagram：在修补时保留当前账号的原生推荐用户分类器和条件 |
| [SuggestedProfilesFingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/SuggestedProfilesFingerprints.kt) | Instagram：定位“链接处理”对应的原生方法或字段 |
| [SuggestionStringConstants.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/misc/SuggestionStringConstants.kt) | Instagram：解析原生常量表中提取的文字，保持语义定位 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy`

| 文件名 | 作用 |
| --- | --- |
| [DisableTypingStatusPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/DisableTypingStatusPatch.kt) | Instagram：阻止发送正在输入状态 |
| [MarkStorySeenFingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/MarkStorySeenFingerprints.kt) | Instagram：定位“隐私与已读控制”对应的原生方法或字段 |
| [MarkStorySeenHooks.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/MarkStorySeenHooks.kt) | Instagram：接通手动标记快拍已读的请求 |
| [StorySeenResultHooks.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/StorySeenResultHooks.kt) | Instagram：接通快拍已读请求的结果回传 |
| [ViewLiveAnonymouslyPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/ViewLiveAnonymouslyPatch.kt) | Instagram：接入匿名观看直播 |
| [ViewStoriesAnonymouslyPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/ViewStoriesAnonymouslyPatch.kt) | Instagram：接入匿名观看快拍 |
| [WrappedStoryAggregate.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/privacy/WrappedStoryAggregate.kt) | Instagram：把媒体模型接入原生故事已读请求的类型包装与聚合 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links/sanitizeShareLinks`

| 文件名 | 作用 |
| --- | --- |
| [SanitizeShareLinksPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/sanitizeShareLinks/SanitizeShareLinksPatch.kt) | Instagram：清理分享链接参数 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links/shareLinks`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/shareLinks/Fingerprint.kt) | Instagram：定位“分享链接生成”对应的原生方法或字段 |
| [ShareLinks.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/shareLinks/ShareLinks.kt) | Instagram：生成帖子和媒体的分享链接 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/links/validateLinks`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/validateLinks/Fingerprint.kt) | Instagram：定位“分享链接检查”对应的原生方法或字段 |
| [ValidateLinksPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/links/validateLinks/ValidateLinksPatch.kt) | Instagram：检查原生链接是否可处理 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/chatActionBarButton`

| 文件名 | 作用 |
| --- | --- |
| [ChatActionBarButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/chatActionBarButton/ChatActionBarButtonPatch.kt) | Instagram：向聊天工具栏加入扩展按钮 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/inboxActionBarButton`

| 文件名 | 作用 |
| --- | --- |
| [InboxActionBarButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/inboxActionBarButton/InboxActionBarButtonPatch.kt) | Instagram：向私信列表工具栏加入扩展按钮 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/mainFeedActionBarButton`

| 文件名 | 作用 |
| --- | --- |
| [MainFeedActionBarButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/mainFeedActionBarButton/MainFeedActionBarButtonPatch.kt) | Instagram：向首页工具栏加入扩展按钮 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/userProfileActionBarButton`

| 文件名 | 作用 |
| --- | --- |
| [UserProfileActionBarButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/actionBar/userProfileActionBarButton/UserProfileActionBarButtonPatch.kt) | Instagram：向用户主页工具栏加入扩展按钮 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/buildExpiredPopup`

| 文件名 | 作用 |
| --- | --- |
| [RemoveBuildExpiredPopupPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/buildExpiredPopup/RemoveBuildExpiredPopupPatch.kt) | Instagram：移除构建过期提示 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/changeLikeAnimation`

| 文件名 | 作用 |
| --- | --- |
| [ChangeLikeAnimationPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/changeLikeAnimation/ChangeLikeAnimationPatch.kt) | Instagram：替换点赞动画 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/clone`

| 文件名 | 作用 |
| --- | --- |
| [ClonePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/clone/ClonePatch.kt) | Instagram：修改应用标识以便与原版共存 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment`

| 文件名 | 作用 |
| --- | --- |
| [AddCommentButtonAttributesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/AddCommentButtonAttributesPatch.kt) | Instagram：提供评论扩展按钮的属性 |
| [AddCommentButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/AddCommentButtonPatch.kt) | Instagram：在评论界面加入扩展按钮 |
| [CommentButtonClickCheckPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/CommentButtonClickCheckPatch.kt) | Instagram：识别评论扩展按钮的点击 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/Fingerprint.kt) | Instagram：定位“评论操作”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/copyComment`

| 文件名 | 作用 |
| --- | --- |
| [CopyCommentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/copyComment/CopyCommentPatch.kt) | Instagram：接入复制评论文字 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/copyComment/Fingerprint.kt) | Instagram：定位“评论文字复制”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/debugComment`

| 文件名 | 作用 |
| --- | --- |
| [DebugCommentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/debugComment/DebugCommentPatch.kt) | Instagram：接入评论对象调试 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/debugComment/Fingerprint.kt) | Instagram：定位“评论调试”对应的原生方法或字段 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/saveMediaComment`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/saveMediaComment/Fingerprint.kt) | Instagram：定位“评论媒体保存”对应的原生方法或字段 |
| [SaveMediaCommentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/comment/saveMediaComment/SaveMediaCommentPatch.kt) | Instagram：接入评论媒体保存 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/customise/font`

| 文件名 | 作用 |
| --- | --- |
| [CustomFontPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/customise/font/CustomFontPatch.kt) | Instagram：接入自定义字体 |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/customise/font/Fingerprints.kt) | Instagram：定位“自定义字体”对应的原生方法或字段 |
| [FontHooks.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/customise/font/FontHooks.kt) | Instagram：定位并接通字体替换调用 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/downloadVoiceMessagePatch`

| 文件名 | 作用 |
| --- | --- |
| [DownloadVoiceMessagePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/downloadVoiceMessagePatch/DownloadVoiceMessagePatch.kt) | Instagram：接入语音消息下载 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/makeEphemeralPermanent`

| 文件名 | 作用 |
| --- | --- |
| [MakeEphemeralPermanentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/makeEphemeralPermanent/MakeEphemeralPermanentPatch.kt) | Instagram：保留临时私信内容 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/markChatAsReadPatch`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/markChatAsReadPatch/Fingerprints.kt) | Instagram：定位“聊天手动标记已读”对应的原生方法或字段 |
| [MarkChatAsReadPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/markChatAsReadPatch/MarkChatAsReadPatch.kt) | Instagram：接入手动标记聊天已读 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/saveAllMessages`

| 文件名 | 作用 |
| --- | --- |
| [SaveAllMessagesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/directMessage/saveAllMessages/SaveAllMessagesPatch.kt) | Instagram：接入聊天消息保存 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/disableVideoAutoplay`

| 文件名 | 作用 |
| --- | --- |
| [DisableVideoAutoplayPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/disableVideoAutoplay/DisableVideoAutoplayPatch.kt) | Instagram：关闭视频自动播放 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree`

| 文件名 | 作用 |
| --- | --- |
| [DisableSwipeToCreatePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/DisableSwipeToCreatePatch.kt) | Instagram：关闭滑动进入创作界面 |
| [HideGroupCreationOnShareSheetPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/HideGroupCreationOnShareSheetPatch.kt) | Instagram：隐藏分享面板的建群入口 |
| [HideNotesTrayPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/HideNotesTrayPatch.kt) | Instagram：隐藏便签栏 |
| [HideStoriesTrayPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/HideStoriesTrayPatch.kt) | Instagram：隐藏快拍栏 |
| [LimitFeedToFollowingProfiles.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/LimitFeedToFollowingProfiles.kt) | Instagram：把首页内容限制为已关注用户 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/doubleTap`

| 文件名 | 作用 |
| --- | --- |
| [DisableDoubleTapOnCommentPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/doubleTap/DisableDoubleTapOnCommentPatch.kt) | Instagram：关闭评论双击操作 |
| [DisableDoubleTapOnMessagePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/doubleTap/DisableDoubleTapOnMessagePatch.kt) | Instagram：关闭私信双击操作 |
| [DisableDoubleTapOnPostPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/doubleTap/DisableDoubleTapOnPostPatch.kt) | Instagram：关闭帖子双击操作 |
| [DisableDoubleTapOnReelPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/doubleTap/DisableDoubleTapOnReelPatch.kt) | Instagram：关闭 Reels 双击操作 |
| [DisableDoubleTapPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/doubleTap/DisableDoubleTapPatch.kt) | Instagram：汇总双击操作控制 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/focusLock`

| 文件名 | 作用 |
| --- | --- |
| [FocusLockPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/distractionFree/focusLock/FocusLockPatch.kt) | Instagram：接入专注锁定 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/saveMessages`

| 文件名 | 作用 |
| --- | --- |
| [DeletedMessagesResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/saveMessages/DeletedMessagesResourcePatch.kt) | Instagram：提供已保存删除消息的界面资源 |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/saveMessages/Fingerprint.kt) | Instagram：定位“聊天消息保存”对应的原生方法或字段 |
| [SaveDeletedMessagesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/dm/saveMessages/SaveDeletedMessagesPatch.kt) | Instagram：接入被删除消息的保存与查看 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/download`

| 文件名 | 作用 |
| --- | --- |
| [DownloadMediaPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/DownloadMediaPatch.kt) | Instagram：接入帖子媒体下载 |
| [FeedDownloadLithoHook.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/FeedDownloadLithoHook.kt) | Instagram：定位收藏组件、属性设置器与原生行状态，注入左侧下载按钮并绑定直接访问桥接 |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/Fingerprints.kt) | Instagram：定位“媒体下载”对应的原生方法或字段 |
| [ProfileDownloadPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/ProfileDownloadPatch.kt) | Instagram：从目标 APK 解析帖子网格、原生分页与生命周期契约，注入主页批量下载及分页桥接调用 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/extension`

| 文件名 | 作用 |
| --- | --- |
| [SharedExtensionPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/extension/SharedExtensionPatch.kt) | Instagram：把共享扩展合入安装包并接入初始化 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/extension/hooks`

| 文件名 | 作用 |
| --- | --- |
| [ApplicationInitHook.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/extension/hooks/ApplicationInitHook.kt) | Instagram：在应用初始化时执行扩展初始化 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/externalDownloader`

| 文件名 | 作用 |
| --- | --- |
| [ExternalDownloaderPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/externalDownloader/ExternalDownloaderPatch.kt) | Instagram：接入外部下载器操作 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/ghostMode`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/ghostMode/Fingerprint.kt) | Instagram：定位“隐身与已读控制”对应的原生方法或字段 |
| [ViewDmAnonymouslyPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/ghostMode/ViewDmAnonymouslyPatch.kt) | Instagram：接入匿名查看私信 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/hookFlags`

| 文件名 | 作用 |
| --- | --- |
| [HookFlagsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/hookFlags/HookFlagsPatch.kt) | Instagram：接通实验功能开关读取 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/improveImageViewing`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/improveImageViewing/Fingerprint.kt) | Instagram：定位“图片查看优化”对应的原生方法或字段 |
| [ImproveImageViewingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/improveImageViewing/ImproveImageViewingPatch.kt) | Instagram：改善图片查看操作 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/instants`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/instants/Fingerprints.kt) | Instagram：定位“Instants 媒体保存”对应的原生方法或字段 |
| [InstantsDownloadPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/instants/InstantsDownloadPatch.kt) | Instagram：接入 Instants 下载与保存 |
| [InstantsDownloadResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/instants/InstantsDownloadResourcePatch.kt) | Instagram：提供 Instants 保存界面资源 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs`

| 文件名 | 作用 |
| --- | --- |
| [CheckMappingsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/CheckMappingsPatch.kt) | Instagram：检查原生类型和方法映射 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/developerOptions`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/developerOptions/Fingerprint.kt) | Instagram：定位“开发者选项”对应的原生方法或字段 |
| [RecommendedFlagsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/developerOptions/RecommendedFlagsPatch.kt) | Instagram：提供推荐的实验功能开关配置 |
| [UnlockDeveloperOptionPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/developerOptions/UnlockDeveloperOptionPatch.kt) | Instagram：打开开发者选项入口 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/employeeOptions`

| 文件名 | 作用 |
| --- | --- |
| [UnlockEmployeeOptionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/employeeOptions/UnlockEmployeeOptionsPatch.kt) | Instagram：打开员工选项入口 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/whitehatSettings`

| 文件名 | 作用 |
| --- | --- |
| [AllowUserNetworkCertificatePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/internalStuffs/whitehatSettings/AllowUserNetworkCertificatePatch.kt) | Instagram：允许使用用户安装的网络证书 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/moreOptionsOnPost`

| 文件名 | 作用 |
| --- | --- |
| [MoreOptionsOnPostPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/moreOptionsOnPost/MoreOptionsOnPostPatch.kt) | Instagram：向帖子加入更多操作 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/navigation`

| 文件名 | 作用 |
| --- | --- |
| [NavigationBarPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/navigation/NavigationBarPatch.kt) | Instagram：接入导航栏自定义 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/notification`

| 文件名 | 作用 |
| --- | --- |
| [FixNotificationRegistrationCrashPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/notification/FixNotificationRegistrationCrashPatch.kt) | Instagram：修复通知注册路径的崩溃 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/Fingerprints.kt) | Instagram：定位“更多菜单按钮”对应的原生方法或字段 |
| [HookOverflowMenuButton.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/HookOverflowMenuButton.kt) | Instagram：定位并接通更多菜单按钮 |
| [HookOverflowMenuButtonOnClickPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/HookOverflowMenuButtonOnClickPatch.kt) | Instagram：接通更多菜单扩展按钮的点击 |
| [IncludeButtonInOverflowMenuArrayPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/IncludeButtonInOverflowMenuArrayPatch.kt) | Instagram：把扩展按钮加入更多菜单条目 |
| [OverflowMenuButtonAttributesPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/OverflowMenuButtonAttributesPatch.kt) | Instagram：提供更多菜单按钮属性 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/debugOverflowButton`

| 文件名 | 作用 |
| --- | --- |
| [DebugOverflowMenuButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/posts/debugOverflowButton/DebugOverflowMenuButtonPatch.kt) | Instagram：加入更多菜单调试操作 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/reels`

| 文件名 | 作用 |
| --- | --- |
| [HookReelOverflowMenuButton.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/reels/HookReelOverflowMenuButton.kt) | Instagram：接通 Reels 更多菜单按钮 |
| [ReelsOverflowMenuButtonEntity.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/overflowMenuButton/reels/ReelsOverflowMenuButtonEntity.kt) | Instagram：适配 Reels 菜单按钮模型 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/privacy`

| 文件名 | 作用 |
| --- | --- |
| [DisableScreenshotDetectionPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/privacy/DisableScreenshotDetectionPatch.kt) | Instagram：关闭截图检测 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/reels`

| 文件名 | 作用 |
| --- | --- |
| [DisableReelsScrollingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/reels/DisableReelsScrollingPatch.kt) | Instagram：关闭 Reels 滑动切换 |
| [HideReelsFollowButton.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/reels/HideReelsFollowButton.kt) | Instagram：隐藏 Reels 关注按钮 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/removeEmptyBottomSpace`

| 文件名 | 作用 |
| --- | --- |
| [RemoveEmptyBottomSpacePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/removeEmptyBottomSpace/RemoveEmptyBottomSpacePatch.kt) | Instagram：移除界面底部多余空白 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/settings`

| 文件名 | 作用 |
| --- | --- |
| [Fingerprint.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/settings/Fingerprint.kt) | Instagram：定位“设置页面和配置”对应的原生方法或字段 |
| [NativeSettingsSwitchStylePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/settings/NativeSettingsSwitchStylePatch.kt) | Instagram：使用原生风格设置开关 |
| [SettingsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/settings/SettingsPatch.kt) | Instagram：把 Piko 设置入口接入原生页面 |
| [SettingsResourcePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/settings/SettingsResourcePatch.kt) | Instagram：合入 Piko 设置页面资源 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories`

| 文件名 | 作用 |
| --- | --- |
| [HandleStoryButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/HandleStoryButtonPatch.kt) | Instagram：接通快拍扩展按钮点击 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/customiseStoryRingSize`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseStoryRingSizePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/customiseStoryRingSize/CustomiseStoryRingSizePatch.kt) | Instagram：调整快拍圆环大小 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/customiseStoryTimestamp`

| 文件名 | 作用 |
| --- | --- |
| [CustomiseStoryTimestampPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/customiseStoryTimestamp/CustomiseStoryTimestampPatch.kt) | Instagram：调整快拍时间显示 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/disableStoryFlipping`

| 文件名 | 作用 |
| --- | --- |
| [DisableStoryFlippingPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/disableStoryFlipping/DisableStoryFlippingPatch.kt) | Instagram：关闭快拍自动翻页 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/loopStory`

| 文件名 | 作用 |
| --- | --- |
| [InterfaceStoryLoop.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/loopStory/InterfaceStoryLoop.kt) | Instagram：解析两种故事视频播放器的绝对跳转与重播接口 |
| [LoopStoryPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/loopStory/LoopStoryPatch.kt) | Instagram：接入快拍循环播放 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/viewstorymention`

| 文件名 | 作用 |
| --- | --- |
| [ViewStoryMentionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/stories/viewstorymention/ViewStoryMentionsPatch.kt) | Instagram：接入快拍提及信息查看 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme`

| 文件名 | 作用 |
| --- | --- |
| [ComposePrismBlackPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ComposePrismBlackPatch.kt) | Instagram：处理 Compose Prism 深色背景的纯黑配色 |
| [ThemeBytecode.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemeBytecode.kt) | Instagram：接通原生主题的字节码调用 |
| [ThemeComposeBytecode.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemeComposeBytecode.kt) | Instagram：接通 Compose 主题调用 |
| [ThemeFingerprints.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemeFingerprints.kt) | Instagram：定位“主题配色”对应的原生方法或字段 |
| [ThemeLegacyRadioBytecode.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemeLegacyRadioBytecode.kt) | Instagram：接通旧式主题单选控件 |
| [ThemeOverlayTable.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemeOverlayTable.kt) | Instagram：提供主题覆盖表 |
| [ThemePalette.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemePalette.kt) | Instagram：提供主题调色板 |
| [ThemePatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemePatch.kt) | Instagram：汇总主题替换补丁 |
| [ThemeResources.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/theme/ThemeResources.kt) | Instagram：合入主题资源 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/unlockPlusBenefits`

| 文件名 | 作用 |
| --- | --- |
| [UnlockPlusBenefitsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/unlockPlusBenefits/UnlockPlusBenefitsPatch.kt) | Instagram：接入 Plus 功能入口 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/misc/userProfile`

| 文件名 | 作用 |
| --- | --- |
| [FriendshipStatusIndicatorPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/userProfile/FriendshipStatusIndicatorPatch.kt) | Instagram：显示与用户的关注关系 |
| [ProfileMoreOptionsPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/userProfile/ProfileMoreOptionsPatch.kt) | Instagram：扩展用户主页操作菜单 |
| [UserProfileButtonPatch.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/misc/userProfile/UserProfileButtonPatch.kt) | Instagram：向用户主页加入扩展按钮 |

## `patches/src/main/kotlin/app/crimera/patches/instagram/utils`

| 文件名 | 作用 |
| --- | --- |
| [Constants.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/utils/Constants.kt) | Instagram：定义扩展常量及宿主类型描述符 |
| [EntityBridge.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/utils/EntityBridge.kt) | Instagram：解析易变模型接口并生成安全参数的原生直接调用桥接 |
| [Utils.kt](../../patches/src/main/kotlin/app/crimera/patches/instagram/utils/Utils.kt) | Instagram：提供扩展共用的页面、资源和模型辅助操作 |
