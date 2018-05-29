package edu.di.unimi.it.compilers.cup.gradle

import org.gradle.api.Project

class CupPluginExtension {
    String generateDir
    String sourceDir
    int expectedConflicts

    CupPluginExtension(Project project) {
        generateDir = "$project.buildDir/generated-src/cup"
        sourceDir = "src/main/cup"
        expectedConflicts = 0
    }
}
