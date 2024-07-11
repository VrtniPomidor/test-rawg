plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.secrets)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.room)
}

android {
    namespace = "com.example.testrawg"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testrawg"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.named("debug").get()
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

composeCompiler {
    enableStrongSkippingMode = true
    includeSourceInformation = true
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)

    // Libs
    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Navigation and serialization
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.bundles.coil)

    // Paging
    implementation(libs.bundles.androidx.paging)

    // Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.dataStore)

    // Animation
    implementation(libs.androidx.animation)
    implementation(libs.androidx.foundation)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
