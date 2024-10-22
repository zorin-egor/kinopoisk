plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.sample.kinopoisk.core.domain"
}

dependencies {
    api(projects.core.data)
    testImplementation(libs.kotlinx.coroutines.test)
}