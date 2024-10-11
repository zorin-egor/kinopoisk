plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.room)
}

android {
    namespace = "com.sample.kinopoisk.core.database"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.square.retrofit2.gson.converter)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
