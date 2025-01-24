plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    kotlin("kapt")
}

android {
    namespace = "com.aplikasi.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aplikasi.myapplication"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("io.appwrite:sdk-for-android:6.1.0")


        implementation ("com.squareup.retrofit2:retrofit:2.11.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

        implementation ("androidx.viewpager2:viewpager2:1.0.0")

    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.3")


}