import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    id("com.vanniktech.maven.publish") version "0.31.0"
    `maven-publish`
    signing
}

description = "Android Compose Calendar View"
group = "com.kuliahin"
version = System.getenv("RELEASE_VERSION") ?: ""

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

mavenPublishing {
    coordinates("com.kuliahin", "compose-calendarview", System.getenv("RELEASE_VERSION") ?: "")

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    pom {
        name.set("Android Compose Calendar View")
        description.set("Android Compose Calendar View")
        url.set("https://github.com/AndrewKuliahin96/compose-calendar-view/")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/AndrewKuliahin96/compose-calendar-view/blob/main/LICENSE")
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
