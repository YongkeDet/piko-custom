# 项目配置与维护

[返回文件用途总索引](../../FILE_GUIDE.md) · 本表 53 个文件。

构建配置、发布流程、问题模板和仓库维护文件。

## 仓库根目录

| 文件名 | 作用 |
| --- | --- |
| [.envrc](../../.envrc) | 进入目录时载入 Nix 开发环境 |
| [.gitattributes](../../.gitattributes) | 规定 Git 的换行和文件属性 |
| [.gitignore](../../.gitignore) | 排除构建产物、缓存及本地临时文件 |
| [.releaserc](../../.releaserc) | 配置自动版本发布和更新日志生成 |
| [AGENTS.md](../../AGENTS.md) | 规定本仓库的修改、字节码注入、验证与设备操作规则 |
| [CHANGELOG.md](../../CHANGELOG.md) | 记录历史版本变更 |
| [FILE_GUIDE.md](../../FILE_GUIDE.md) | 中文文件用途总入口及常用功能到源码的对照表 |
| [LICENSE](../../LICENSE) | 项目的 GPLv3 授权条款 |
| [NOTICE](../../NOTICE) | 说明上游来源、版权和授权声明 |
| [README.md](../../README.md) | 项目首页：导入、功能、使用方法和当前验证范围 |
| [build.gradle.kts](../../build.gradle.kts) | 定义根项目插件和各扩展模块的构建规则 |
| [crowdin.yml](../../crowdin.yml) | 配置 Crowdin 翻译资源同步 |
| [dns-probe.sh](../../dns-probe.sh) | 在 Termux 等环境检查 X 媒体域名的 DNS 和 HTTPS 连通性 |
| [flake.lock](../../flake.lock) | 锁定 Nix 开发环境的依赖版本 |
| [flake.nix](../../flake.nix) | 定义 Nix 开发环境及工具依赖 |
| [gradle.properties](../../gradle.properties) | 保存补丁包版本和 Gradle 构建参数 |
| [gradlew](../../gradlew) | 在 Linux / macOS 上启动固定版本的 Gradle |
| [gradlew.bat](../../gradlew.bat) | 在 Windows 上启动固定版本的 Gradle |
| [package-lock.json](../../package-lock.json) | 锁定 Node.js 发布工具的依赖版本 |
| [package.json](../../package.json) | 声明自动发布和更新日志所需的 Node.js 开发依赖 |
| [patch-instagram.sh](../../patch-instagram.sh) | 构建或校验当前 combined 补丁包来源，修补显式指定的原始 Instagram APK / APKM，并核对修补与重建结果 |
| [patch-twitter.sh](../../patch-twitter.sh) | 验证当前补丁包来源后修补显式指定的原始 APK / APKM，并检查失败与重建结果 |
| [patches-bundle.json](../../patches-bundle.json) | Morphe 读取的稳定补丁源：版本、下载地址、发布时间和说明 |
| [patches-list.json](../../patches-list.json) | 供工具使用的补丁清单数据；当前可用项以发布包和 README 为准 |
| [settings.gradle.kts](../../settings.gradle.kts) | 声明模块、插件及依赖仓库 |

## `.agents/skills/morphe-cli-reference`

| 文件名 | 作用 |
| --- | --- |
| [SKILL.md](../../.agents/skills/morphe-cli-reference/SKILL.md) | 供开发助手参考的 Morphe 命令行用法文档 |

## `.agents/skills/morphe-faq-and-app-notes`

| 文件名 | 作用 |
| --- | --- |
| [SKILL.md](../../.agents/skills/morphe-faq-and-app-notes/SKILL.md) | 供开发助手参考的 Morphe 常见问题与应用说明文档 |

## `.agents/skills/morphe-library-reference`

| 文件名 | 作用 |
| --- | --- |
| [SKILL.md](../../.agents/skills/morphe-library-reference/SKILL.md) | 供开发助手参考的 Morphe 补丁库接口文档 |

## `.agents/skills/morphe-patch-development-workflow`

| 文件名 | 作用 |
| --- | --- |
| [SKILL.md](../../.agents/skills/morphe-patch-development-workflow/SKILL.md) | 供开发助手参考的 Morphe 补丁开发流程文档 |

## `.agents/skills/morphe-patch-development-workflow/references`

| 文件名 | 作用 |
| --- | --- |
| [fingerprint-development-workflow.md](../../.agents/skills/morphe-patch-development-workflow/references/fingerprint-development-workflow.md) | 说明如何开发、验证并维护原生方法定位指纹 |

## `.agents/skills/morphe-patch-performance`

| 文件名 | 作用 |
| --- | --- |
| [SKILL.md](../../.agents/skills/morphe-patch-performance/SKILL.md) | 供开发助手参考的 补丁性能分析文档 |

## `.agents/skills/morphe-runtime-boundaries`

| 文件名 | 作用 |
| --- | --- |
| [SKILL.md](../../.agents/skills/morphe-runtime-boundaries/SKILL.md) | 供开发助手参考的 补丁与应用运行时边界文档 |

## `.github`

| 文件名 | 作用 |
| --- | --- |
| [dependabot.yml](../../.github/dependabot.yml) | 配置依赖更新检查 |

## `.github/ISSUE_TEMPLATE`

| 文件名 | 作用 |
| --- | --- |
| [bug_instagram.yml](../../.github/ISSUE_TEMPLATE/bug_instagram.yml) | Instagram 的故障反馈模板 |
| [bug_twitter.yml](../../.github/ISSUE_TEMPLATE/bug_twitter.yml) | Twitter / X 的故障反馈模板 |
| [config.yml](../../.github/ISSUE_TEMPLATE/config.yml) | 配置 GitHub 问题入口 |
| [feature_instagram.yml](../../.github/ISSUE_TEMPLATE/feature_instagram.yml) | Instagram 的功能建议模板 |
| [feature_twitter.yml](../../.github/ISSUE_TEMPLATE/feature_twitter.yml) | Twitter / X 的功能建议模板 |

## `.github/scripts`

| 文件名 | 作用 |
| --- | --- |
| [generate_patches_readme.py](../../.github/scripts/generate_patches_readme.py) | 从补丁元数据生成补丁功能表 |

## `.github/workflows`

| 文件名 | 作用 |
| --- | --- |
| [build_pull_request.yml](../../.github/workflows/build_pull_request.yml) | 检查拉取请求的构建 |
| [crowdin_pull.yml](../../.github/workflows/crowdin_pull.yml) | 从 Crowdin 拉取翻译更新 |
| [crowdin_push.yml](../../.github/workflows/crowdin_push.yml) | 向 Crowdin 推送待翻译资源 |
| [instagram-profile.yml](../../.github/workflows/instagram-profile.yml) | 构建并测试 Instagram 与 NewX 的 combined 补丁包，检查定位规则和接口描述符，并记录 MPP 来源 |
| [newx-12.34.yml](../../.github/workflows/newx-12.34.yml) | 构建并测试 NewX 补丁包，检查定位规则、接口描述符并记录来源 |
| [open_pull_request.yml](../../.github/workflows/open_pull_request.yml) | 自动化拉取请求相关维护流程 |
| [publish-custom-download-folder.yml](../../.github/workflows/publish-custom-download-folder.yml) | 核对已验证构建的哈希及来源后发布自定义 NewX 补丁包 |
| [publish-instagram-profile.yml](../../.github/workflows/publish-instagram-profile.yml) | 核对已验证combined构建的哈希和源码来源，发布包含Instagram主页下载的补丁包 |
| [release.yml](../../.github/workflows/release.yml) | 运行上游版本发布流程 |

## `gradle`

| 文件名 | 作用 |
| --- | --- |
| [libs.versions.toml](../../gradle/libs.versions.toml) | 集中声明依赖库、插件及版本 |

## `gradle/wrapper`

| 文件名 | 作用 |
| --- | --- |
| [gradle-wrapper.jar](../../gradle/wrapper/gradle-wrapper.jar) | Gradle Wrapper 的启动程序 |
| [gradle-wrapper.properties](../../gradle/wrapper/gradle-wrapper.properties) | 指定 Wrapper 使用的 Gradle 发行版 |

## `patches`

| 文件名 | 作用 |
| --- | --- |
| [build.gradle.kts](../../patches/build.gradle.kts) | 配置 patches 模块的依赖、编译及打包 |

## `scripts`

| 文件名 | 作用 |
| --- | --- |
| [update-file-guide.py](../../scripts/update-file-guide.py) | 生成并检查本中文文件用途索引，新增文件时可重新运行 |
