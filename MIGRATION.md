# 独立仓库迁移记录

迁移日期：2026-10-20。

- 上游：https://github.com/crimera/piko/tree/x-lite
- 定制来源：https://github.com/YongkeDet/piko
- 迁移源码提交：7e97531ace2d1d8079aa4fc17a95cdb17f03eb89（清理后文件树与 .19 维护源码相同）。
- 原 .19 MPP 构建提交：5464a22338ce0be0e1a414610d25f59757b28d0b。
- 原 .19 MPP SHA-256：14562d0968886fe782eb0bbe29041f36442f7069d35f07878016e59d01397b92。
- 新仓库：https://github.com/YongkeDet/piko-custom。

本次按源码快照导入，保留新仓库初始提交；早期 Git 提交历史可从来源仓库查阅。原文件版权与许可声明、LICENSE、Piko NOTICE 及历史验证文档保留，补充 Morphe NOTICE。

迁移变更：版本升至 .20；补丁元信息和 X 发布说明入口改为新仓库；更新 README；构建与发布独立执行，移除旧仓库固定构建编号、Crowdin 与上游专用发布流程。X/IG 下载算法、中文读取和图标位置沿用 .19。

## 构建与发布

使用 Java 17、Android SDK 36。Maven 依赖仍从 Morphe 和 crimera 的 GitHub Packages 读取，需要相应读取权限的 GITHUB_TOKEN，依赖仓库地址不能机械改成本仓库。

主分支提交会执行合并测试、Android 构建、API 29 检查、解析器与扩展描述符检查、Instagram 中文资源回归，成功后发布版本对应的 MPP、校验值、来源记录和许可证。PR 只构建测试。可从 Actions 手动重新执行。

发布校验来源提交、SHA-256、标签和补丁源 URL。已有版本与当前构建不一致时拒绝覆盖，须同时提升 gradle.properties 和 patches-bundle.json 的版本后重新构建。对应源码由发布标签提供，构建输入随源码保留。

重新修补安装时沿用之前的签名。
