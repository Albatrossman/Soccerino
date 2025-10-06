pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://dl.google.com/dl/android/maven2/") }
        google()
        mavenCentral()
    }
}

rootProject.name = "Soccerino"
include(":app")
include(":core:di")
include(":core:common")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":feature:ranking")
include(":feature:following")
include(":feature:player")

includeBuild("build-logic")
