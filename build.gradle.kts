plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
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