plugins {
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.sample.android.application.compose)
    alias(libs.plugins.sample.android.application.flavors)
}

val appCode = 1
val appVersion = "0.0.1"

android {
    namespace = "com.sample.kinopoisk"

    defaultConfig {
        applicationId = "com.sample.kinopoisk"
        versionCode = appCode
        versionName = appVersion

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "APP_NAME", "\"Kinopoisk\"")
        buildConfigField("String", "VERSION_NAME", "\"$appVersion\"")
        buildConfigField("Integer", "VERSION_CODE", "$appCode")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.named("debug").get()
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)

    implementation(projects.features.films)
    implementation(projects.features.filmDetails)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.androidx.core.splashscreen)
}