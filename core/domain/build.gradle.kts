plugins {
    id("convention.kotlin.library")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.serialization.json)

    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotest.assertions)
}