plugins {
    `java-library`
    `maven-publish`
    jacoco
}

group = "com.mictale"
version = providers.gradleProperty("version").getOrElse("SNAPSHOT")

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().configureEach {
    // Bytecode for Java 11 and Android, whatever JDK runs the build.
    options.release.set(11)
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

dependencies {
    // Nullness annotations that Kotlin reads; runtime-free, a few kilobytes.
    api("org.jspecify:jspecify:1.0.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest:3.0")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    testLogging {
        events("failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

// Coverage of the unit tests, in build/reports/jacoco/test/html.
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)
    }
}

// The build fails when unit tests cover less than 90% of lines or branches.
tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                counter = "LINE"
                minimum = "0.90".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                minimum = "0.90".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("Jsonite")
                description.set("A JSON parser with streaming API")
                url.set("https://github.com/lemachi/jsonite")
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
            }
        }
    }
}
