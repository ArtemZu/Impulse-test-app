import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.artemzu.auth"
    compileSdk = 33

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val githubClientId: String = gradleLocalProperties(rootDir).getProperty("GITHUB_CLIENT_ID")
        buildConfigField("String", "GITHUB_CLIENT_ID", githubClientId)

        val githubClientSecret: String =
            gradleLocalProperties(rootDir).getProperty("GITHUB_CLIENT_SECRET")
        buildConfigField("String", "GITHUB_CLIENT_SECRET", githubClientSecret)
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
    implementation(project(mapOf("path" to ":core:designsystem")))
    implementation(project(mapOf("path" to ":core:domain")))

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