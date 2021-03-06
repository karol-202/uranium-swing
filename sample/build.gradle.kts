import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	application
}

application {
	mainClassName = "pl.karol202.uranium.swing.sample.MainKt"
	applicationDefaultJvmArgs = listOf(
			"-Dsun.awt.disablegrab=true",
			"-Djdk.gtk.version=2.2" /* OpenJDK 8u242 switched default implementation to GTK3,
			 which results in terribly looking UI */
	)
}

dependencies {
	implementation(project(":uranium-swing"))

	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}
