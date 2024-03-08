plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.appturismo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appturismo"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //Camara
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")

    //AnimatedBottomBar
    implementation("nl.joery.animatedbottombar:library:1.1.0")

    //Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    //MapBox
    implementation("com.mapbox.maps:android:10.16.1")
    //MapBox AR
    //implementation("com.mapbox.mapboxsdk:mapbox-android-navigation:0.42.6")

    //Custom Navigation bar
    //SmoothBottomBar
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    //Custom Toggle group
    implementation("nl.bryanderidder:themed-toggle-button-group:1.4.1")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

    //Google Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //Google Places
    implementation("com.google.android.libraries.places:places:3.2.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}