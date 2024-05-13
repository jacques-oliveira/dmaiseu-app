plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

buildscript{
    repositories {
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}
android {
    namespace = "com.example.dmaiseu"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.BDgames.DmaisEu"
        minSdk = 24
        targetSdk = 33
        versionCode = 20
        versionName = "1.3.7"

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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    ///rounded imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //imagepicker
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("androidx.activity:activity-ktx:1.2.3")
    implementation ("androidx.fragment:fragment-ktx:1.3.3")

}