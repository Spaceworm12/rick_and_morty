plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.rickandmorty"
        minSdk = 30
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
                ("proguard-rules.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = ("17")
    }
    buildFeatures {
        compose = true
    }
    buildFeatures {
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ("1.4.3")
    }

}

dependencies {
    //ci-ce-ro-ne
    implementation("com.github.terrakok:cicerone:7.1")
    //swipe
    implementation("me.saket.swipe:swipe:1.0.0")
    implementation("com.google.android.engage:engage-core:1.2.0")
    //log intercept
    val loggingInterceptVer = "4.7.2"
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptVer")
    implementation("com.squareup.okhttp3:okhttp:4.7.2")
    //room db
    kapt("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-common:2.5.2")
    implementation("androidx.room:room-runtime")
    kapt("androidx.room:room-compiler")
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-rxjava2:2.5.1")

    //rx
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")

    // Retrofit - сетевые запросы
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    //coil - загрузка изображений
    implementation("io.coil-kt:coil:1.4.0")
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.compose:compose-bom:2022.10.00")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3:1.1.1")

    androidTestImplementation("androidx.compose:compose-bom:2022.10.00")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    api("androidx.compose.runtime:runtime-livedata:1.4.0")
    api("androidx.compose.ui:ui-tooling:1.4.0")
    api("androidx.compose.ui:ui-tooling-preview:1.4.0")
    api("androidx.compose.ui:ui:1.4.0")
    api("androidx.compose.material:material:1.4.0")
    api("androidx.compose.runtime:runtime-livedata:1.4.0")

    implementation("androidx.compose.material:material-icons-extended:1.4.0")
}