plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.tamarind.drinks"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tamarind.drinks"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Build config fields for app configuration
        buildConfigField("String", "API_BASE_URL", "\"https://api.tamarinddrinks.com/\"")
        buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", "\"pk_test_placeholder\"")
        buildConfigField("String", "PAYPAL_CLIENT_ID", "\"paypal_sandbox_placeholder\"")
        buildConfigField("String", "MPESA_CONSUMER_KEY", "\"mpesa_placeholder\"")
    }

    signingConfigs {
        create("release") {
            // Update these with actual keystore details for release builds
            storeFile = file("keystore/release.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "tamarind123"
            keyAlias = System.getenv("KEY_ALIAS") ?: "tamarind"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "tamarind123"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            versionNameSuffix = "-debug"
            buildConfigField("String", "API_BASE_URL", "\"http://localhost:8080/\"")
            buildConfigField("Boolean", "USE_MOCK_SERVER", "true")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            buildConfigField("Boolean", "USE_MOCK_SERVER", "false")
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("sandbox") {
            dimension = "environment"
            buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", "\"pk_test_51placeholder\"")
            buildConfigField("String", "PAYPAL_CLIENT_ID", "\"sandbox_client_id\"")
            buildConfigField("String", "MPESA_CONSUMER_KEY", "\"sandbox_consumer_key\"")
            buildConfigField("String", "MPESA_CONSUMER_SECRET", "\"sandbox_consumer_secret\"")
            buildConfigField("String", "MPESA_PASSKEY", "\"sandbox_passkey\"")
            buildConfigField("String", "MPESA_SHORTCODE", "\"174379\"")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", "\"pk_live_placeholder\"")
            buildConfigField("String", "PAYPAL_CLIENT_ID", "\"production_client_id\"")
            buildConfigField("String", "MPESA_CONSUMER_KEY", "\"production_consumer_key\"")
            buildConfigField("String", "MPESA_CONSUMER_SECRET", "\"production_consumer_secret\"")
            buildConfigField("String", "MPESA_PASSKEY", "\"production_passkey\"")
            buildConfigField("String", "MPESA_SHORTCODE", "\"production_shortcode\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Retrofit & Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // Moshi JSON
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // Room Database
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Coil Image Loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Stripe Payment
    implementation("com.stripe:stripe-android:20.34.0")

    // Google Pay
    implementation("com.google.android.gms:play-services-wallet:19.2.1")

    // PayPal
    implementation("com.paypal.checkout:android-sdk:1.3.2")

    // WorkManager for background tasks
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // DataStore for preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Security Crypto for encrypted preferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Paging 3 for pagination
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.2.1")

    // Accompanist for system UI controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
