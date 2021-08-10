package edu.di.unimi.it.compilers.cup.gradle

import edu.di.unimi.it.compilers.cup.gradle.tasks.CupCompileTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class CupPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            apply plugin: 'java'
            extensions.create('cup', CupPluginExtension, project)
            tasks.create(name: 'cupCompile', type: CupCompileTask)
            tasks.compileJava.dependsOn tasks.cupCompile

            dependencies {
                implementation 'com.github.vbmacher:java-cup-runtime:11b-20160615'
            }
        }

        project.afterEvaluate {
            project.tasks.cupCompile.loadConfig()
            project.sourceSets.main.java.srcDirs += project.tasks.cupCompile.generateDir
        }
    }
}
