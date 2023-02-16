plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.artemzu.githubapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.artemzu.githubapp"
        minSdk = 29
        targetSdk = 33
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(mapOf("path" to ":core:designsystem")))
    implementation(project(mapOf("path" to ":core:domain")))
    implementation(project(mapOf("path" to ":feature:auth")))
    implementation(project(mapOf("path" to ":feature:main")))

    implementation(Libraries.androidCoreKts)

    implementation(Libraries.activityCompose)

    kapt(Libraries.hiltKapt)
    implementation(Libraries.hilt)

    implementation(platform(Libraries.composeBom))
    implementation(Libraries.composeUi)
    implementation(Libraries.composeUiGraphics)
    implementation(Libraries.composeUiToolingPreview)
    implementation(Libraries.composeMaterial3)

    implementation(Libraries.lifecycleCompose)

    implementation(Libraries.accompanistSystemUiController)

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