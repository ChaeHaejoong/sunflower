android {
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.google.samples.apps.sunflower"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    testInstrumentationRunner = "com.google.samples.apps.sunflower.utilities.MainTestRunner"
    versionCode = 1
    versionName = "0.1.6"
    vectorDrawables.useSupportLibrary = true

    buildConfigField("String", "UNSPLASH_ACCESS_KEY", "\"" + getUnsplashAccess() + "\"")
    javaCompileOptions {
      annotationProcessorOptions {
        arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
      }
    }
  }

  // ← 여기 signingConfigs 추가
  signingConfigs {
    create("release") {
      storeFile = file("upload-key.keystore")
      storePassword = System.getenv("KEYSTORE_PASSWORD")
      keyAlias = System.getenv("KEY_ALIAS")
      keyPassword = System.getenv("KEY_PASSWORD")
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      
      // ← 서명 설정 연결
      signingConfig = signingConfigs.getByName("release")
    }
    create("benchmark") {
      initWith(getByName("release"))
      signingConfig = signingConfigs.getByName("debug")
      isDebuggable = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules-benchmark.pro"
      )
    }
  }
  // ... (나머지 기존 코드 그대로 유지)
}
