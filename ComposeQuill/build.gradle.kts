plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.22"
    id("maven-publish")
    id("signing")

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.register<Zip>("aar") {
    from("src/main") {
        include("assets/**")
        include("res/**")
        include("AndroidManifest.xml")
    }
    from("build/intermediates/bundles/release") {
        include("*.jar")
    }
    from("build/intermediates/library_assets/release") {
        include("*.aar")
    }
    archiveFileName.set("Compose Quill.aar")
}
afterEvaluate {
    publishing {
        // Configure all publications
        publications.create<MavenPublication>("release") {
            groupId = "com.tbib"
            artifactId = "composequill"
            version = "1.0.0-1-pre"
            artifact(tasks["aar"])

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
                name = "LocalMaven"
                url = uri("$buildDir/maven")
            }
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/the-best-is-best/ComposeQuill")
                credentials {
                    username = "the-best-is-best"
                    password =
                        System.getenv("GITHUB_MAVEN")
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
    namespace = "com.tbib.ttextricheditor"
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc01")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")


    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.coil-kt:coil-compose:2.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}