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
         maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")

    }

    versionCatalogs {
        create("libsx") {
            from(files("./gradle/libs.versions.toml"))
        }
    }


}

rootProject.name = "example"
include(":app")
//include(":tTextRichEditor")
include(":ComposeQuill")
