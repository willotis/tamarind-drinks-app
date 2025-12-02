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
        google()
        mavenCentral()
        gradlePluginPortal()
        // Ensure all Maven Central artifacts are accessible
        maven { url = uri("https://repo1.maven.org/maven2/") }
    }
}

rootProject.name = "TamarindDrinks"
include(":app")
