package com.zfeng.minedemo.plugin.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("aar", AARExtension::class.java)
        project.plugins.apply("maven-publish")
        project.tasks.register("publishReleaseToMineMaven") {
            dependsOn("publishReleasePublicationToMavenRepository")
            description = "发布aar到私人maven"
            group = "zfeng"
        }

        project.tasks.register("publishDebugToMineMaven") {
            dependsOn("publishDebugPublicationToMavenRepository")
            description = "发布aar到私人maven"
            group = "zfeng"
        }

        project.afterEvaluate {
            val extension = project.extensions.getByType(PublishingExtension::class.java)
            extension.repositories {
                maven {
                    isAllowInsecureProtocol = true
                    setUrl("http://xxx")
                    credentials {
                        username = ""
                        password = ""
                    }
                }
            }

            extension.publications {
                val aarExtension = project.extensions.getByType(AARExtension::class.java)
                create("release",MavenPublication::class.java) {
                    from(components.getAt("release"))
                    groupId = "com.zfeng.aar"
                    artifactId = aarExtension.name.get()
                    version = aarExtension.version.get()
                }

                create("debug",MavenPublication::class.java) {
                    from(components.getAt("debug"))
                    groupId = "com.zfeng.aar"
                    artifactId = aarExtension.name.get()
                    version = aarExtension.version.get()
                }
            }
        }

    }
}