# Future Framework 发布指南

本文档说明如何将 Future Framework 发布到不同的 Maven 仓库。

## 支持的仓库

目前支持以下仓库：
1. **Nexus** - 私有仓库（默认启用，无需额外配置）
2. **GitHub Packages** - GitHub 官方 Maven 仓库（需显式启用）
3. **Gitee Packages** - Gitee 官方 Maven 仓库（需显式启用）

## 快速开始

### 查看可用的发布任务

```bash
./gradlew tasks --group publishing
```

### 发布命令

#### 发布到 Nexus（默认，只发布到 Nexus）

```bash
./gradlew publishToNexus
```

这个命令**只**会发布到 Nexus，不会尝试连接 GitHub 或 Gitee。

#### 发布到 GitHub Packages

**前提条件**：
1. 在 `gradle.properties` 中配置 GitHub 凭证（见下方配置说明）
2. 使用 `-PenableGitHub` 标志启用 GitHub 发布

```bash
./gradlew publishToGitHub -PenableGitHub
```

#### 发布到 Gitee Packages

**前提条件**：
1. 在 `gradle.properties` 中配置 Gitee 凭证（见下方配置说明）
2. 使用 `-PenableGitee` 标志启用 Gitee 发布

```bash
./gradlew publishToGitee -PenableGitee
```

## 配置说明

### 1. Nexus 配置（默认已配置）

编辑 `gradle.properties`：

```properties
nexusUsername=admin
nexusPassword=suven123
mavenUrl=http://192.168.3.100:9091/nexus
```

或使用环境变量：
```bash
export NEXUS_USERNAME=admin
export NEXUS_PASSWORD=suven123
export MAVEN_URL=http://192.168.3.100:9091/nexus
```

### 2. GitHub Packages 配置

#### 步骤 1：创建 Personal Access Token

1. 访问 https://github.com/settings/tokens
2. 点击 "Generate new token (classic)"
3. 选择以下权限：
   - `write:packages` - 上传包到 GitHub Packages
   - `read:packages` - 从 GitHub Packages 下载包
4. 生成并复制 Token

#### 步骤 2：配置凭证

编辑 `gradle.properties`：

```properties
githubOwner=suvenw
githubRepo=future-framework
githubUsername=your_github_username
githubToken=ghp_xxxxxxxxxxxxxxxxxxxx
```

或使用环境变量：
```bash
export GITHUB_OWNER=suvenw
export GITHUB_REPO=future-framework
export GITHUB_USERNAME=your_github_username
export GITHUB_TOKEN=ghp_xxxxxxxxxxxxxxxxxxxx
```

#### 步骤 3：执行发布

```bash
./gradlew publishToGitHub -PenableGitHub
```

### 3. Gitee Packages 配置

**⚠️ 重要提示：Gitee 的 Maven Packages 功能可能需要企业版或专业版订阅才能使用。免费版用户可能会遇到 404 错误。**

#### 步骤 1：创建 Personal Access Token

1. 访问 https://gitee.com/profile/personal_access_tokens
2. 点击 "生成新令牌"
3. 选择 `projects` 权限
4. 生成并复制 Token

#### 步骤 2：配置凭证

编辑 `gradle.properties`：

```properties
giteeOwner=suvenw
giteeRepo=future-framework
giteeUsername=your_gitee_username
giteeToken=xxxxxxxxxxxxxxxxxxxx
```

或使用环境变量：
```bash
export GITEE_OWNER=suvenw
export GITEE_REPO=future-framework
export GITEE_USERNAME=your_gitee_username
export GITEE_TOKEN=xxxxxxxxxxxxxxxxxxxx
```

#### 步骤 3：执行发布

```bash
./gradlew publishToGitee -PenableGitee
```

#### 常见问题

**Q: 发布时返回 404 错误？**
A: 这可能是因为：
1. 您的 Gitee 账户没有开通 Packages 功能（需要企业版/专业版）
2. 仓库路径不正确
3. Token 权限不足

**解决方案：**
- 升级 Gitee 账户到企业版或专业版
- 或者使用 GitHub Packages 作为替代方案
- 或者只使用 Nexus 私有仓库

## 在其他项目中使用

### 从 Nexus 使用

```groovy
repositories {
    maven {
        url "http://your-nexus-server/repository/maven-public/"
        credentials {
            username = "your-username"
            password = "your-password"
        }
    }
}

dependencies {
    implementation 'com.suven.framework:future-http:0.1.56-releases'
}
```

### 从 GitHub Packages 使用

```groovy
repositories {
    maven {
        url "https://maven.pkg.github.com/suvenw/future-framework"
        credentials {
            username = "your-github-username"
            password = "your-github-token"
        }
    }
}

dependencies {
    implementation 'com.suven.framework:future-http:0.1.56-releases'
}
```

### 从 Gitee Packages 使用

```groovy
repositories {
    maven {
        url "https://gitee.com/api/v5/repos/suvenw/future-framework/packages/maven"
        credentials {
            username = "your-gitee-username"
            password = "your-gitee-token"
        }
    }
}

dependencies {
    implementation 'com.suven.framework:future-http:0.1.56-releases'
}
```

## 注意事项

1. **默认行为**：`./gradlew publishToNexus` 只会发布到 Nexus，不会尝试连接 GitHub 或 Gitee
2. **显式启用**：发布到 GitHub/Gitee 必须使用 `-PenableGitHub` 或 `-PenableGitee` 标志
3. **版本号**：当前版本号在 `build.gradle` 中定义，确保每次发布使用正确的版本号
4. **Token 安全**：不要将 Token 提交到 Git 仓库，建议使用环境变量
5. **重复发布**：同一版本不能重复发布到 Maven 仓库，如需重新发布请更新版本号

## 常见问题

### Q: 执行 `publishToNexus` 时报 401 Unauthorized
A: 检查 Nexus 用户名和密码是否正确配置在 `gradle.properties` 或环境变量中

### Q: 执行 `publishToGitHub` 时报 401 Unauthorized
A: 检查：
1. 是否使用了 `-PenableGitHub` 标志
2. GitHub Token 是否正确且未过期
3. Token 是否具有 `write:packages` 权限

### Q: 如何只发布单个模块？
A: 
```bash
./gradlew :future-http:publishAllPublicationsToNexusRepository
```

### Q: 如何查看当前配置会发布到哪些仓库？
A: 
```bash
./gradlew :future-utils:tasks --group publishing
```
查看列出的 `publishAllPublicationsTo*Repository` 任务

## 联系支持

如有问题，请联系项目维护者。
