plugins {
    id("convention.kotlin.library")
}

dependencies {
    api(libs.javax.inject)

    implementation(libs.jetbrains.kotlinx.coroutines.core)
}
