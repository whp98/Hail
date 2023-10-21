import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}
val version = Properties().apply { load(file("../version.properties").reader()) }
val signFolder = "../androidSign/androidSign/"
val props =
    Properties().apply { load(file(signFolder + "signing.properties").reader()) }
val keyStoreFile = file(signFolder + props.getProperty("storeFile"))
android {
    namespace = "com.aistra.hail"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aistra.hail"
        minSdk = 23
        targetSdk = 33
        versionCode = version.getProperty("versionCode").toInt()
        versionName = version.getProperty("versionName")
    }
    val signing = if (file(signFolder + "signing.properties").exists()) {
        signingConfigs.create("release") {
            val props =
                Properties().apply { load(file(signFolder + "signing.properties").reader()) }
            storeFile = file(signFolder + props.getProperty("storeFile"))
            storePassword = props.getProperty("storePassword")
            keyAlias = props.getProperty("keyAlias")
            keyPassword = props.getProperty("keyPassword")
        }
    } else signingConfigs.getByName("debug")
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signing
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    applicationVariants.all {
        outputs.all {
            (this as? com.android.build.gradle.internal.api.ApkVariantOutputImpl)
                ?.outputFileName = "Hail.apk"
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("com.belerweb:pinyin4j:2.5.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("dev.rikka.rikkax.preference:simplemenu-preference:1.0.3")
    implementation("dev.rikka.shizuku:api:13.1.4")
    implementation("dev.rikka.shizuku:provider:13.1.4")
    implementation("io.github.iamr0s:Dhizuku-API:2.4")
    implementation("me.zhanghai.android.appiconloader:appiconloader:1.5.0")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")
}