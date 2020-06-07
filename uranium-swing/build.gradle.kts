import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	`maven-publish`
}

dependencies {
	api("pl.karol202.uranium:uranium-core-jvm:0.2.1")

	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.3.7")
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "1.8"
	}

	val sourcesJar by registering(Jar::class) {
		archiveClassifier.set("sources")
		from(sourceSets.main.get().allSource)
	}
}

object Publish
{
	const val repo = "uranium"
	const val name = "uranium-swing"
	const val description = "React-like, declarative Kotlin library for creating Swing desktop apps"
	const val vcsUrl = "https://github.com/karol-202/uranium-swing"
	const val githubRepo = "karol-202/uranium-swing"
}

publishing {
	repositories {
		val user = System.getenv("BINTRAY_USER")
		val key = System.getenv("BINTRAY_KEY")

		maven("https://api.bintray.com/maven/$user/${Publish.repo}/${Publish.name}/;publish=1") {
			credentials {
				username = user
				password = key
			}
		}
	}

	publications {
		create<MavenPublication>("uraniumSwing") {
			from(components["java"])
			artifact(tasks["sourcesJar"])

			pom {
				name.set(Publish.name)
				description.set(Publish.description)
				url.set(Publish.vcsUrl)
				licenses {
					license {
						name.set("MIT")
						url.set("https://opensource.org/licenses/MIT")
					}
				}
				developers {
					developer {
						id.set("karol202")
						name.set("Karol Jurski")
						email.set("karoljurski1@gmail.com")
					}
				}
				scm {
					url.set(Publish.vcsUrl)
				}
			}
		}
	}
}
