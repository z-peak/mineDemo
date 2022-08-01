package com.zfeng.minedemo.plugin

import com.zfeng.minedemo.plugin.tools.BuildMode
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension
import java.util.*
import com.zfeng.minedemo.Versions
import com.zfeng.minedemo.Config
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        applyPlugin(target)
        val androidExtensions = target.extensions.getByName("android")

        if (androidExtensions is AppExtension) {
            configAndroidExtension(target, androidExtensions)
        }
    }

    private fun applyPlugin(project: Project) {
        val buildMode = buildMode(project)
        println("buildMode:${buildMode}")
        when (buildMode) {
            BuildMode.RELEASE -> {
                applyReleasePlugin(project)
            }
            BuildMode.DEVELOP -> {
                applyDevelopPlugin(project)
            }
            else -> {
                applyReleasePlugin(project)
            }
        }
    }

    private fun applyReleasePlugin(project: Project) {
        println("===============Release模式打包,所有插件已开启==================")
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-android-extensions")
//        project.plugins.apply("com.huawei.agconnect")

    }

    private fun applyDevelopPlugin(project: Project) {
        println("===============develop模式打包==================")
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-android-extensions")
//        project.plugins.apply("com.huawei.agconnect")
        project.afterEvaluate {
            tasks.findByName("preReleaseBuild")?.doFirst {
                throw GradleException("开发模式下禁止打Release包，请修改local.properties")
            }
        }
    }

    private fun configAndroidExtension(project: Project, extension: AppExtension) {
        configVersions(project, extension)
        configBuildTypes(extension)
    }

    private fun configVersions(project: Project, extension: AppExtension) {
        extension.compileSdkVersion(Versions.COMPILE_SDK)
        extension.buildToolsVersion(Versions.BUILD_TOOLS)
        extension.defaultConfig {
            applicationId = Config.MainPacket.APPLICATION_ID
            targetSdkVersion(Versions.TARGET_SDK)
            minSdkVersion(Versions.MIN_SDK)
            versionCode = Versions.VERSION_CODE
            versionName = Versions.VERSION_NAME

            ndk {
                abiFilters("armeabi-v7a", "arm64-v8a")
            }

            //关闭png合法性检查
            extension.aaptOptions.cruncherEnabled = false
            extension.aaptOptions.useNewCruncher = false
            resConfig("en")
        }

        extension.splits {
            abi {
                isEnable = true
                reset()
                include("armeabi-v7a", "arm64-v8a")
                isUniversalApk = true
            }
        }

        extension.compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        project.tasks.withType(KotlinCompile::class.java).configureEach {
            kotlinOptions { jvmTarget = "1.8" }
        }

        extension.signingConfigs.maybeCreate("release")
        extension.signingConfigs {
            getByName("release") {
                storeFile = project.file("demo.jks")
                storePassword = Config.MainPacket.KEY_STORE_PWD
                keyAlias = Config.MainPacket.KEY_ALIAS
                keyPassword = Config.MainPacket.KEY_PWD
            }
        }

        extension.sourceSets {
            maybeCreate("main")
            getByName("main") {
                jniLibs.srcDir("libs")
                java.srcDirs(
                    "src/main/java",
                    "src/main/module/oauth/src/main/java",
                    "src/main/module/share/src/main/java"
                )
                res.srcDirs(
                    "src/main/res",
                    "src/main/res/live",
                    "src/main/res/live/im",
                    "src/main/res/ugc",
                    "src/main/res/talent",
                    "src/main/res/sync",
                    "src/main/res/jb",
                    "src/main/res/order",
                    "src/main/module/oauth/src/main/res",
                    "src/main/module/share/src/main/res"
                )
            }
        }

        extension.lintOptions {
            //在 release 版本是否检查 fatal 类型错误，默认release版本为开启。开启后，检查到 fatal 类型错误则会关闭。
            isCheckReleaseBuilds = false
            //是否发现错误，则停止构建。
            isAbortOnError = false
            //是否忽略警告，只检查error。
            isIgnoreWarnings = false
        }
        extension.dexOptions {
            javaMaxHeapSize = "4g"
            maxProcessCount = 8
            preDexLibraries = true
        }
    }

    private fun configBuildTypes(extension: AppExtension) {
        val defaultProguardFile = extension.getDefaultProguardFile("proguard-android.txt")
        extension.buildTypes.maybeCreate("release")
        extension.buildTypes.getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = extension.signingConfigs.getByName("release")
            proguardFiles(defaultProguardFile, "proguard-rules.pro")
            resValue("bool", "config_center_is_debug_mode", "false")
        }

        extension.buildTypes.maybeCreate("debug")
        extension.buildTypes.getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = extension.signingConfigs.getByName("release")
            proguardFiles(defaultProguardFile, "proguard-rules.pro")
            resValue("bool", "config_center_is_debug_mode", "true")
        }
    }


    private fun buildMode(project: Project): String {
        return kotlin.runCatching {
            val properties = Properties()
            val file = project.rootProject.file("local.properties")
            properties.load(file.inputStream())
            properties.getProperty("com.zfeng.build", BuildMode.RELEASE)
        }.getOrDefault(BuildMode.RELEASE)
    }
}