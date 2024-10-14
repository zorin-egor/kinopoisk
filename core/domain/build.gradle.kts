plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.sample.kinopoisk.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    testImplementation(libs.kotlinx.coroutines.test)
}