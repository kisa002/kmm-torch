import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64 {
        setUpiOSObserver()
    }
    iosArm64 {
        setUpiOSObserver()
    }
    iosSimulatorArm64 {
        setUpiOSObserver()
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)

                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.haeyum.kmm_torch"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
}

fun KotlinNativeTarget.setUpiOSObserver() {
    val path = projectDir.resolve("src/nativeInterop/cinterop/Observer")

    binaries.all {
        linkerOpts("-F $path")
        linkerOpts("-ObjC")
    }

    compilations.getByName("main") {
        cinterops.create("Observer") {
            compilerOpts("-F $path")
        }
    }
}