plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("MainApplication") {
            id = "com.zfeng.application"
            implementationClass = "com.zfeng.minedemo.plugin.ApplicationPlugin"
        }
    }

    plugins {
        create("PublishPlugin") {
            id = "com.zfeng.publish"
            implementationClass = "com.zfeng.minedemo.plugin.publish.PublishPlugin"
        }
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}


dependencies {

    implementation(gradleApi())
    implementation(localGroovy())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    implementation("com.android.tools.build:gradle:4.0.0")
//    implementation("com.squareup.okhttp3:okhttp:3.12.1")

}