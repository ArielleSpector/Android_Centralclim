import org.gradle.api.JavaVersion
import org.gradle.api.JavaVersion.VERSION_11

plugins {alias(libs.plugins.android.application)
}

// Add this import statement at the top


        android {
            namespace = "com.example.android_centralclim"
            compileSdk = 36

            defaultConfig {
                applicationId = "com.example.android_centralclim"
                minSdk = 24
                targetSdk = 36
                versionCode = 1
                versionName = "1.0"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
            compileOptions {
                // Correctly reference JavaVersion
                sourceCompatibility = VERSION_11
                targetCompatibility = VERSION_11
            }
        }

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}