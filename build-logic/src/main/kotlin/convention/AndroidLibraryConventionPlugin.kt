package convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        return target.run {
            val compileSdk = (project.findProperty("COMPILE_SDK") as String? ?: "36").toInt()
            val minSdk = (project.findProperty("MIN_SDK") as String? ?: "23").toInt()
            val jvmTarget = project.findProperty("JVM_TARGET") as String? ?: "11"

            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                this.compileSdk = compileSdk

                this.defaultConfig.apply {
                    this.minSdk = minSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                this.compileOptions.apply {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    this.jvmTarget.set(JvmTarget.fromTarget(jvmTarget))
                    freeCompilerArgs.addAll(
                        listOf(
                            "-opt-in=kotlin.ExperimentalStdlibApi",
                            "-opt-in=kotlin.ExperimentalUnsignedTypes"
                        )
                    )
                }
            }
        }
    }

}