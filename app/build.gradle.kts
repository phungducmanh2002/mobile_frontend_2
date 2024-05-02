plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.appktx2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appktx2"
        minSdk = 24
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //add by me
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor( "org.projectlombok:lombok:1.18.32")
    implementation( "com.github.bumptech.glide:glide:5.0.0-rc01")
    //
    implementation("com.github.dhaval2404:imagepicker:2.1")
}