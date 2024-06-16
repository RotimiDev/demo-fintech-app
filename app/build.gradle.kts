plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleServices)
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.akeemrotimi.vpdmoneyassessment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.akeemrotimi.vpdmoneyassessment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    // Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Unit Test Libraries
    testImplementation(libs.junit)

    // Mockito for mocking
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)

    // AndroidX Test - Rules library
    testImplementation(libs.androidx.rules)

    // AndroidX Lifecycle for LiveData and ViewModel test
    testImplementation(libs.androidx.lifecycle.livedata.core)
    testImplementation(libs.androidx.lifecycle.viewmodel)

    // Android/UI Test Libraries
    androidTestImplementation(libs.androidx.espresso.core)

    // Coroutines test library
    testImplementation(libs.kotlinx.coroutines.test)

    // Room testing
    testImplementation(libs.androidx.room.testing)

    // Compose BOM
    implementation(libs.androidx.activity.compose)
    implementation( platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.material3)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.pager.pager)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)
    androidTestImplementation( platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)

    // Compose Coil
    implementation(libs.coil.compose)

    // Navigation component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    // Firebase Authentication
    implementation(libs.firebase.auth)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Room components
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.runtime.livedata)

    // Kotlin coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
}
