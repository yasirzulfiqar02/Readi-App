plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.readi.apps"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.readi.apps"
        minSdk = 24
        targetSdk = 36
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
    implementation(libs.androidx.animation.core)
    implementation(libs.filament.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.mediation.test.suite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.lottie)//lottie files

    implementation(libs.androidx.media3.exoplayer)// for media3 exoplayer
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

    implementation(libs.androidx.media3.exoplayer.v141)
    implementation(libs.androidx.media3.exoplayer.dash.v141)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.media3.ui.v141)

    implementation(libs.shawnlin013.number.picker) //number picker
    implementation(libs.roundedimageview)
    implementation(libs.play.services.auth.api.phone)
    implementation(libs.wheelpicker)//wheel picker
    implementation(libs.dotsindicator)//dots indicator
    implementation(libs.library)

    implementation(libs.androidx.navigation.fragment.ktx)//bottom navigation
    implementation(libs.androidx.navigation.ui.ktx)

    implementation("com.github.chrisbanes:PhotoView:2.3.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.android.gms:play-services-ads:23.3.0")

    implementation ("com.google.firebase:firebase-auth:23.2.0")
    implementation ("com.google.android.gms:play-services-auth:21.3.0")

    implementation(libs.retrofit) //retrofit
    implementation (libs.converter.gson) //jason convertor

    implementation(platform(libs.firebase.bom))// FCM
    implementation(libs.firebase.messaging)
}