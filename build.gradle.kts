plugins {
    kotlin("jvm") version "1.3.72" apply false
}

allprojects {
    group = "pl.karol202.uranium"
    version = "0.2.1"

    repositories {
        jcenter()
        mavenLocal()
    }
}
