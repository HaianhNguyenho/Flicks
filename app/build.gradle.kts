 plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.recyclervideo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.recyclervideo"
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

    // thay thế findViewById, để dễ dàng truy cập các phần tử view dễ dàng hơn
    buildFeatures {
        viewBinding = true
    }



}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //retrofit: quản lý các yêu cầu HTTP, giúp tương tác các API web thông qua get
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //Client: thư viện mạnh mẽ để làm trực tiếp vs HTTP
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    //gson: chuyển đổi từ đối tượng sang Json và chuyển đổi JSON sang đối tượng
    implementation ("com.google.code.gson:gson:2.9.0")
    //Image Loading: tải hiện thị ảnh
    implementation ("io.coil-kt:coil:1.4.0")


}