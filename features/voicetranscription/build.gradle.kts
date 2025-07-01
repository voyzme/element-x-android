import extension.setupAnvil

/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 * Please see LICENSE in the repository root for full details.
 */
plugins {
    id("io.element.android-library")
}

android {
    namespace = "io.element.android.features.voicetranscription"
}

setupAnvil()

dependencies {
    implementation(projects.libraries.di)
    implementation(libs.dagger)
    implementation(libs.androidx.corektx)
    implementation(projects.libraries.core)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.androidutils)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.uiStrings)
    implementation(projects.services.toolbox.api)

    // Vosk speech-to-text native binding
    implementation("com.alphacephei:vosk-android:0.3.47@aar") // Adjust version if needed

    // Media loader (MatrixMediaLoader) - likely part of matrix.api or a submodule
    implementation(projects.libraries.matrix.api) // keep for media loader

    // Possibly needed audio processing libs, e.g., ffmpeg or other if applicable
    // implementation(libs.audio.processing) // Uncomment & add if needed

    testImplementation(libs.test.junit)
    testImplementation(libs.test.truth)
    testImplementation(libs.test.robolectric)
    testImplementation(projects.libraries.matrix.test)
    testImplementation(projects.tests.testutils)
}

