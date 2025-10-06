package convention

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        return target.run {
            val jvmTarget = project.findProperty("JVM_TARGET") as String? ?: "11"

            pluginManager.apply("java-library")
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
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