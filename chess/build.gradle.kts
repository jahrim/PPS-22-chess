import com.lordcodes.turtle.shellRun
import javafx.JavaFX
import wartremover.WartRemover
import java.util.regex.Pattern

plugins {
    scala
    application
    alias(libs.plugins.spotless)
    alias(libs.plugins.kotlin.qa)
    `maven-publish`
    signing
    id("org.scoverage")
}

repositories { mavenCentral() }

dependencies {
    implementation(libs.scala)
    implementation(libs.bundles.scalafmt)
    implementation(libs.vertx)
    implementation(libs.scalafx)
    libs.bundles.javafx.get().forEach {
        val fxArtifact = "${it.module}:${it.version}"
        implementation("$fxArtifact:${JavaFX.getSpecificClassifier()}")
        JavaFX.getClassifiers().forEach { runtimeOnly("$fxArtifact:$it") } // Multiplatform Jar
    }
    implementation(libs.tuprolog)
    testImplementation(libs.scalatest)
    testImplementation(libs.scalatestplusjunit)
    scalaCompilerPlugins(libs.wartremover)
    scoverage(libs.scala)
}

project.version = shellRun {
    git.gitCommand(listOf("describe", "--tags"))
}

application {
    mainClass.set("io.github.chess.main")
}

// Code Style (aesthetic...)
spotless {
    isEnforceCheck = false
    scala {
        scalafmt(libs.versions.scalafmt.version.get()).configFile(".scalafmt.conf")
        licenseHeaderFile(file("../LICENSE-HEADER"), "package ")
    }
    // always apply formatting before building, running or testing the project
    tasks.compileScala.get().dependsOn(tasks.spotlessApply)
}

// Code Linting (error prevention...)
val wartRemoverCompileOptions = WartRemover.configFile(file(".wartremover.conf")).toCompilerOptions()

// Scala Compiler Options
tasks.withType(ScalaCompile::class.java) {
    scalaCompileOptions.additionalParameters =
        listOf(
            "-Xtarget:8",
            "-indent",
            "-rewrite",
            "-feature",
            "-language:implicitConversions"
        ) + wartRemoverCompileOptions
}

// Publication
val scaladocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.scaladoc)
    archiveClassifier.set("javadoc")
    from("${project.buildDir}/docs/scaladoc")
}

val sourceJar by tasks.registering(Jar::class) {
    dependsOn(tasks.classes)
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks {

    withType<PublishToMavenRepository>().configureEach {
        val baseVersion = project.version.toString()
        val projectVersion =
            if (baseVersion.contains("-"))
                baseVersion.substringBefore("-") + "-SNAPSHOT"
            else
                baseVersion
        onlyIf { Pattern.matches("(([0-9])+(\\.?([0-9]))*)+(-SNAPSHOT)?", projectVersion) }
    }

    distZip {
        enabled = false
    }

    distTar {
        enabled = false
    }

    startScripts {
        enabled = false
    }

    val fatJar by registering(Jar::class) {
        group = "build"
        description = "Assembles a runnable fat jar archive containing all the needed stuff to be executed as standalone."
        archiveClassifier.set("")
        archiveVersion.set("")
        from(sourceSets.main.get().output)
        dependsOn(configurations.compileClasspath)
        from(configurations.runtimeClasspath.get().map { zipTree(it) })
        manifest {
            attributes["Main-Class"] = "io.github.chess.main"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    build {
        dependsOn(fatJar)
    }

}

val publicationName = "ChessGame"

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            artifact(tasks.jar)
            artifact(scaladocJar)
            artifact(sourceJar)
            version = project.version.toString()
            pom {
                groupId = "io.github.jahrim.chess"
                name.set("$groupId:$artifactId")
                description.set("Chess Game for PPS")
                url.set("https://github.com/jahrim/PPS-22-chess")
                packaging = "jar"
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("jahrim")
                        name.set("Jahrim Gabriele Cesario")
                        email.set("jahrim.cesario2@studio.unibo.it")
                    }
                    developer {
                        id.set("maxim-derevyanchenko")
                        name.set("Maxim Derevyanchenko")
                        email.set("maxim.derevyanchenko@studio.unibo.it")
                    }
                    developer {
                        id.set("mirko-felice")
                        name.set("Mirko Felice")
                        email.set("mirko.felice@studio.unibo.it")
                    }
                    developer {
                        id.set("madina9229")
                        name.set("Madina Kentpayeva")
                        email.set("madina.kentpayeva@studio.unibo.it")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/jahrim/PPS-22-chess.git")
                    developerConnection.set("scm:git:ssh://github.com/jahrim/PPS-22-chess.git")
                    url.set("https://github.com/jahrim/PPS-22-chess")
                }
            }
        }
        repositories {
            maven {
                val releasesUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                url = uri(releasesUrl)
                credentials {
                    val mavenUsername: String? by project
                    username = mavenUsername
                    val mavenPassword: String? by project
                    password = mavenPassword
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications[publicationName])
}
