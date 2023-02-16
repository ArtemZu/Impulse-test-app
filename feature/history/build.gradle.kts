plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.artemzu.githubapp.history"
    compileSdk = 33

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(mapOf("path" to ":core:common")))
    implementation(project(mapOf("path" to ":core:domain")))
    implementation(project(mapOf("path" to ":core:ui")))

    implementation(platform(Libraries.composeBom))
    implementation(Libraries.composeUi)
    implementation(Libraries.composeUiGraphics)
    implementation(Libraries.composeUiToolingPreview)
    implementation(Libraries.composeMaterial3)

    implementation(Libraries.lifecycleCompose)

    kapt(Libraries.hiltKapt)
    implementation(Libraries.hilt)

    implementation(Libraries.hiltNavigationCompose)
    implementation(Libraries.navigationCompose)

    testImplementation(Libraries.jUnit)

    androidTestImplementation(Libraries.androidTestJUnit)

    androidTestImplementation(Libraries.androidTestEspresso)
    androidTestImplementation(platform(Libraries.composeBom))
    androidTestImplementation(Libraries.androidTestComposeUiJUnit)
    debugImplementation(Libraries.androidTestComposeUiTooling)
    debugImplementation(Libraries.androidTestComposeUiManifest)
}