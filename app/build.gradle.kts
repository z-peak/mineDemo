import com.zfeng.minedemo.Deps

plugins {
    id("com.android.application")
    id("com.zfeng.application")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.KOTLIN)
    implementation(Deps.CORE_KTX)
    implementation(Deps.APPCOMPAT)
    implementation(Deps.CONSTRAINT_LAYOUT)
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation(Deps.TEST_JUNIT)
    androidTestImplementation(Deps.JUNIT)
    androidTestImplementation(Deps.ESPRESSO_CODE)

}