@file:Suppress("UnstableApiUsage")

import org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS

include(":ComposeQuill")


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
//        maven{
//            url = uri("https://maven.pkg.github.com/the-best-is-best/AndroidTextRichEditor")
//            credentials {
//                username = "the-best-is-best"
//                password =
//                    "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"
//            }
//        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "example"
include(":app")
//include(":tTextRichEditor")
include(":ComposeQuill")
