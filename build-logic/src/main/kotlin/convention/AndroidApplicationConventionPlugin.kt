package convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        return target.run {
            val compileSdk = (project.findProperty("COMPILE_SDK") as String).toInt()
            val minSdk = (project.findProperty("MIN_SDK") as String).toInt()
            val targetSdk = (project.findProperty("TARGET_SDK") as String).toInt()
            val jvmTarget = project.findProperty("JVM_TARGET") as String

            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<BaseExtension> {
                this.compileSdkVersion(apiLevel = compileSdk)
                this.defaultConfig.apply {
                    this.minSdk = minSdk
                    this.targetSdk = targetSdk

                    this.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                this.compileOptions.apply {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
                this.buildFeatures.apply {
                    compose = true
                    buildConfig = true
                }
            }

            tasks.withType(KotlinCompile::class.java).configureEach {
                compilerOptions {
                    this.jvmTarget.set(JvmTarget.fromTarget(jvmTarget))
                    freeCompilerArgs.addAll(
                        listOf(
                            "-Xannotation-default-target=param-property"
                        )
                    )
                }
            }
        }
    }

}