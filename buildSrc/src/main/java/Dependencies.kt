object Libraries {
    private object Versions {
        const val androidCoreKts = "1.8.0"
        const val lifecycleCompose = "2.6.0-alpha03"
        const val composeBom = "2022.10.00"
        const val activityCompose = "1.5.1"

        const val hilt = "2.44"

        const val accompanist = "0.29.1-alpha"

        const val navigationCompose = "2.5.3"
        const val hiltNavigationCompose = "1.0.0"

        const val dataStore = "1.0.0"

        const val retrofit = "2.9.0"
        const val loggingInterceptor = "4.10.0"
        const val serializationConverter = "0.8.0"

        const val kotlinSerialization = "1.4.1"

        const val browser = "1.5.0"

        const val koil = "2.2.2"

        const val room = "2.5.0"

        const val jUnit = "4.13.2"
        const val androidTestJUnit = "1.1.5"
        const val androidTestEspresso = "3.5.1"
    }

    // Android
    const val androidCoreKts = "androidx.core:core-ktx:${Versions.androidCoreKts}"

    // Activity
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    // Hilt
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // Lifecycle
    const val lifecycleCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleCompose}"

    // Accompanist
    const val accompanistSystemUiController =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"

    // Compose
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"

    // Navigation
    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    // DataStore
    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    // Room
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // Retorfit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    const val serializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.serializationConverter}"
    const val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"

    // Image Loader
    const val koil = "io.coil-kt:coil-compose:${Versions.koil}"

    // Browser
    const val browser = "androidx.browser:browser:${Versions.browser}"

    // Test
    const val jUnit = "junit:junit:${Versions.jUnit}"

    // Android test
    const val androidTestJUnit = "androidx.test.ext:junit:${Versions.androidTestJUnit}"
    const val androidTestEspresso =
        "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
    const val androidTestComposeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val androidTestComposeUiJUnit = "androidx.compose.ui:ui-test-junit4"
    const val androidTestComposeUiTooling = "androidx.compose.ui:ui-tooling"
    const val androidTestComposeUiManifest = "androidx.compose.ui:ui-test-manifest"

}