#!/usr/bin/env python3
"""Generate Chinese filename/purpose tables for Git-tracked repository files."""
from __future__ import annotations

import collections
import os
from pathlib import Path
import subprocess
from urllib.parse import quote

ROOT = Path(__file__).resolve().parents[1]
GUIDE = "FILE_GUIDE.md"
SCRIPT = "scripts/update-file-guide.py"
PAGES = {
    "project": ("项目配置与维护", "构建配置、发布流程、问题模板和仓库维护文件。"),
    "newx-patches": ("新版 X 补丁入口", "修补安装包时运行的 Kotlin 代码：定位原生实现并注入调用。"),
    "newx-runtime": ("新版 X 运行时代码", "注入安装包后随 X 运行的设置、下载、通知、过滤及界面实现。"),
    "legacy-twitter": ("旧版 Twitter / X 代码", "保留的旧版补丁和扩展；当前 NewX 构建排除这些实现，不能据此判断 12.34 已支持相应功能。"),
    "legacy-instagram": ("Instagram 代码", "Instagram 补丁和扩展，包含主页媒体下载、原生分页接入、串行下载与顶部进度；combined 构建与 NewX 一同打包。分表文件名为历史沿用。"),
    "shared": ("共享代码与工具", "多个应用共用的存储、设置、请求、界面辅助及通用补丁工具。部分通用实现来自外部依赖，不在此仓库内。"),
    "stubs": ("编译期类型占位", "为编译提供宿主应用、Android 库及 Kotlin 类型签名；这些占位类不是实际功能实现。"),
    "resources-strings": ("文字、翻译与选项资源", "按语言和应用分组的界面文字及选项资源；新版 X 简体中文位于 values-zh-rCN/newx。"),
    "resources-assets": ("图标、图片与布局资源", "应用图标、品牌替换图片、设置布局和其他 Android 资源。"),
    "validation": ("测试、验证与历史说明", "回归测试、安装包修补结果、失败日志、来源校验和发布说明；历史记录不等于当前版本的兼容承诺。"),
}
OUTPUTS = [GUIDE, *[f"docs/file-guide/{key}.md" for key in PAGES]]

# Explicit descriptions for the active NewX implementation. Keep these specific to behavior.
NEWX_PATCHES = dict(line.split("|", 1) for line in """
HideAdsPatch|隐藏新版 X 时间线中的广告条目
TimelineAdModelAdapterPatch|把原生广告模型接入时间线广告过滤逻辑
DisableBlurPatch|关闭原生界面模糊效果
RestoreTwitterBrandingPatch|把 X 品牌资源替换为 Twitter 名称和图标
RestoreTwitterTopBarLogoPatch|恢复顶部 Twitter 鸟形标志
BrowseObjectPatch|在帖子菜单接入对象浏览与调试入口
CanonicalUrlsPatch|把规范原始链接解析接入原生链接打开流程
ClonePatch|修改应用标识，为与原版共存创建克隆应用
CrashLogsPatch|接入崩溃日志收集、复制和设置入口
CustomFontPatch|接入自定义字体替换
CustomLinksPatch|接入自定义域名链接的处理和路由
CustomSharingDomainPatch|接入自定义分享域名
CustomizeDrawerPatch|修改侧边抽屉条目的显示、顺序和打开方式
DrawerRowAnalysis|分析侧边抽屉条目模型及对应的原生调用
DynamicColorFingerprints|定位动态配色需要的原生主题方法
DynamicColorPatch|把动态配色和主题调色板接入原生界面
ExtensionPatch|把 NewX 扩展及初始化调用合入安装包
Hooks|定义 NewX 扩展初始化所需的宿主调用位置
FeatureFlagPatch|接入实验功能开关覆盖
ClassicInlineActionSpacingPatch|接入帖子动作栏的经典间距
CoilThumbnailCachePatch|接入 Coil 图片缩略图缓存
CustomizeInlineActionsPatch|调整帖子下方动作按钮的显示与顺序
DownloadBannerContentPatch|让下载通知完整换行，并在原生通知内绘制进度条
GlideThumbnailCachePatch|接入 Glide 图片缩略图缓存
InAppNotificationPatch|接通 X 原生顶部通知的模型与发送入口
InlineDownloadButtonPatch|在帖子动作栏加入媒体下载按钮
NativeDownloadRedirectPatch|把 X 原生下载操作重定向到所选保存目录
ThumbnailCachePatch|协调缩略图缓存补丁的依赖与接入
UnifiedDownloadNotificationPatch|统一下载进度、完成、失败和取消的顶部通知及生命周期
ForceHighestVideoQualityPatch|接入视频清晰度选择与高画质策略
PreferredVideoResolution|解析所选视频分辨率及其匹配规则
CustomizeMediaSheetOptionsPatch|调整媒体操作面板中显示的选项
DefaultMediaTabPatch|设置主页默认打开的媒体标签页
ProfileBulkDownloadPatch|接入主页全部图片、视频和 GIF 的批量下载入口
ProfilePhotosGalleryPatch|把主页照片标签页接入照片画廊
FixedBottomBarPatch|固定手机主底栏的滚动可见状态
FloatingGlassNavBarPatch|为手机主底栏接入悬浮圆角、玻璃背景和边缘效果
NavBarCustomizationPatch|接入底部导航的隐藏、排序和替换
NavBarTabData|识别和读取底部导航标签的数据结构
HidePostReplyBarPatch|隐藏帖子详情底部回复栏并处理留白
CustomizePostOptionsPatch|接入帖子菜单选项的自定义过滤
PostOptionsPatch|接通原生帖子菜单和扩展操作
DefaultProfilePostSortingPatch|设置主页帖子的默认排序
ProfileTabsCustomizationPatch|接入主页标签页的显示、顺序和默认选择
DefaultReplySortingPatch|设置帖子的默认回复排序
ServerLoggingPatch|接入服务端响应日志收集与导出
ShareImagePatch|接入把帖子分享为图片的原生功能
DisableVideoScrollingPatch|关闭视频上下滑动切换
BooleanModelLabels|按语义标签识别原生模型中的布尔属性
PostModels|识别帖子、作者、媒体及统计相关的原生模型
SemanticIntrospection|提供按字段和方法语义分析原生模型的辅助逻辑
TimelineModels|识别时间线、游标、请求和分页结果模型
DownloadFingerprints|定位原生下载权限与媒体下载相关方法
HidePremiumUpsellPatch|隐藏 Premium 升级推广入口
UnlockColorCustomizationPatch|接入原生颜色自定义功能
UnlockDownloadsPatch|解除原生媒体下载入口的限制
Fingerprints|定位新版设置页面及入口对应的原生方法
NewXSettingsContributions|汇总各项 NewX 功能向设置页注册的选项
PikoSettingsIcon|向设置入口提供 Piko 图标资源
SettingsCategories|定义 Piko 设置的分类及排序
SettingsPatch|把 Piko 设置入口接入 X 原生设置页面
SettingsResourcePatch|合入新版设置需要的文字和图标资源
SettingsRowModelHook|把 Piko 设置行接入原生设置模型
CustomizeTimelineTabsPatch|调整首页时间线标签页的显示和顺序
DisableAutomaticContentRefreshPatch|汇总禁止自动刷新所需的补丁，保留手动刷新与分页
DisableTimelineRefreshPatch|在原生时间线请求中识别并拦截自动刷新
ForYouTopicFilterPatch|接入“为你推荐”主题筛选
HideAiGeneratedPostsPatch|过滤 AI 生成的帖子
HideDiscoverMorePatch|隐藏“发现更多”推荐内容
HideNewPostButtonPatch|隐藏新帖按钮
HideNewPostPillPatch|隐藏提示有新帖可读的浮动提示条
HidePostDividersPatch|隐藏帖子之间的分隔线
HideSpacesBarPatch|隐藏 Spaces 语音空间栏
HideTimelineTabsBarPatch|隐藏时间线标签栏
HideWhoToFollowPatch|隐藏推荐关注条目
NewXTimelineSuccessFingerprint|定位时间线成功响应的模型与方法
PostPoliciesPatch|接入帖子、社区附注及相关交互策略
RestorePinnedHomeTabPatch|恢复固定的首页时间线标签选择
RestoreTimelinePositionPatch|接入时间线滚动位置的保存与恢复
ShowPollResultsPatch|在投票相关界面接入结果显示
ShowSensitiveMediaPatch|接入敏感媒体显示策略
TimelineFilterPatch|汇总时间线条目过滤规则
HideVerifiedPostsPatch|接入认证账号帖子过滤
PostFilterPatch|接入帖子文本与作者规则过滤
TimelineTextModelAdapterPatch|把原生帖子文本模型接入规则过滤器
Constants|保存 NewX 补丁和扩展共用的描述符常量
""".strip().splitlines())

NEWX_RUNTIME = dict(line.split("|", 1) for line in """
FeatureSwitchAdapter|绘制实验功能开关列表
FeatureSwitchFragment|提供实验功能开关的设置页面
FeatureSwitchImportExport|导入和导出实验功能开关配置
FeatureSwitchStore|保存实验功能开关的覆盖值
FilteredRepliesDialog|展示被过滤的回复
FilteredRepliesStore|保存被过滤回复的相关数据
BottomBarVisibility|按设置保持底栏可见，保留其他原生隐藏条件
CanonicalUrlResolver|解析规范原始链接
CustomLinkActivity|接收自定义域名链接的 Android 页面入口
CustomLinkRouter|把自定义链接转换并交给正确的原生页面
CustomSharingDomainValidator|检查自定义分享域名的格式
DownloadBannerController|驱动顶部下载通知及文字、进度条的原生状态更新
DownloadBannerPolicy|管理独立任务、通知去重、结果排队与取消归属
DownloadFileName|解析媒体文件名模板并生成保存名称
DownloadOptionsFragment|提供下载方式、命名、画质等选项页面
DownloadProgressDisplay|把真实下载字节与速度更新为顶部进度；保留旧目标的进度视图
DownloadProgressState|累计每个下载任务的大小、百分比、等待状态和速度
DownloadSettings|读取并管理下载相关设置
DrawerCatalog|提供侧边抽屉可选条目的目录
DrawerEditorFragment|提供侧边抽屉条目的编辑页面
DrawerItemFilter|按配置过滤侧边抽屉条目
DrawerTabOpener|打开抽屉条目对应的原生标签页
DropIndicatorLayout|在拖动排序时绘制插入位置提示
FilteredRepliesPostOptionHandler|处理帖子菜单中的已过滤回复入口
FloatingGlassNavBar|读取玻璃底栏设置并调用原生视觉效果
GalleryCacheStatsFragment|显示画廊缩略图缓存统计和管理入口
InlineActionBarSpacing|调整帖子动作栏的间距
InlineActionFilter|按设置过滤帖子动作按钮
InlineDownloadButton|处理帖子下载按钮的点击和媒体收集
MediaDiskCache|管理媒体缩略图的磁盘缓存
MediaLabels|生成媒体类型与项目的显示名称
MediaMerger|下载并合成长图等需要合并的媒体
MediaPickerDialog|显示媒体选择、清晰度与操作对话框
MediaSheetFilter|按配置过滤媒体操作面板选项
MediaTabResolver|解析主页媒体标签页的选择
MediaThumbnailLoader|读取并显示媒体缩略图
NativeDownloadRouter|把原生下载请求转交 Piko 下载流程
NavBarCatalog|提供可选底部导航条目
NavBarConfig|保存底部导航的顺序与配置
NavBarEditorFragment|提供底部导航编辑页面
NavBarFilter|按设置过滤底部导航项
NavBarReplacement|处理底部导航项目的替换
NewXCrashCopyReceiver|处理崩溃日志的复制操作
NewXCrashHandler|收集和保存 NewX 扩展崩溃日志
NewXCrashPostOptionHandler|处理帖子菜单中的崩溃日志操作
NewXDownloadFolders|选择保存目录并生成按作者区分的子目录
NewXDownloadTexts|把下载状态转换为本地化文字
NewXDownloader|组织媒体下载请求、事件监听和完成处理
NewXInAppNotification|连接原生顶部通知、任务队列和进度状态的桥接入口
NewXNotificationTexts|整理原生通知需要显示的文字
NewXObjectBrowserHandler|处理原生对象浏览和调试操作
NewXPostOptionActions|分发扩展添加的帖子菜单操作
NewXPostOptions|提供帖子菜单的扩展选项
NewXShareImageHandler|处理帖子分享为图片的操作
PollResultsFormatter|格式化投票结果和百分比
PostOptionsFilter|按设置过滤帖子菜单选项
ProfileBulkDownload|执行主页全部媒体下载，管理会话、取消和结果汇总
ProfileBulkPager|控制主页分页、停滞判断、空页与失败重试
ProfilePhotosGallery|构建主页照片画廊及媒体下载入口
ProfilePostSortingResolver|解析主页帖子的默认排序设置
ProfileTabFilter|按配置过滤主页标签页
ProfileTabsCatalog|提供主页标签页的条目目录
ProfileTabsConfig|保存主页标签页的顺序和选择配置
ProfileTabsEditorFragment|提供主页标签页编辑页面
ReplyBarInsets|处理隐藏回复栏后的底部留白
ReplySortingResolver|解析默认回复排序设置
ResolutionChooserDialog|显示视频分辨率选择对话框
RowDragShadow|为列表条目拖动排序绘制拖动影像
ShareUrlResolver|整理分享链接及自定义分享域名
UpdateFont|载入并应用所选自定义字体
AhoMatcher|使用多模式匹配查找帖子中的过滤词
PhraseMatcher|执行短语匹配
PostFilterFragment|提供帖子过滤规则编辑页面
PostFilterMatcher|判断帖子是否命中过滤规则
PostFilterRule|表示一条帖子过滤规则
PostFilterRuleAdapter|绘制帖子过滤规则列表
PostFilterRuleStore|保存和读取帖子过滤规则
VerifiedAccountWhitelistFragment|提供认证账号过滤白名单编辑页面
VerifiedAccountWhitelistStore|保存认证账号过滤白名单
BuiltInSettings|注册内置设置及默认值
ChangelogAction|打开版本更新说明
ComposeSettingsHook|把原生 Compose 设置操作连接到扩展页面
NewXCrashSettingsAction|处理设置页中的崩溃日志入口
NewXLogger|记录 NewX 扩展日志
NewXSettingsActivity|承载 Piko 设置界面的 Android 页面
NewXSettingsHost|组织设置页面、入口和页面切换
PikoSettingsIcon|绘制设置入口的 Piko 图标
ServerLogExportAction|导出服务端日志
DynamicColorPalette|生成和查询动态颜色调色板
TwitterTheme|定义 Twitter 风格主题配色
ForYouTopicFilter|保存并应用“为你推荐”主题筛选
ForYouTopicFilterFragment|提供主题筛选的完整设置页面
ForYouTopicFilterSheet|提供主题筛选的底部选择面板
NewXTimelineFilter|在运行时过滤时间线条目
PinnedHomeTabRestore|保存并恢复固定的首页标签页
TimelineModelAccess|通过已注入的接口读取时间线模型
TimelineRefreshGate|判断自动刷新是否应被阻止，放行手动刷新和分页
TimelineScrollPositionStore|保存并恢复时间线滚动位置
TimelineTabFilter|按配置过滤时间线标签页
BottomSheetView|提供底部弹出面板视图
IconView|提供图标显示控件
ListItem|表示设置或列表中的一条项目
LoadingIndicatorView|提供加载状态指示视图
NewXSettingsTheme|为 Piko 设置页面应用主题
Theme|查询界面主题、文字色和强调色
NewXUtils|提供 NewX 上下文、页面及常用操作辅助
ToStringParser|从原生模型文字表示中解析语义字段
""".strip().splitlines())

EXACT = {
    "docs/instagram-validation/check-ig-string-context.py": "编译真实IgStr，验证443项中文绑定绕过宿主下载语言表，覆盖.15标签错配反例",
    "docs/instagram-validation/2026-10-19-instagram-label-mismatch.md": "记录中文按钮错配、宿主语言包资源边界、恢复.14读取方式和实机复验范围",
    "docs/instagram-validation/instagram-451-label-fix-static.json": "记录.16资源读取边界、全部中文资源和左侧下载按钮最终检查",
    "docs/instagram-validation/instagram-451-label-fix-verifier.json": "记录.16全包分支与结果读取结构检查",
    "docs/instagram-validation/validated-instagram-451-label-fix-2026-10-19.json": "记录.16中文错配修复构建、来源、回归反例、451修补结果与实机边界",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/FeedDownloadButton.java": "Instagram：在收藏左侧添加原生下载组件，按当前行的媒体、轮播位置及页面环境执行下载",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/InstagramCrashHandler.java": "Instagram：通过系统Downloads保存崩溃报告，保留私有待发布副本并在下次启动重试",
    "patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/FeedDownloadLithoHook.kt": "Instagram：定位收藏组件、属性设置器与原生行状态，注入左侧下载按钮并绑定直接访问桥接",
    "docs/instagram-validation/instagram-451-footer-locale-static.json": "记录.15下载按钮插入顺序、点击桥接、全部中文资源与原有下载功能的最终检查",
    "docs/instagram-validation/instagram-451-footer-locale-verifier.json": "记录.15全包分支目标及结果读取的结构审计",
    "docs/instagram-validation/validated-instagram-451-footer-locale-2026-10-18.json": "记录.15的源码、构建工件、测试与451修补验证，并说明首次切中文退出尚未确诊",
    ".envrc": "进入目录时载入 Nix 开发环境",
    ".gitattributes": "规定 Git 的换行和文件属性",
    ".gitignore": "排除构建产物、缓存及本地临时文件",
    ".releaserc": "配置自动版本发布和更新日志生成",
    "AGENTS.md": "规定本仓库的修改、字节码注入、验证与设备操作规则",
    "CHANGELOG.md": "记录历史版本变更",
    "LICENSE": "项目的 GPLv3 授权条款",
    "NOTICE": "说明上游来源、版权和授权声明",
    "README.md": "项目首页：导入、功能、使用方法和当前验证范围",
    GUIDE: "中文文件用途总入口及常用功能到源码的对照表",
    SCRIPT: "生成并检查本中文文件用途索引，新增文件时可重新运行",
    "baseline_patch_times.txt": "记录历史 X-Lite 12.17.3 alpha 补丁的耗时基线",
    "build.gradle.kts": "定义根项目插件和各扩展模块的构建规则",
    "settings.gradle.kts": "声明模块、插件及依赖仓库",
    "gradle.properties": "保存补丁包版本和 Gradle 构建参数",
    "gradlew": "在 Linux / macOS 上启动固定版本的 Gradle",
    "gradlew.bat": "在 Windows 上启动固定版本的 Gradle",
    "gradle/libs.versions.toml": "集中声明依赖库、插件及版本",
    "gradle/wrapper/gradle-wrapper.jar": "Gradle Wrapper 的启动程序",
    "gradle/wrapper/gradle-wrapper.properties": "指定 Wrapper 使用的 Gradle 发行版",
    "package.json": "声明自动发布和更新日志所需的 Node.js 开发依赖",
    "package-lock.json": "锁定 Node.js 发布工具的依赖版本",
    "crowdin.yml": "配置 Crowdin 翻译资源同步",
    "dns-probe.sh": "在 Termux 等环境检查 X 媒体域名的 DNS 和 HTTPS 连通性",
    "flake.nix": "定义 Nix 开发环境及工具依赖",
    "flake.lock": "锁定 Nix 开发环境的依赖版本",
    "patch-twitter.sh": "验证当前补丁包来源后修补显式指定的原始 APK / APKM，并检查失败与重建结果",
    "patch-instagram.sh": "构建或校验当前 combined 补丁包来源，修补显式指定的原始 Instagram APK / APKM，并核对修补与重建结果",
    "patches-bundle.json": "Morphe 读取的稳定补丁源：版本、下载地址、发布时间和说明",
    "patches-list.json": "供工具使用的补丁清单数据；当前可用项以发布包和 README 为准",
    "extensions/proguard-rules.pro": "控制扩展代码压缩与保留，维持补丁调用所需的类和方法",
    "patches/src/main/resources/instagram/settings/raw/piko_tabler_icons_license.txt": "说明所使用 Tabler 图标的版权和授权条款",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadRatePolicy.java": "Instagram：计算服务端拒绝后的至少 15 分钟冷却，并解析媒体文件请求的更长 Retry-After",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/DownloadUtils.java": "Instagram：把单帖、头像、语音及私信媒体下载交给串行队列，并为主页媒体生成下载请求",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/InstagramDownloads.java": "Instagram：串行执行媒体文件下载，管理独立任务、暂停、传输进度与跨任务冷却",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/NativeProfileBridge.java": "Instagram：提供修补时替换为原生直接调用的主页、账号、分页响应及下一页访问桥接",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloadStore.java": "Instagram：按登录账号、目标主页和媒体编号保存已完成文件记录，继续前检查 SAF 文档是否仍存在",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfileDownloader.java": "Instagram：控制前台主页批量下载，收集帖子网格媒体，串行保存后等待 30 秒再请求原生下一页",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/ProfilePageState.java": "Instagram：管理原生主页分页状态，区分缓存与网络响应，检测重复游标、停滞及可靠的结束标记",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/TopDownloadNotice.java": "Instagram：绘制可换行的顶部下载卡片、百分比及进度条，管理暂停入口与各任务结果队列",
    "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/userprofile/ProfileMoreOption.java": "Instagram：处理主页更多资料选项，包括下载此主页全部图片和视频的入口",
    "extensions/instagram/src/test/java/app/morphe/extension/instagram/patches/download/ProfileDownloadStateTest.java": "验证 Instagram 缓存与分页完成判断、并发到达页面的保留、异常游标拦截、拒绝冷却期限，以及暂停后停止读取轮播媒体信息",
    "patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/ProfileDownloadPatch.kt": "Instagram：从目标 APK 解析帖子网格、原生分页与生命周期契约，注入主页批量下载及分页桥接调用",
    "docs/instagram-validation/README.md": "说明 Instagram 主页下载的最新验证记录、历史基线、功能边界和手机试用范围",
    "docs/instagram-validation/InstagramDownloadStaticReview.java": "离线检查已修补 Instagram APK 的模型直接桥接、设置开关、主页分页、串行下载、顶部卡片与资源，不注入应用",
    "docs/instagram-validation/validated-instagram-upgrade-2026-10-18.json": "记录 Instagram .13 的源码、CI、测试、同一 MPP 对451与439的修补重建结果及使用限制",
    "patches/src/test/kotlin/app/crimera/patches/instagram/filter/story/FilterStoriesPatchTest.kt": "回归451启动崩溃：检查快拍对象来源、字段循环、空值路径和合法跳转，拒绝错误/歧义循环",
    "docs/instagram-validation/InstagramVerifierAudit.java": "扫描APK全部普通分支、switch目标和结果读取，捕获启动VerifyError涉及的非法跳转",
    "docs/instagram-validation/2026-10-18-instagram-451-startup-verifyerror.md": "记录451启动崩溃的设备异常、精确反例、根因、指令修复和实机复验边界",
    "docs/instagram-validation/instagram-451-crash-13-verifier.json": "保留.13无效跳转的失败反例，与手机VerifyError方法及地址对应",
    "docs/instagram-validation/instagram-451-crash-fix-static.json": "记录.14的快拍筛选对象流、主页下载和界面资源最终检查",
    "docs/instagram-validation/instagram-451-crash-fix-verifier.json": "记录.14全包分支目标和结果读取的结构审计结果",
    "docs/instagram-validation/validated-instagram-451-crash-fix-2026-10-18.json": "记录451启动闪退修复的源码、CI、MPP散列、补丁重建、测试及设备复验边界",
    "patches/src/test/kotlin/app/crimera/patches/instagram/links/misc/SuggestionStringConstantsTest.kt": "验证R8纯文字开关表和常量参数解码，拒绝不符合契约的表或调用",
    "docs/instagram-validation/instagram-451-contract-notes.md": "说明439与451的原生类型、调用、持有者、故事调度及验证约束",
    "docs/instagram-validation/instagram-451-upgrade-static.json": "记录451最终APK的模型桥接、设置开关、主页下载、顶部进度、暂停和冷却检查",
    "docs/instagram-validation/instagram-439-upgrade-static.json": "记录439回归APK的模型桥接、设置开关、主页下载、顶部进度、暂停和冷却检查",
    "docs/instagram-validation/validated-build-2026-10-18.json": "记录 Instagram 与 X 上一轮 combined 构建的确切源码、CI、MPP 与安装包散列、修补结果及检查统计",
    "docs/instagram-validation/instagram-static.json": "记录上一轮 Instagram 最终 APK 的主页下载、串行下载、顶部进度及资源静态检查明细",
    "docs/instagram-validation/instagram-static-pause-fix.json": "记录本轮 Instagram 最终 APK 的109项检查，包括分页冷却、准备暂停及晚到进度终态保护",
    "docs/instagram-validation/validated-build-2026-10-18-pause-fix.json": "记录本轮 Instagram .12 源码、373项测试、MPP与原版安装包散列、60项修补重建及109项检查结果",
    "docs/instagram-validation/x-all-banner.json": "记录上一轮 combined 构建中 X 全选 APK 的完整下载通知与任务队列静态检查明细",
    "docs/instagram-validation/x-all-bulk.json": "记录上一轮 combined 构建中 X 全选 APK 的主页媒体下载与分页静态检查明细",
    "docs/instagram-validation/x-all-fixed.json": "记录上一轮 combined 构建中 X 全选 APK 的固定底栏和禁止自动刷新静态检查明细",
    "docs/instagram-validation/x-all-glass.json": "记录上一轮 combined 构建中 X 全选 APK 的悬浮玻璃底栏静态检查明细",
    "patches/src/main/kotlin/app/crimera/patches/utils/ScopedFingerprintMatching.kt": "辅助在指定类或范围内匹配原生方法指纹",
    "patches/src/main/kotlin/app/morphe/util/resource/CheckString.kt": "检查翻译字符串及格式占位符",
    "patches/src/main/kotlin/app/revanced/patches/all/misc/activity/exportall/ExportAllActivitiesPatch.kt": "通用补丁：调整 Activity 的导出状态；当前 NewX 发布包排除此旧通用补丁",
    "patches/src/main/kotlin/app/revanced/patches/all/misc/versioncode/ChangeVersionCodePatch.kt": "通用补丁：修改应用版本代码；当前 NewX 发布包排除此旧通用补丁",
    ".github/dependabot.yml": "配置依赖更新检查",
    ".github/scripts/generate_patches_readme.py": "从补丁元数据生成补丁功能表",
    ".github/workflows/newx-12.34.yml": "构建并测试 NewX 补丁包，检查定位规则、接口描述符并记录来源",
    ".github/workflows/instagram-profile.yml": "构建并测试 Instagram 与 NewX 的 combined 补丁包，检查定位规则和接口描述符，并记录 MPP 来源",
    ".github/workflows/publish-custom-download-folder.yml": "核对已验证构建的哈希及来源后发布自定义 NewX 补丁包",
    ".github/workflows/publish-instagram-profile.yml": "核对已验证combined构建的哈希和源码来源，发布包含Instagram主页下载的补丁包",
    ".github/workflows/build_pull_request.yml": "检查拉取请求的构建",
    ".github/workflows/open_pull_request.yml": "自动化拉取请求相关维护流程",
    ".github/workflows/release.yml": "运行上游版本发布流程",
    ".github/workflows/crowdin_pull.yml": "从 Crowdin 拉取翻译更新",
    ".github/workflows/crowdin_push.yml": "向 Crowdin 推送待翻译资源",
    ".github/ISSUE_TEMPLATE/config.yml": "配置 GitHub 问题入口",
    "docs/newx-12.34-coverage.md": "对照旧版 73 项功能，列出新版迁移对应项与缺失项",
    "docs/newx-fixed-bar-and-auto-refresh.md": "说明固定底栏、禁止自动刷新及手动刷新保留范围",
    "docs/newx-floating-glass-navbar.md": "说明悬浮玻璃底栏的设计、开关和原生接入契约",
    "docs/newx-profile-bulk-download.md": "说明主页全部媒体下载、分页、取消和限制",
    "docs/newx-unified-download-banner.md": "说明顶部完整进度、结果排队及多任务通知规则",
    "x-lite/alpha-12.17.3-compatibility/README.md": "历史 12.17.3 alpha 适配说明，不代表当前支持范围",
}

SHARED_NAMES = dict(line.split("|", 1) for line in """
CustomCrashHandler|收集扩展崩溃信息
ObjectBrowser|提供对象字段浏览与调试界面
PikoUtils|提供扩展共用操作辅助
ExtensionStrings|定义扩展文字资源的访问常量
TooltipHelper|提供操作提示辅助
DownloadFileNames|生成旧下载器的保存文件名
DownloadMetadata|表示下载媒体的元数据
DownloadRequest|表示旧下载器的一次下载请求
FolderPickerActivity|承载保存目录选择页面
MediaDownloader|执行旧版 Twitter / X 与 Instagram 共用的媒体传输和文件保存，提供阻塞下载、字节进度与 HTTP 失败信息
MediaType|定义下载媒体类型
MetadataMuxer|处理保存媒体的元数据封装
StorageUtils|提供共享存储与文件操作辅助
BooleanSetting|表示和读取布尔设置
Setting|定义设置项的基础契约
StringSetting|表示和读取文字设置
BaseSharedPref|提供偏好配置读写的基础实现
SharedPref|封装共享偏好配置访问
MarkChatAsReadScope|限定将聊天标记已读的操作范围
ShareLinkSanitizer|清理分享链接中的追踪参数
Requester|执行共享网络请求
Route|表示网络请求的路由
PikoSharedPrefCategory|提供共享偏好设置分类控件
ListViewDragSelectionController|处理列表拖动选择
""".strip().splitlines())

LEGACY_DETAILS = dict(line.split("|", 1) for line in """
Pref|读取和保存应用功能设置
Utils|提供扩展共用的页面、资源和模型辅助操作
Constants|定义扩展常量及宿主类型描述符
UI|提供图标常量、按钮与对话框等界面辅助
PostType|定义帖子与媒体类型
Block|过滤推荐内容的解析字段并清理分析缓存
Links|处理分享和打开链接
SuggestedProfiles|处理推荐用户条目的显示
WelcomeMessage|在首次使用 Piko 时显示欢迎说明
Changelogs|展示版本更新说明
DatabasePatch|清理本地帖子数据库中的记录
DownloadPatch|接通媒体下载操作
FeatureSwitchPatch|读取并应用实验功能开关覆盖
InlineDownloadButton|处理帖子动作栏下载按钮
TimelineEntry|按设置过滤原生时间线条目
TimelinePosition|保存和恢复时间线滚动位置
TweetInfo|处理帖子信息和来源显示
VersionCheckPatch|识别宿主版本并向扩展提供版本判断
Customise|按配置调整导航、标签和搜索联想等界面条目
ButtonPressHandler|分发原生菜单扩展按钮的点击操作
ExtensionPatch|把扩展合入安装包并接入初始化
SharedExtensionPatch|把共享扩展合入安装包并接入初始化
Hooks|定义扩展初始化需要的原生调用位置
ApplicationInitHook|在应用初始化时执行扩展初始化
HideAds|过滤时间线广告
DisableAdsPatch|过滤广告内容
HideRecommendationItemsPatch|过滤推荐内容条目
TimelineEntryHookPatch|接通时间线条目读取与过滤
DisableChirpFontPatch|禁用 Chirp 字体
HideBookmarkInTimelinePatch|隐藏时间线中的书签按钮
HideFABMenuButtonsPatch|隐藏悬浮发布菜单中的指定按钮
HideImmersivePlayer|隐藏播放器中上滑查看更多视频的入口
RemoveViewCountPatch|隐藏帖子浏览次数
RestoreLegacyFollowerListsPatch|恢复旧式关注与粉丝列表
FeatureFlagResourcePatch|提供实验功能开关界面资源
ChangeDownloadDirPatch|接入自定义媒体保存目录
CopyMediaLink|添加复制媒体链接操作
ClearTrackingParamsPatch|清理分享链接的追踪参数
CustomDeepLinksPatch|接入自定义域名的深层链接
HandleCustomDeepLinksPatch|处理自定义域名链接的跳转
CustomSharingDomainPatch|替换分享链接使用的域名
HandleModernShareSheetLinks|处理新版分享面板生成的链接
LegacyShareLinksPatch|生成旧式分享链接
NoShortenedUrlPatch|展开短链接，使用原始地址
ResponseLogging|记录服务端响应用于调试
AppIconPatch|接入应用图标选择
AppIconResourcePatch|合入可选应用图标资源
XMLfiles|列出图标替换涉及的 XML 资源
BlockRedirectingToXLitePatch|阻止自动跳转到 X-Lite
BlockUpdateScreenPatch|屏蔽阻止使用的更新页面
BringBackTwitterPatch|恢复 Twitter 名称与品牌图标
CrashLogsPatch|接入崩溃日志收集
CustomiseExploreTabsPatch|自定义探索页标签
CustomEmojiFont|接入自定义表情字体
CustomFont|接入自定义文字字体
CustomiseFontHook|接通字体选择与原生字体调用
CustomiseInlineBarPatch|自定义帖子动作栏按钮
CustomiseNavBarPatch|自定义底部导航条目
CustomiseNotificationTabsPatch|自定义通知页标签
CustomizePostFontSize|自定义帖子文字大小
CustomiseProfileTabsPatch|自定义用户主页标签
DefaultReplySortingPatch|设置默认回复排序
CustomiseSearchTabsPatch|自定义搜索结果标签
CustomiseSideBarPatch|自定义侧边栏条目
CustomiseTimelineTabsPatch|自定义首页时间线标签
CustomiseTypeAheadResponsePatch|自定义搜索输入联想结果
HideFABPatch|隐藏悬浮发布按钮
ImportExportLoginTokenPatch|接入登录令牌的导入与导出
MoreProfileInfoPatch|显示额外用户资料
MoreProfileInfoResourcePatch|提供额外用户资料的界面资源
HideRecommendedUsersPatch|隐藏推荐用户
RoundOffNumbersPatch|调整关注数等数字的缩写显示
PauseSearchSuggestionsPatch|暂停搜索建议更新
RemoveSearchSuggestionsPatch|移除搜索建议
SelectableTextPatch|允许选择和复制文字
CheckCompatibilityPatch|检查设置与宿主版本兼容情况
SettingsPatch|把 Piko 设置入口接入原生页面
SettingsResourcePatch|合入 Piko 设置页面资源
BrowseTweetObjectPatch|在帖子菜单加入对象浏览入口
DebugMenu|提供帖子调试菜单
ExternalDownloaderPatch|接入外部下载器操作
InlineDownloadButtonPatch|向帖子动作栏加入下载按钮
NativeDownloaderPatch|接入原生媒体下载流程
NativeReaderModePatch|接入原生阅读模式
NativeReaderModeResourcePatch|提供阅读模式所需资源
NativeShareImagePatch|接入帖子分享为图片
NativeShareMenuPatch|接入原生分享菜单扩展
NativeShareMenuResourcePatch|提供原生分享菜单扩展资源
NativeTranslatorPatch|接入原生翻译操作
ShowChangelogsPatch|接入更新说明入口
EnableForcePipPatch|接入强制画中画功能
RedirectBMTab|把导航项重定向到书签页
EnableUndoPostPatch|接入撤回发帖功能
HideBannerPatch|隐藏时间线提示横幅
DeleteFromDatabasePatch|接入本地帖子数据库清理
DisableAutoScrollPatch|控制时间线的自动滚动
RestoreTimelinePosition|接入时间线滚动位置恢复
EnableVidAutoAdvancePatch|接入视频自动切换
ForceHDPatch|设置优先使用高清视频
HideCommunityBadge|隐藏社区徽章
HideHiddenRepliesPatch|隐藏折叠回复提示
HideNavBarBadges|隐藏底部导航角标
HideNudgeButtonPatch|隐藏推广引导按钮
HidePostMetrics|隐藏帖子互动统计
HideSoialProofPatch|隐藏社交关系推荐提示
HideLiveThreadsPatch|隐藏直播串流内容
RemovePremiumUpsellPatch|隐藏 Premium 升级推广
ShowSensitiveMediaPatch|允许显示敏感媒体
ShowSourceLabel|显示帖子来源应用
ShowPollResultsPatch|显示投票结果
ForceTranslate|调整翻译入口显示策略
HideCommunityNotes|隐藏社区附注
HidePromoteButton|隐藏帖子推广按钮
TweetInfoHook|把帖子信息读取接入原生模型
HideSuggestedContentPatch|隐藏建议或推荐内容
FilterStoriesPatch|按规则过滤快拍
InterceptUriPatch|拦截并处理打开链接的请求
OpenLinksExternallyPatch|使用外部浏览器打开链接
DisableCommentsPatch|关闭评论入口
DisableExplorePatch|隐藏探索内容
DisableHighlightsPatch|隐藏精选快拍
DisableStoriesPatch|隐藏快拍内容
ActivityFeedSuggestedProfiles|隐藏动态页推荐用户
DisableAnalyticsPatch|关闭分析收集相关行为
DisableDiscoverPeoplePatch|隐藏发现用户入口
HideReshareButtonPatch|隐藏转发分享按钮
ReelsSuggestedProfiles|隐藏 Reels 推荐用户
SessionReelsClassifier|在修补时保留当前账号的原生推荐用户分类器和条件
SuggestionStringConstants|解析原生常量表中提取的文字，保持语义定位
EntityBridge|解析易变模型接口并生成安全参数的原生直接调用桥接
InterfaceStoryLoop|解析两种故事视频播放器的绝对跳转与重播接口
WrappedStoryAggregate|把媒体模型接入原生故事已读请求的类型包装与聚合
DisableTypingStatusPatch|阻止发送正在输入状态
MarkStorySeenHooks|接通手动标记快拍已读的请求
StorySeenResultHooks|接通快拍已读请求的结果回传
ViewLiveAnonymouslyPatch|接入匿名观看直播
ViewStoriesAnonymouslyPatch|接入匿名观看快拍
SanitizeShareLinksPatch|清理分享链接参数
ShareLinks|生成帖子和媒体的分享链接
ValidateLinksPatch|检查原生链接是否可处理
ChatActionBarButtonPatch|向聊天工具栏加入扩展按钮
InboxActionBarButtonPatch|向私信列表工具栏加入扩展按钮
MainFeedActionBarButtonPatch|向首页工具栏加入扩展按钮
UserProfileActionBarButtonPatch|向用户主页工具栏加入扩展按钮
RemoveBuildExpiredPopupPatch|移除构建过期提示
ChangeLikeAnimationPatch|替换点赞动画
ClonePatch|修改应用标识以便与原版共存
AddCommentButtonAttributesPatch|提供评论扩展按钮的属性
AddCommentButtonPatch|在评论界面加入扩展按钮
CommentButtonClickCheckPatch|识别评论扩展按钮的点击
CopyCommentPatch|接入复制评论文字
DebugCommentPatch|接入评论对象调试
SaveMediaCommentPatch|接入评论媒体保存
CustomFontPatch|接入自定义字体
FontHooks|定位并接通字体替换调用
DownloadVoiceMessagePatch|接入语音消息下载
MakeEphemeralPermanentPatch|保留临时私信内容
MarkChatAsReadPatch|接入手动标记聊天已读
SaveAllMessagesPatch|接入聊天消息保存
DisableVideoAutoplayPatch|关闭视频自动播放
DisableSwipeToCreatePatch|关闭滑动进入创作界面
HideGroupCreationOnShareSheetPatch|隐藏分享面板的建群入口
HideNotesTrayPatch|隐藏便签栏
HideStoriesTrayPatch|隐藏快拍栏
LimitFeedToFollowingProfiles|把首页内容限制为已关注用户
DisableDoubleTapOnCommentPatch|关闭评论双击操作
DisableDoubleTapOnMessagePatch|关闭私信双击操作
DisableDoubleTapOnPostPatch|关闭帖子双击操作
DisableDoubleTapOnReelPatch|关闭 Reels 双击操作
DisableDoubleTapPatch|汇总双击操作控制
FocusLockPatch|接入专注锁定
DeletedMessagesResourcePatch|提供已保存删除消息的界面资源
SaveDeletedMessagesPatch|接入被删除消息的保存与查看
DownloadMediaPatch|接入帖子媒体下载
ViewDmAnonymouslyPatch|接入匿名查看私信
HookFlagsPatch|接通实验功能开关读取
ImproveImageViewingPatch|改善图片查看操作
InstantsDownloadPatch|接入 Instants 下载与保存
InstantsDownloadResourcePatch|提供 Instants 保存界面资源
CheckMappingsPatch|检查原生类型和方法映射
RecommendedFlagsPatch|提供推荐的实验功能开关配置
UnlockDeveloperOptionPatch|打开开发者选项入口
UnlockEmployeeOptionsPatch|打开员工选项入口
AllowUserNetworkCertificatePatch|允许使用用户安装的网络证书
MoreOptionsOnPostPatch|向帖子加入更多操作
NavigationBarPatch|接入导航栏自定义
FixNotificationRegistrationCrashPatch|修复通知注册路径的崩溃
HookOverflowMenuButton|定位并接通更多菜单按钮
HookOverflowMenuButtonOnClickPatch|接通更多菜单扩展按钮的点击
IncludeButtonInOverflowMenuArrayPatch|把扩展按钮加入更多菜单条目
OverflowMenuButtonAttributesPatch|提供更多菜单按钮属性
DebugOverflowMenuButtonPatch|加入更多菜单调试操作
HookReelOverflowMenuButton|接通 Reels 更多菜单按钮
ReelsOverflowMenuButtonEntity|适配 Reels 菜单按钮模型
DisableScreenshotDetectionPatch|关闭截图检测
DisableReelsScrollingPatch|关闭 Reels 滑动切换
HideReelsFollowButton|隐藏 Reels 关注按钮
RemoveEmptyBottomSpacePatch|移除界面底部多余空白
NativeSettingsSwitchStylePatch|使用原生风格设置开关
HandleStoryButtonPatch|接通快拍扩展按钮点击
CustomiseStoryRingSizePatch|调整快拍圆环大小
CustomiseStoryTimestampPatch|调整快拍时间显示
DisableStoryFlippingPatch|关闭快拍自动翻页
LoopStoryPatch|接入快拍循环播放
ViewStoryMentionsPatch|接入快拍提及信息查看
ComposePrismBlackPatch|处理 Compose Prism 深色背景的纯黑配色
ThemeBytecode|接通原生主题的字节码调用
ThemeComposeBytecode|接通 Compose 主题调用
ThemeLegacyRadioBytecode|接通旧式主题单选控件
ThemeOverlayTable|提供主题覆盖表
ThemePalette|提供主题调色板
ThemePatch|汇总主题替换补丁
ThemeResources|合入主题资源
UnlockPlusBenefitsPatch|接入 Plus 功能入口
FriendshipStatusIndicatorPatch|显示与用户的关注关系
ProfileMoreOptionsPatch|扩展用户主页操作菜单
UserProfileButtonPatch|向用户主页加入扩展按钮
""".strip().splitlines())

ENTITY_NAMES = dict(line.split("|", 1) for line in """
ExtMediaEntities|帖子附加媒体
Media|媒体
Tweet|帖子
TweetInfo|帖子信息
TwitterUser|用户
Video|视频
AudioMediaInterface|音频媒体
CommentData|评论
DeveloperOptions|开发者选项
DeveloperOptionsItem|开发者选项条目
DirectItem|私信条目
Entity|通用原生实体
GifMediaInterface|GIF 媒体
ImageData|图片
InstagramButton|原生按钮
InstagramButtonStyleEnum|原生按钮样式
InstagramDialogBox|原生对话框
MediaData|媒体
MediaInterface|媒体访问接口
MessageInfo|消息信息
OriginalSoundDataIntf|原始音频
ProfileInfo|用户资料
ReelResponseItem|Reels 响应条目
TrackDataIntf|音轨
UserData|用户
UserFriendshipStatus|关注关系
VideoData|视频
""".strip().splitlines())

# Folder semantics for preserved implementations; filenames further identify each role.
TOPICS = dict(line.split("|", 1) for line in """
ads|广告过滤
timelineEntryHook|时间线条目读取与过滤
featureFlag|实验功能开关
featureFlagPatch|实验功能开关接入
featureflags|实验功能开关配置
featureswitches|实验功能开关配置
changedirectory|自定义下载目录
copyMediaLink|复制媒体链接
cleartrackingparams|分享链接追踪参数清理
customDeeplinks|自定义域名链接
customsharingdomain|自定义分享域名
handlemodernsharesheetlinks|原生分享面板链接处理
legacyShareLinks|旧式分享链接
unshorten|短链接还原
responseLogging|响应日志记录
appicon|应用图标选择
appIcon|应用图标选择
blockRedirectToXLite|阻止跳转 X-Lite
blockUpdateScreen|屏蔽强制更新页面
bringbacktwitter|Twitter 品牌恢复
crashLogs|崩溃日志
exploretabs|探索页标签
font|自定义字体
inlinebar|帖子动作栏
navbar|底部导航
notificationtabs|通知页标签
postFontSize|帖子字号
profiletabs|主页标签
replySorting|回复排序
searchtabs|搜索页标签
sidebar|侧边栏
timelinetabs|时间线标签
typeAheadResponse|搜索输入联想
dynamiccolor|动态配色
fab|悬浮按钮
login|登录令牌导入导出
logintoken|登录令牌导入导出
moreProfileInfo|额外用户资料
profile|用户资料
recommendedusers|推荐用户过滤
roundOffNumbers|数字显示格式
searchsuggestions|搜索建议
selectabletext|可选择文字
settings|设置页面和配置
shareMenu|分享菜单
browseObject|对象浏览调试
debugMenu|调试菜单
externalDownloader|外部下载器
nativeDownloader|原生下载器
nativeReaderMode|原生阅读模式
readerMode|原生阅读模式
nativeShareImage|帖子分享为图片
shareImage|帖子分享为图片
nativeShareMenu|原生分享菜单
nativeTranslator|原生翻译
translator|原生翻译
providers|翻译服务适配
showchangelogs|更新说明
enableForcePip|画中画
redirectBMNavBar|书签导航替换
undoposts|撤回发帖
unlockdownloads|下载入口解锁
banner|时间线提示横幅
deleteFromDatabase|本地数据库清理
disableAutoScroll|时间线滚动位置
enableVidAutoAdvance|视频自动切换
forceHD|视频高清画质
hideCommunityBadge|社区徽章隐藏
hideHiddenReplies|隐藏回复提示隐藏
hideNavbarBadges|导航角标隐藏
hideNudgeButtons|推广操作按钮隐藏
hidePostMetrics|帖子统计隐藏
hideSocialProof|社交关系提示隐藏
live|直播内容处理
removePremiumUpsell|Premium 推广隐藏
sensitivemediasettings|敏感媒体显示
showSourceLabel|帖子来源标签
showpollresults|投票结果
tweetinfo|帖子信息
tweet|帖子数据
commentDataEntity|评论数据模型
decoder|原生数据解码
developerOptions|开发者选项
dialogbox|原生对话框接口
directItem|私信条目模型
instagramButton|原生按钮接口
mediadata|媒体数据模型
messageInfoEntity|消息信息模型
originalSoundDataIntf|原始音频数据接口
profileinfo|用户资料模型
reelResponseItem|Reels 响应模型
trackDataIntf|音轨数据接口
userdata|用户数据模型
userfriendshipstatus|关注关系模型
videoData|视频数据模型
extMedia|帖子媒体实体
tweetInfo|帖子信息模型
twitterUser|用户数据模型
story|快拍及已读状态
distractionFree|减少干扰内容
privacy|隐私与已读控制
sanitizeShareLinks|分享链接清理
shareLinks|分享链接生成
validateLinks|分享链接检查
links|链接处理
chatActionBarButton|聊天页工具栏按钮
inboxActionBarButton|私信列表工具栏按钮
mainFeedActionBarButton|首页工具栏按钮
userProfileActionBarButton|用户主页工具栏按钮
buildExpiredPopup|构建过期提示
changeLikeAnimation|点赞动画
clone|应用克隆
comment|评论操作
copyComment|评论文字复制
copyTextButton|评论文字复制按钮
debugComment|评论调试
debugButton|调试按钮
saveMediaComment|评论媒体保存
saveMediaButton|评论媒体保存按钮
downloadVoiceMessagePatch|语音消息下载
makeEphemeralPermanent|临时消息保留
markChatAsReadPatch|聊天手动标记已读
saveAllMessages|聊天消息保存
saveMessages|聊天消息保存
disableVideoAutoplay|视频自动播放控制
doubleTap|双击操作控制
focusLock|专注锁定
download|媒体下载
downloader|媒体下载
ghostMode|隐身与已读控制
hookFlags|实验开关接入
devFlags|实验开关状态
improveImageViewing|图片查看优化
instants|Instants 媒体保存
internalStuffs|内部选项
employeeOptions|员工选项
whitehatSettings|安全研究选项
moreOptionsOnPost|帖子额外菜单
navigation|导航栏配置
notification|通知处理
overflowMenuButton|更多菜单按钮
reels|Reels 短视频
buttons|媒体菜单按钮
removeEmptyBottomSpace|底部空白处理
stories|快拍操作
customiseStoryRingSize|快拍圆环大小
customiseStoryTimestamp|快拍时间显示
disableStoryFlipping|快拍自动翻页控制
loopStory|快拍循环播放
viewstorymention|快拍提及查看
theme|主题配色
unlockPlusBenefits|Plus 功能入口
userProfile|用户主页操作
userprofile|用户主页操作
actionbar|工具栏按钮
dm|私信及消息保留
feed|首页帖子操作
followList|关注列表
db|本地消息与媒体数据库
widgets|设置控件
fragments|设置子页面
preference|偏好设置界面
loggers|日志记录
entity|原生数据访问接口
utils|通用辅助逻辑
""".strip().splitlines())

LANGUAGES = dict(line.split("|", 1) for line in """
ar|阿拉伯语
bn|孟加拉语
ca|加泰罗尼亚语
cs|捷克语
da|丹麦语
de|德语
el|希腊语
es|西班牙语
fa|波斯语
fi|芬兰语
fr|法语
gu|古吉拉特语
hi|印地语
hr|克罗地亚语
hu|匈牙利语
in|印度尼西亚语
it|意大利语
iw|希伯来语
ja|日语
kn|卡纳达语
ko|韩语
mr|马拉地语
ms|马来语
nb|挪威博克马尔语
nl|荷兰语
pl|波兰语
pt|葡萄牙语
ro|罗马尼亚语
ru|俄语
sk|斯洛伐克语
sr|塞尔维亚语
sv|瑞典语
ta|泰米尔语
th|泰语
tl|菲律宾语
tr|土耳其语
uk|乌克兰语
vi|越南语
zh|中文
""".strip().splitlines())

DOC_TOPICS = [
    ("full", "完整下载信息与原生进度条"), ("pinned", "固定顶部进度和独立任务通知"),
    ("unified-banner", "统一下载通知"), ("banner", "统一顶部下载通知"),
    ("profile-bulk", "主页批量下载、分页与重试"), ("author", "按作者建立下载子文件夹"),
    ("toast", "保存成功提示去重"), ("download-success", "保存成功提示去重"),
    ("auto-refresh", "禁止自动刷新"), ("fixed", "固定底栏与自动刷新"),
    ("glass", "悬浮玻璃底栏"), ("floating-glass", "悬浮玻璃底栏"),
    ("zh", "简体中文资源"), ("video-model", "视频模型适配"),
    ("post-policy", "帖子策略适配"), ("control-flow", "字节码控制流检查"),
    ("baseline", "初始适配基线"), ("port", "12.34 版本适配"),
]


def page_for(path: str) -> str:
    if "/src/test/" in path or path.startswith(("docs/", "x-lite/")) or path == "baseline_patch_times.txt":
        return "validation"
    if "/stub/" in path:
        return "stubs"
    if path.startswith("patches/src/main/resources/addresources/"):
        return "resources-strings"
    if path.startswith("patches/src/main/resources/"):
        return "resources-assets"
    if path.startswith("patches/src/main/kotlin/app/crimera/patches/newx/"):
        return "newx-patches"
    if path.startswith("extensions/newx/"):
        return "newx-runtime"
    if path.startswith(("extensions/twitter/", "patches/src/main/kotlin/app/crimera/patches/twitter/")):
        return "legacy-twitter"
    if path.startswith(("extensions/instagram/", "patches/src/main/kotlin/app/crimera/patches/instagram/")):
        return "legacy-instagram"
    if path.startswith(("extensions/", "patches/src/main/kotlin/")):
        return "shared"
    return "project"


def topic_for(path: str) -> str:
    for part in reversed(Path(path).parts[:-1]):
        if part in TOPICS:
            return TOPICS[part]
    return "补丁基础设施"


def resource_purpose(path: str) -> str:
    p = Path(path)
    if "/addresources/" in path:
        qualifier, app = path.split("/addresources/", 1)[1].split("/")[:2]
        lang = "默认语言（主要为英语）" if qualifier == "values" else LANGUAGES.get(qualifier.split("-")[1], qualifier)
        if qualifier == "values-zh-rCN": lang = "简体中文（中国大陆）"
        if qualifier == "values-zh-rHK": lang = "繁体中文（香港）"
        if qualifier == "values-zh-rTW": lang = "繁体中文（台湾）"
        app = {"newx": "新版 X", "twitter": "旧版 Twitter / X", "instagram": "Instagram", "shared": "共享界面", "twitter-bring-back": "Twitter 品牌恢复"}.get(app, app)
        kind = {"strings.xml": "界面文字和提示", "arrays.xml": "列表选项和取值", "styles.xml": "界面样式", "attrs.xml": "自定义属性", "colors.xml": "颜色"}.get(p.name, "文字或选项资源")
        return f"{app}的{lang}{kind}"
    if "/appicons/images/" in path: return "旧版应用图标选择器使用的图标预览图片"
    if "/appicons/mipmap" in path: return "旧版可选应用图标的 Android 图标定义"
    if "/bringbacktwitter/" in path: return "恢复 Twitter 品牌使用的图标或启动画面资源"
    if "/newx/settings/" in path: return "新版 Piko 设置入口的图标资源"
    if "/settings/layout/" in path:
        labels = {"feature_flags_view": "实验开关列表", "fragment_export_token": "登录令牌导出页", "fragment_icon_selector": "应用图标选择页", "icon_item": "图标选择条目", "item_row": "设置列表条目", "search_dialog": "设置搜索对话框", "search_item_row": "设置搜索结果条目", "section_header": "设置分类标题", "webview": "网页容器"}
        return f"旧版设置界面的{labels.get(p.stem, '布局')}定义"
    if "/raw/" in path: return "界面直接读取的原始资源文件"
    if p.suffix == ".webp": return "应用界面或品牌替换使用的图片资源"
    return "Android 矢量图标或界面图形资源"


def purpose(path: str) -> str:
    if path in EXACT:
        return EXACT[path]
    p = Path(path)
    name = p.stem
    if path.startswith("docs/file-guide/"):
        return "中文文件用途分表：" + PAGES[name][0]
    if p.name == "build.gradle.kts":
        module = p.parent.as_posix()
        return f"配置 {module} 模块的依赖、编译及打包" + ("；stub 类型只供编译使用" if "/stub" in module else "")
    if p.name == "AndroidManifest.xml": return "声明本扩展或占位模块的 Android 组件、权限与应用信息"
    if "/src/main/resources/" in path: return resource_purpose(path)
    if path.startswith(".agents/"):
        if p.name == "fingerprint-development-workflow.md": return "说明如何开发、验证并维护原生方法定位指纹"
        return "供开发助手参考的 " + {"morphe-cli-reference": "Morphe 命令行用法", "morphe-faq-and-app-notes": "Morphe 常见问题与应用说明", "morphe-library-reference": "Morphe 补丁库接口", "morphe-patch-development-workflow": "Morphe 补丁开发流程", "morphe-patch-performance": "补丁性能分析", "morphe-runtime-boundaries": "补丁与应用运行时边界"}.get(p.parts[2], "方法定位开发流程") + "文档"
    if path.startswith(".github/ISSUE_TEMPLATE/"):
        app = "Instagram" if "instagram" in p.name else "Twitter / X"
        return f"{app} 的" + ("故障反馈模板" if p.name.startswith("bug") else "功能建议模板")
    if "/stub/" in path: return f"编译期 {name} 类型签名占位；运行时使用宿主或依赖提供的真实类型"
    if "/src/test/" in path:
        if "/extension/shared/" in path: return "单元测试使用的日志或设置替身，不是打包进应用的功能代码"
        subject = name.removesuffix("Test")
        if subject in NEWX_RUNTIME: return "验证“" + NEWX_RUNTIME[subject] + "”的回归测试"
        if subject in NEWX_PATCHES: return "验证“" + NEWX_PATCHES[subject] + "”的回归测试"
        subjects = {"NewXResolverLinter": "补丁定位规则检查器", "ProfileBulkHostResolver": "主页批量下载宿主定位", "FloatingGlassNavBarResolver": "玻璃底栏定位", "NavBarTabDataWrapper": "导航标签数据包装", "ProfileTabsHook": "主页标签字节码注入", "SettingsAggregateValidationSupport": "设置聚合校验", "SettingsAggregateValidation": "所有设置注册、默认值及资源", "TimelineFilterBaseline": "时间线过滤基线", "ThemeStyleResolution": "主题样式解析", "SharingLinks": "分享域名和链接清理", "XdsFactory": "原生主题工厂定位"}
        return subjects[subject] + ("的测试辅助代码" if subject.endswith("Support") else "的回归测试")
    if "/patches/newx/" in path:
        if name not in NEWX_PATCHES: raise ValueError("NewX 补丁缺少人工说明：" + path)
        return NEWX_PATCHES[name]
    if path.startswith("extensions/newx/") and p.suffix == ".java":
        if name not in NEWX_RUNTIME: raise ValueError("NewX 扩展缺少人工说明：" + path)
        return NEWX_RUNTIME[name]
    if path.startswith("docs/"):
        if path.startswith("docs/releases/"):
            first = (ROOT / path).read_text(encoding="utf-8").strip().splitlines()[0].lstrip("# ")
            return f"{name} 的发布说明：{first}"
        topic = next((cn for key, cn in DOC_TOPICS if key in p.name), "NewX 适配与回归验证")
        selection = "（全选方案）" if "-all" in p.name else "（默认方案）" if "-default" in p.name else ""
        if p.suffix == ".java":
            d = {"BulkFixStaticReview": "检查主页批量下载及中文资源", "FixedRefreshStaticReview": "检查固定底栏与自动刷新控制流", "FullBannerStaticReview": "检查完整文字、原生进度条及通知状态", "GlassLabelRelocationRepro": "单独复现玻璃底栏注入的标签重定位问题", "GlassNavStaticReview": "检查悬浮玻璃底栏原生调用", "PinnedBannerStaticReview": "检查固定顶部进度及任务队列", "UnifiedBannerStaticReview": "检查统一原生下载通知"}
            return d[name] + "的离线字节码验证程序，不注入应用"
        if p.suffix == ".md": return topic + "的实现、故障分析或验证记录"
        if "provenance" in name: return topic + "的源码、构建和文件哈希来源记录"
        if "test" in name: return topic + "的测试统计记录"
        if p.suffix == ".log": return topic + selection + ("的失败修补日志" if "failure" in name else "的修补执行日志")
        if p.suffix == ".json":
            return topic + selection + ("的失败信息记录" if "error" in name or "failure" in name else "的静态检查结果" if "review" in name or "regression" in name or "static" in name else "的修补结果或验证数据")
        return topic + "的诊断输出"
    if path.startswith("extensions/shared/") and p.suffix == ".java": return SHARED_NAMES[name]
    if p.suffix in {".kt", ".java"}:
        app = "旧版 Twitter / X" if "/twitter/" in path else "Instagram"
        topic = topic_for(path)
        if name == "Debug": return f"{app}：提供原生对象字段的调试访问"
        if "/entity/" in path and name in ENTITY_NAMES: return f"{app}：{ENTITY_NAMES[name]}数据的访问契约或注入适配"
        if name in LEGACY_DETAILS: return f"{app}：{LEGACY_DETAILS[name]}"
        if path.startswith("extensions/") and name in NEWX_RUNTIME: return f"{app}：{NEWX_RUNTIME[name]}"
        if "fingerprint" in name.lower() or "/fingerprints/" in path: return f"{app}：定位“{topic}”对应的原生方法或字段"
        if "/entity/" in path:
            return f"{app}：“{topic}”的模型接口或接口注入适配"
        if "/patches/" in path and path.startswith("patches/"):
            role = "原生调用接入" if "Hook" in name else "补丁实现"
            return f"{app}：“{topic}”的{role}"
        role = "设置页面" if name.endswith(("Fragment", "Activity")) else "列表适配器" if name.endswith("Adapter") else "配置或状态存储" if name.endswith(("Store", "State", "Pref")) else "界面控件" if name.endswith(("Button", "View", "CheckBox", "TextView", "Preference")) else "运行时处理"
        return f"{app}：“{topic}”的{role}"
    raise ValueError("文件缺少用途说明：" + path)


def link(source: str, target: str, label: str | None = None) -> str:
    relative = os.path.relpath(target, str(Path(source).parent)).replace(os.sep, "/")
    return f"[{label or Path(target).name}]({quote(relative, safe='/.-_')})"


def table_row(source: str, target: str, description: str) -> str:
    return f"| {link(source, target)} | {description.replace('|', chr(92) + '|').replace(chr(10), ' ')} |"


def main() -> None:
    tracked = subprocess.check_output(["git", "ls-files", "-z"], cwd=ROOT).decode().split("\0")
    files = sorted(set(filter(None, tracked)) | set(OUTPUTS) | {SCRIPT})
    groups: dict[str, list[str]] = {key: [] for key in PAGES}
    descriptions = {}
    for path in files:
        descriptions[path] = purpose(path)
        groups[page_for(path)].append(path)
    for key, (title, intro) in PAGES.items():
        output = f"docs/file-guide/{key}.md"
        lines = [f"# {title}", "", f"{link(output, GUIDE, '返回文件用途总索引')} · 本表 {len(groups[key])} 个文件。", "", intro, ""]
        by_directory: dict[str, list[str]] = collections.defaultdict(list)
        for path in groups[key]: by_directory[Path(path).parent.as_posix()].append(path)
        for directory, paths in sorted(by_directory.items()):
            lines.extend([f"## `{directory}`" if directory != "." else "## 仓库根目录", "", "| 文件名 | 作用 |", "| --- | --- |"])
            lines.extend(table_row(output, path, descriptions[path]) for path in paths)
            lines.append("")
        destination = ROOT / output
        destination.parent.mkdir(parents=True, exist_ok=True)
        destination.write_text("\n".join(lines), encoding="utf-8", newline="\n")

    patch = "patches/src/main/kotlin/app/crimera/patches/newx/"
    runtime = "extensions/newx/src/main/java/app/morphe/extension/newx/"
    instagram_patch = "patches/src/main/kotlin/app/crimera/patches/instagram/misc/download/"
    instagram_runtime = "extensions/instagram/src/main/java/app/morphe/extension/instagram/patches/download/"
    common = [
        ("图片、视频保存目录与作者子文件夹", runtime + "misc/NewXDownloadFolders.java", runtime + "misc/DownloadFileName.java"),
        ("普通媒体下载按钮", patch + "misc/inlineactions/InlineDownloadButtonPatch.kt", runtime + "misc/InlineDownloadButton.java"),
        ("下载进度条、完整文字与实时速度", patch + "misc/inlineactions/DownloadBannerContentPatch.kt", runtime + "misc/DownloadProgressDisplay.java"),
        ("多任务通知排队、去重与取消", runtime + "misc/DownloadBannerController.java", runtime + "misc/DownloadBannerPolicy.java"),
        ("原生顶部通知接入", patch + "misc/inlineactions/UnifiedDownloadNotificationPatch.kt", runtime + "misc/NewXInAppNotification.java"),
        ("主页全部图片和视频下载", patch + "misc/mediatab/ProfileBulkDownloadPatch.kt", runtime + "misc/ProfileBulkDownload.java"),
        ("主页分页、空页和失败重试", runtime + "misc/ProfileBulkPager.java", runtime + "misc/ProfileBulkDownload.java"),
        ("禁止自动刷新新内容", patch + "timeline/DisableTimelineRefreshPatch.kt", runtime + "timeline/TimelineRefreshGate.java"),
        ("固定底栏", patch + "misc/navbar/FixedBottomBarPatch.kt", runtime + "misc/BottomBarVisibility.java"),
        ("悬浮玻璃底栏", patch + "misc/navbar/FloatingGlassNavBarPatch.kt", runtime + "misc/FloatingGlassNavBar.java"),
        ("Piko 设置分类与排序", patch + "settings/SettingsCategories.kt", patch + "settings/NewXSettingsContributions.kt"),
        ("新版 X 简体中文文字", "patches/src/main/resources/addresources/values-zh-rCN/newx/strings.xml", "patches/src/main/resources/addresources/values/newx/strings.xml"),
        ("Instagram 主页全部图片和视频下载", instagram_patch + "ProfileDownloadPatch.kt", instagram_runtime + "ProfileDownloader.java"),
        ("Instagram 帖子网格分页、缓存与结束判断", instagram_runtime + "ProfilePageState.java", instagram_runtime + "NativeProfileBridge.java"),
        ("Instagram 已保存文件记录与手动继续", instagram_runtime + "ProfileDownloadStore.java", instagram_runtime + "ProfileDownloader.java"),
        ("Instagram 串行下载、暂停与拒绝冷却", instagram_runtime + "InstagramDownloads.java", instagram_runtime + "DownloadRatePolicy.java"),
        ("Instagram 顶部完整进度与结果排队", instagram_runtime + "TopDownloadNotice.java", instagram_runtime + "InstagramDownloads.java"),
        ("Instagram 简体中文文字", "patches/src/main/resources/addresources/values-zh-rCN/instagram/strings.xml", "patches/src/main/resources/addresources/values/instagram/strings.xml"),
        ("Morphe 补丁源和发布", "patches-bundle.json", ".github/workflows/publish-instagram-profile.yml"),
    ]
    lines = ["# 文件用途索引", "", "在下表中，文件名右侧就是中文用途；点击文件名可直接打开源码。", "", "Kotlin 补丁在修补安装包时运行；Java 扩展在修补后的应用中运行；XML 和图片提供文字、图标及布局。当前 combined 构建包含 NewX 与 Instagram，不包含旧版 Twitter / X 实现。Instagram 分表沿用 legacy-instagram.md 文件名，文件名不表示该模块已停用。", "", "## 按功能查找", "", "| 要修改的功能 | 主要文件 | 配套文件 |", "| --- | --- | --- |"]
    for label, primary, secondary in common:
        if primary not in descriptions or secondary not in descriptions: raise ValueError("功能入口链接不存在")
        lines.append(f"| {label} | {link(GUIDE, primary)} | {link(GUIDE, secondary)} |")
    lines.extend(["", "## 全部文件", "", f"共 **{len(files):,} 个文件**，按目录逐个标注，包含本索引和维护脚本。范围是 Git 管理的仓库文件，不含本地安装包、编译产物及缓存。", "", "| 分组 | 文件数 | 内容 |", "| --- | ---: | --- |"])
    for key, (title, intro) in PAGES.items():
        lines.append(f"| {link(GUIDE, f'docs/file-guide/{key}.md', title)} | {len(groups[key])} | {intro} |")
    lines.extend(["", "## 常用根目录文件", "", "| 文件名 | 作用 |", "| --- | --- |"])
    for path in ["README.md", "patches-bundle.json", "patches-list.json", "gradle.properties", "settings.gradle.kts", "build.gradle.kts", "patch-twitter.sh", "patch-instagram.sh", "dns-probe.sh", "AGENTS.md", "LICENSE", "NOTICE"]:
        lines.append(table_row(GUIDE, path, descriptions[path]))
    lines.extend(["", "## 维护索引", "", f"用途说明和目录规则维护在 {link(GUIDE, SCRIPT)}。在仓库根目录运行：", "", "```sh", f"python3 {SCRIPT}", "```", "", "新增源码文件先加入 Git，再更新对应说明并运行脚本。新版 X 的补丁和运行时文件缺少专门说明时，脚本会报错，避免把新功能只标成笼统的“源代码”。", "", f"功能是否已迁移到当前 X 版本，请看 {link(GUIDE, 'docs/newx-12.34-coverage.md', '旧版功能迁移对照表')}；文件存在本身不代表该版本可用。", ""])
    (ROOT / GUIDE).write_text("\n".join(lines), encoding="utf-8", newline="\n")
    assert sum(map(len, groups.values())) == len(files)
    for path in files:
        if not (ROOT / path).is_file(): raise ValueError("索引目标不存在：" + path)
    print(f"Indexed {len(files)} files across {len(PAGES)} sections.")


if __name__ == "__main__":
    main()
