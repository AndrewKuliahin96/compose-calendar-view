import com.android.build.api.dsl.LibraryPublishing

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-2"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    val kotlinLint by configurations.creating

    dependencies {
        kotlinLint("com.pinterest.ktlint:ktlint-cli:1.2.1") {
            attributes {
                attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
            }
        }
    }

    tasks.register<JavaExec>("ktlint") {
        description = "Check Kotlin code style."
        mainClass.set("com.pinterest.ktlint.Main")
        classpath = kotlinLint
        args("src/**/*.kt")
        jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
    }

    tasks.register<JavaExec>("ktlintFormat") {
        group = "formatting"
        description = "Fix Kotlin code style deviations."
        mainClass.set("com.pinterest.ktlint.Main")
        classpath = kotlinLint
        args("-F", "src/**/*.kt")
        jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
    }
}

subprojects {
    if (plugins.hasPlugin("com.android.library")) {
        configure<LibraryPublishing> {
            singleVariant("release") {
                withSourcesJar()
            }
        }
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
ext["signing.key"] = System.getenv("SIGNING_KEY")

nexusPublishing.repositories {
    sonatype {
        nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
        snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

        username.set(System.getenv("OSSRH_USERNAME"))
        password.set(System.getenv("OSSRH_PASSWORD"))
        version = System.getenv("RELEASE_VERSION")
    }
}
