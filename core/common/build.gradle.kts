plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.sample.kinopoisk.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
}