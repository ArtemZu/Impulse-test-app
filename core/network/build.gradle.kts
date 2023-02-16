plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.8.10"
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.artemzu.githubapp.network"
    compileSdk = 33

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 29

        buildConfigField("String", "AUTH_BASE_URL", "\"https://github.com/\"")
        buildConfigField("String", "MAIN_BASE_URL", "\"https://api.github.com/\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    kapt(Libraries.hiltKapt)
    implementation(Libraries.hilt)

    implementation(Libraries.retrofit)
    implementation(Libraries.kotlinxSerialization)
    implementation(Libraries.loggingInterceptor)
    implementation(Libraries.serializationConverter)

    testImplementation(Libraries.jUnit)
}