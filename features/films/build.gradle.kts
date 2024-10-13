plugins {
    alias(libs.plugins.sample.feature)
    alias(libs.plugins.sample.android.library.compose)
}

android {
    namespace = "com.sample.kinopoisk.feature.films"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.navigation)

    implementation(libs.bundles.coil)
    implementation(libs.androidx.fragment.compose)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.accompanist.systemuicontroller)

    testImplementation(libs.androidx.test.core)
}
