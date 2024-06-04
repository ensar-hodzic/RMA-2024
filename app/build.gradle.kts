plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.herbar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.herbar"
        minSdk = 30
        targetSdk = 34
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
    implementation("androidx.camera:camera-camera2:1.1.0-alpha12")
    implementation("androidx.camera:camera-lifecycle:1.1.0-alpha12")
    implementation("androidx.camera:camera-view:1.0.0-alpha30")
    implementation(libs.androidx.room.common)
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    debugImplementation("androidx.tracing:tracing:+")
    testImplementation("junit:junit:+")
    androidTestImplementation("androidx.test.ext:junit:+")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test:core-ktx:+")
    androidTestImplementation("androidx.test.ext:junit-ktx:+")
    testImplementation("org.hamcrest:hamcrest:x.y")
    testImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:+")
    implementation("com.github.bumptech.glide:glide:+")
    annotationProcessor("com.github.bumptech.glide:compiler:+")
    implementation("com.squareup.retrofit2:retrofit:+")
    implementation("com.squareup.retrofit2:converter-gson:+")
    testImplementation ("org.assertj:assertj-core:3.22.0")
    implementation ("org.assertj:assertj-core:3.22.0")
}