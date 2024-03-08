pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri ("https://www.jitpack.io") }
        maven {
            url = uri ("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                //create<BasicAuthentication>("basic")
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoibHVjaW9lbSIsImEiOiJjbG8yMDB1d3oxaHR1MnFxbTR6a2pnYWp5In0.AztCwSY5-2-btxhcMmxBJQ"
            }
        }
    }
}

rootProject.name = "AppTurismo"
include(":app")
 