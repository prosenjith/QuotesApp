plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("org.jetbrains.kotlin.kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.gson.converter)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.okhttp)

    // Unit testing dependencies
    testImplementation(libs.junitJupiterApi)
    testRuntimeOnly(libs.junitJupiterEngine)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoKotlin)

    // Optional: AssertJ for fluent assertions
    testImplementation(libs.assertj)
}

kapt {
    correctErrorTypes = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}