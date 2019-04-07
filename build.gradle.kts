plugins {
    id("org.springframework.boot") version "2.1.4.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.21"
    id("org.jetbrains.kotlin.kapt") version "1.3.21"
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    group = "io.honeymon.boot"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        compile("com.fasterxml.jackson.module:jackson-module-kotlin")
        compile("org.jetbrains.kotlin:kotlin-reflect")
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        compile("org.springframework.boot:spring-boot-starter-logging")

        /**
         * @see <a href="https://kotlinlang.org/docs/reference/kapt.html">Annotation Processing with Kotlin</a>
         */
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        compileOnly("org.springframework.boot:spring-boot-configuration-processor")

        testCompile("org.springframework.boot:spring-boot-starter-test")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
            dependsOn(processResources) // kotlin 에서 ConfigurationProperties
        }


        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
        }
    }
}

project("bootiful-core") {

    dependencies {
        compile("org.springframework.boot:spring-boot-starter-data-jpa")

        runtimeOnly("com.h2database:h2")
    }
}

project(":bootiful-sbadmin") {


    dependencies {
        compile(project(":bootiful-core"))

        compile("de.codecentric:spring-boot-admin-starter-server:2.1.4")

        // Spring Boot Admin-api 대신 클라우드 환경에서 사용한다면!
//        compile("org.springframework.cloud:spring-cloud-starter-netflix-eureka-api")
    }
}

project("bootiful-api") {
    dependencies {
        compile(project(":bootiful-core"))

        compile("org.springframework.boot:spring-boot-starter-web")
        compile("org.springframework.boot:spring-boot-starter-security")
        compile("de.codecentric:spring-boot-admin-starter-client:2.1.4")
    }
}