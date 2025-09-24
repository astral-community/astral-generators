plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.fabricLoom)
    alias(libs.plugins.spotless)
    idea
}

val maven_group: String by project
val mod_version: String by project
val mod_id: String by project

group = maven_group

version = mod_version

base.archivesName = mod_id

repositories {
    mavenCentral()

    maven { url = uri("https://maven.parchmentmc.org") }
    maven { url = uri("https://maven.terraformersmc.com/") }
    maven { url = uri("https://mvn.devos.one/snapshots/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://api.modrinth.com/maven/") }
    maven { url = uri("https://maven.shedaniel.me/") }
    maven { url = uri("https://server.bbkr.space/artifactory/libs-release") }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.18.2:2022.11.06@zip")
        })

    modImplementation(libs.fabricLoader)
    modImplementation(libs.fabricApi)
    modImplementation(libs.fabricKotlin)
    modImplementation(libs.codebebelib)

    modImplementation(libs.registrate) { exclude(group = "io.github.fabricators_of_create") }

    include(modImplementation(libs.libgui.get())!!)
    include(modImplementation(libs.energyApi.get())!!)
    include(modImplementation(libs.portingLib.get())!!)
    include(modImplementation(libs.forgeTags.get())!!)
    include(libs.registrate) { exclude(group = "io.github.fabricators_of_create") }

    modLocalRuntime(libs.ctmRefabricated)
    modLocalRuntime(libs.jade)
    modLocalRuntime(libs.jadeAddons)
    modLocalRuntime(libs.lazydfu)
    modLocalRuntime(libs.rei)
    modLocalRuntime(libs.modMenu)
}

configurations.configureEach { resolutionStrategy { force(libs.fabricLoader) } }

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources")
            exclude(".cache")
        }
    }
}

loom {
    runs {
        register("datagen") {
            client()

            name("Data Generation")
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/generated/resources")}")
            vmArg("-Dfabric-api.datagen.modid=${mod_id}")
            vmArg("-Dporting_lib.datagen.existing_resources=${file("src/main/resources")}")
        }

        getByName("server") { runDir("run/server") }
    }
}

kotlin { jvmToolchain(17) }

java { withSourcesJar() }

tasks.jar {
    inputs.property("archivesName", base.archivesName)

    from("LICENSE") { rename { "${it}_${mod_id}" } }
}

tasks.test { useJUnitPlatform() }

tasks.withType<ProcessResources>().configureEach {
    val mod_id: String by project
    val mod_version: String by project
    val mod_name: String by project
    val mod_description: String by project
    val mod_license: String by project
    val mod_homepage: String by project
    val mod_issue_tracker_url: String by project

    val replaceProperties =
        mapOf<String, String>(
            "mod_id" to mod_id,
            "mod_version" to mod_version,
            "mod_name" to mod_name,
            "mod_description" to mod_description,
            "mod_license" to mod_license,
            "mod_homepage" to mod_homepage,
            "mod_issue_tracker_url" to mod_issue_tracker_url)

    inputs.properties(replaceProperties)

    filesMatching("fabric.mod.json") { expand(replaceProperties) }
}

spotless {
    encoding("UTF-8")

    java {
        importOrder()

        removeUnusedImports()
        removeWildcardImports()

        cleanthat()
    }

    kotlin {
        ktfmt().kotlinlangStyle().configure {
            it.setBlockIndent(4)
            it.setRemoveUnusedImports(true)
            it.setMaxWidth(140)
            it.setManageTrailingCommas(false)
            it.setContinuationIndent(4)
        }
        endWithNewline()
        toggleOffOn()
    }

    json {
        target("src/*/resources/**/*.json")
        targetExclude("src/generated/resources/**")

        gson().indentWithSpaces(2)
    }

    format("dotfiles") {
        target(".gitignore", ".gitattributes", ".editorconfig")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
