plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.kotlin.serialization)
    `java-gradle-plugin`
}

repositories { mavenCentral() }

dependencies {
    compileOnly(libs.bundles.scoverage)
    implementation(libs.bundles.kotlin.serialization.bundle)
    implementation(libs.turtle)
    implementation(libs.commonsio)
}

gradlePlugin {
    plugins {
        create("gradleScoverage") {
            id = "org.scoverage"
            implementationClass = "org.scoverage.ScoveragePlugin"
            displayName = "Gradle Scoverage plugin"
        }
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}
