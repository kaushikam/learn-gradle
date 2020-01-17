import org.apache.tools.ant.filters.ReplaceTokens

plugins {
	id("base")
}

val dataContent = copySpec {
	from("src/data")
	include("**/*.txt")
}

tasks.create<Copy>("initConfig") {
	from("src/main/resources") {
		include("**/*.properties")
		include("**/*.xml")
		filter(ReplaceTokens::class, "tokens" to mapOf("version" to "2.3.1"))
	}
	from("src/main/languages") {
		rename("EN_US.(.*)", "$1")
	}
	exclude("**/*.bak")
	includeEmptyDirs = false
	into("build/target/config")
	with(dataContent)
}

tasks.create<Copy> ("copy") {
	description = "Copies file from 'src/main/resources/test.txt' to 'dest'"
	group = "Custom"

	from("src/main/resources")
	into("dest")
}

tasks.create<Zip>("zip") {
	description = "Archives sources in a zip file"
	group = "Archive"

	from("src")
	archiveFileName.set("learn-gradle.zip")
}