plugins {
    alias(libs.plugins.sample.android.library)
    id("kotlinx-serialization")
}

android {
    namespace = "com.sample.kinopoisk.core.network"
    buildFeatures {
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(libs.bundles.network)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.coroutines.test)
}