plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "2.0.0"
    id("maven-publish")
    id("signing")
    alias(libs.plugins.compose.compiler)

}
apply(plugin = "maven-publish")
apply(plugin = "signing")
apply(plugin = "io.objectbox")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17


}
//
buildscript {

    dependencies {
        classpath("io.objectbox:objectbox-gradle-plugin:4.0.1")

    }
}

afterEvaluate {
    tasks.withType<PublishToMavenLocal> {
        // Make 'publishReleasePublicationToMavenLocal' depend on 'assembleRelease'
        dependsOn("assembleRelease")
    }
    publishing {

        publications.create<MavenPublication>("release") {
            groupId = "io.github.the-best-is-best"
            artifactId = "composequill"
            version = "1.0.2"
            from(components["release"])



            //  artifact("$buildDir/outputs/aar/ComposeQuill-release.aar")
            //artifact("$buildDir/libs/ComposeQuill-release.jar")
            // Provide artifacts information required by Maven Central
            pom {
                name.set("Compose Quill")
                description.set("A Compose library that provides a rich text editor support image or video.")
                url.set("https://github.com/the-best-is-best/ComposeQuill")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/the-best-is-best/ComposeQuill/issues")
                }
                scm {
                    connection.set("https://github.com/the-best-is-best/ComposeQuill.git")
                    url.set("https://github.com/the-best-is-best/ComposeQuill")
                }
                developers {
                    developer {
                        id.set("MichelleRaouf")
                        name.set("Michelle Raouf")
                        email.set("eng.michelle.raouf@gmail.com")
                    }
                }
            }
        }
        repositories {

            maven {
                name = "OSSRH-snapshots"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = System.getenv("MAVEN_NAME")
                    password = System.getenv("MAVEN_TOKEN")
                }
//            }
//            maven {
//                name = "LocalMaven"
//                url = uri("$buildDir/maven")
                //   }
//                maven {
//                    name = "GitHubPackages"
//                    url = uri("https://maven.pkg.github.com/the-best-is-best/ComposeQuill")
//                    credentials {
//                        username = "the-best-is-best"
//                        password =
//                            System.getenv("BUILD_MAVEN")
//                    }
//                }
            }


        }

    }

}


signing {
    useGpgCmd()
    sign(publishing.publications)
}
android {
    namespace = "com.tbib.composequill"

    compileSdk = 34
    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("io.github.the-best-is-best:media-picker:1.0.3")
    implementation("io.github.the-best-is-best:compose-request-permission:1.0.0")
    implementation("androidx.documentfile:documentfile:1.0.1")

    implementation("io.github.the-best-is-best:ComposeSearchableDropdown:2.0.1")
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc05-k2")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.github.skydoves:colorpicker-compose:1.0.7")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.8")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.objectbox:objectbox-kotlin:4.0.1")
}