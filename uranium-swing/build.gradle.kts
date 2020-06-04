import com.jfrog.bintray.gradle.BintrayExtension.PackageConfig
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	id("com.jfrog.bintray")
	`maven-publish`
}

dependencies {
	api("pl.karol202.uranium:uranium-core-jvm:0.2-2")

	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.3.7")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}

tasks.register<Jar>("sourcesJar") {
	archiveClassifier.set("sources")
	from(sourceSets.main.get().allSource)
}

bintray {
	user = project.findProperty("bintray.user") as String?
	key = project.findProperty("bintray.key") as String?
	setPublications("uraniumSwing")
	publish = true

	pkg(delegateClosureOf<PackageConfig> {
		repo = "uranium"
		name = "uranium-swing"
		description = "Uranium for Swing"
		vcsUrl = "https://github.com/karol-202/uranium-swing"
		githubRepo = "karol-202/uranium-swing"
		setLicenses("MIT")
	})
}

publishing {
	publications {
		create<MavenPublication>("uraniumSwing") {
			from(components["java"])
			artifact(tasks["sourcesJar"])

			pom {
				name.set(bintray.pkg.name)
				description.set(bintray.pkg.desc)
				url.set(bintray.pkg.vcsUrl)
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
					url.set(bintray.pkg.vcsUrl)
				}
			}
		}
	}
}
