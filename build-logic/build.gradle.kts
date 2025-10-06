import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        register("ir.miare.application") {
            id = "convention.android.application"
            implementationClass = "convention.AndroidApplicationConventionPlugin"
        }

        register("ir.miare.library") {
            id = "convention.android.library"
            implementationClass = "convention.AndroidLibraryConventionPlugin"
        }

        register("ir.miare.compose") {
            id = "convention.android.library.compose"
            implementationClass = "convention.AndroidComposeLibraryConventionPlugin"
        }

        register("ir.miare.kotlin") {
            id = "convention.kotlin.library"
            implementationClass = "convention.KotlinLibraryConventionPlugin"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.13.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
}