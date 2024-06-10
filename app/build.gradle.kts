plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id("com.vk.vkompose") version "0.5.4-k2"
    id("com.vk.recompose-highlighter") version "0.5.4-k2"
    id("com.vk.recompose-logger") version "0.5.4-k2"
    id("com.vk.compose-test-tag-applier") version "0.5.4-k2"
    id("com.vk.compose-test-tag-cleaner") version "0.5.4-k2"
    id("com.vk.compose-test-tag-drawer") version "0.5.4-k2"
    id("com.vk.compose-source-information-cleaner") version "0.5.4-k2"
    id("com.vk.composable-skippability-checker") version "0.5.4-k2"
}

vkompose {
    skippabilityCheck = true

    recompose {
        isHighlighterEnabled = true
        isLoggerEnabled = true
    }

    testTag {
        isApplierEnabled = true
        isDrawerEnabled = true
        isCleanerEnabled = true
    }

    sourceInformationClean = true
}

android {
    namespace = "ru.skittens.goroute"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.skittens.goroute"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.coil.compose)
    implementation(libs.gson)
    implementation(libs.androidx.material.icons.extended)
    implementation("com.vk.vkompose:detekt:0.5.4-k2")
}