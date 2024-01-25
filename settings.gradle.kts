@file:Suppress("UnstableApiUsage")

import org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS

include(":ComposeQuill")


pluginManagement {
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
     //   mavenLocal()
        // maven(url = "https://jitpack.io")
    }


}

rootProject.name = "example"
include(":app")
//include(":tTextRichEditor")
include(":ComposeQuill")
