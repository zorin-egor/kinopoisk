plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.library.compose)
}

android {
    namespace = "com.sample.kinopoisk.core.ui"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.windowSizeClass)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.core.ktx)
    api(libs.coil.kt)
    api(libs.coil.kt.compose)
}
