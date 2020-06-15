plugins {
    kotlin("jvm") version "1.3.72" apply false
}

allprojects {
    group = "pl.karol202.uranium.swing"
    version = "0.2.2"

    repositories {
        jcenter()
        mavenLocal()
    }
}
