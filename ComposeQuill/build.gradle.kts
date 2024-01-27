plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.22"
    id("maven-publish")
    id("signing")
    // id ("com.vanniktech.maven.publish") version "0.27.0"
}
apply(plugin = "maven-publish")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17


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
            version = "1.0.0-rc3"
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
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/the-best-is-best/ComposeQuill")
                    credentials {
                        username = "the-best-is-best"
                        password =
                            System.getenv("BUILD_MAVEN")
                    }
                }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
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
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.material3:material3")
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc01")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.01.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}