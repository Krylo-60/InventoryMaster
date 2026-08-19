plugins {
    id("fabric-loom") version "1.8-SNAPSHOT"
    id("java")
}

val minecraftVersion = "1.21.1"
val yarnMappings = "1.21.1+build.3:v2"
val loaderVersion = "0.16.10"
val fabricVersion = "0.102.1+1.21.1"

version = "1.0.0+1.21.1"
group = "com.krishivstudios"

base {
    archivesName.set("InventoryMaster-fabric")
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
}
