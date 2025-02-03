import org.gradle.internal.impldep.com.google.api.client.auth.oauth2.BearerToken
import java.util.Base64

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
        minSdk = 26
    }

    buildTypes {
        all {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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

    // Compose
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.compose)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.kuliahin"
            artifactId = "compose.calendar-view"
            version = System.getenv("RELEASE_VERSION") ?: ""
            version = "1.2.0"
            description = "Android Compose Calendar View"

            pom {
                name = "Android Compose Calendar View"
                description = "Android Compose Calendar View library"
                url = "https://github.com/AndrewKuliahin96/compose-calendar-view"

                licenses {
                    license {
                        name = "MIT license"
                        url =
                            "https://github.com/AndrewKuliahin96/compose-calendar-view/blob/main/LICENSE"
                    }
                }

                developers {
                    developer {
                        name = "Andrew Kuliahin"
                        email = "kulagin.andrew38@gmail.com"
                        url = "https://github.com/AndrewKuliahin96"
                    }
                }

                scm {
                    connection = "scm:git@github.com:AndrewKuliahin96/compose-calendar-view.git"
                    developerConnection =
                        "scm:git@github.com:AndrewKuliahin96/compose-calendar-view.git"
                    url = "https://github.com/AndrewKuliahin96/compose-calendar-view"
                }
            }
        }
    }

    repositories {
        mavenCentral {
            url = uri("https://repo1.maven.org/maven2/")

            val userToken = Base64.getEncoder().encode(
                "${System.getenv("CENTRAL_USERNAME")}:${System.getenv("CENTRAL_TOKEN")}".toByteArray(),
            ).toString(Charsets.UTF_8)

            credentials(HttpHeaderCredentials::class) {
                name = "Authorization"
                value = "Bearer $userToken"
            }

            authentication {
                create<HttpHeaderAuthentication>("header")
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
}