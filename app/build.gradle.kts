import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.mandiri.goldmarket"
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode(1)
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "BASE_URL", "${project.properties.get("api")}")
    }

    buildFeatures{
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
//            buildConfigField("String", "BASE_URL", baseUrl)
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    kapt("androidx.room:room-compiler:2.3.0")
    AppDependencies.dependencies.getValue("implementation").forEach { d -> implementation(d) }
    AppDependencies.dependencies.getValue("testImplementation").forEach { t -> testImplementation(t) }
    AppDependencies.dependencies.getValue("androidTestImplementation").forEach { ti -> androidTestImplementation(ti) }
}