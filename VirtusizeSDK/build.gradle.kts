import java.io.FileInputStream
import java.util.*

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish") //apply plugin
}

//library details
val mGroupId = "com.virtusize.sdk"
val mArtifactId = "virtusize-sdk"
val mVersionCode = 1
val mVersionName = "1.0.3"
val mLibraryName = "VirtusizeSDK"
val mLibraryDescription = "Virtusize SDK test library"

android {
    namespace = "com.virtusize.sdk"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

//task for creation of android sources
tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

//local credentials
val githubProperties = Properties()
githubProperties.load(FileInputStream(rootProject.file("github.properties")))

//configuration for publishing artifact
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = mGroupId
                artifactId = mArtifactId
                version = mVersionName
                from(components["release"])
                artifact(tasks["androidSourcesJar"])
                pom {
                    name.set(mLibraryName)
                    description.set(mLibraryDescription)
                }
            }
        }

        //repository details and credentials
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/dsantiago0223/virtusize-sdk-test")
                credentials {
                    username = githubProperties.get("gpr.usr") as String? ?: System.getenv("GPR_USER")
                    password = githubProperties.get("gpr.key") as String? ?: System.getenv("GPR_KEY")
                }
            }
        }
    }
}

/*
./gradlew :VirtusizeSDK:assemble
./gradlew :VirtusizeSDK:publish
 */
tasks.named("publish").configure {
    dependsOn(tasks.named("assemble"))
}