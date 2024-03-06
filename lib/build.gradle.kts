plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.3"
    `maven-publish`
    signing
}

description = "Android Compose Calendar View"
group = "com.kuliahin"
version = System.getenv("RELEASE_VERSION")

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

centralPortal {
    username = System.getenv("CENTRAL_USERNAME")
    password = System.getenv("CENTRAL_TOKEN")

    pom {
        name = "Android Compose Calendar View"
        description = "Android Compose Calendar View library"
        url = "https://github.com/AndrewKuliahin96/compose-calendar-view"

        licenses {
            license {
                name = "MIT license"
                url = "https://github.com/AndrewKuliahin96/compose-calendar-view/blob/main/LICENSE"
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
            developerConnection = "scm:git@github.com:AndrewKuliahin96/compose-calendar-view.git"
            url = "https://github.com/AndrewKuliahin96/compose-calendar-view"
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