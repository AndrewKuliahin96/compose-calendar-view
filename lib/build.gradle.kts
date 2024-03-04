plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    `maven-publish`
    signing
}

android {
    defaultConfig {
        namespace = "com.kuliahin.compose.calendarview"
        compileSdk = 34
        minSdk = 26 // TODO: Downgrade to 23
    }

    buildTypes {
        all {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.common.android)

    // Compose
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    // Core and UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.kuliahin"
            artifactId = "calendar-view"
            version = System.getenv("RELEASE_VERSION")

            afterEvaluate {
                from(components["release"])
            }

            pom {
                pom.name = "Compose Calendar View"
                pom.packaging = "aar"
                pom.description = "Compose Calendar View (Android)"
                pom.url = "https://github.com/AndrewKuliahin96/compose-calendar-view"

                scm {
                    url.set("https://github.com/AndrewKuliahin96/compose-calendar-view")
                    connection = "scm:git@github.com:AndrewKuliahin96/compose-calendar-view.git"
                    developerConnection = "scm:git@github.com:AndrewKuliahin96/compose-calendar-view.git"
                }

                licenses {
                    license {
                        name = "MIT license"
                        url = "https://github.com/AndrewKuliahin96/compose-calendar-view/blob/main/LICENSE"
                    }
                }

                developers {
                    developer {
                        id = "andrew_kuliahin"
                        name = "Andrew Kuliahin"
                        email = "kulagin.andrew38@gmail.com"
                    }
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")

            val isSnapshot = version.toString().endsWith("SNAPSHOT")

            setUrl(provider {
                if (isSnapshot) snapshotsRepoUrl else releasesRepoUrl
            })

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("SIGNING_KEY_ID"),
        System.getenv("SIGNING_KEY"),
        System.getenv("SIGNING_PASSWORD")
    )
    sign(publishing.publications)
}
