plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.sample.kinopoisk.core.navigation"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
}