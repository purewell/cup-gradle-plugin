package edu.di.unimi.it.compilers.cup.gradle

import edu.di.unimi.it.compilers.cup.gradle.tasks.CupCompileTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class CupPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            apply plugin: 'java'
            tasks.create(name: 'cupCompile', type: CupCompileTask)
            tasks.compileJava.dependsOn tasks.cupCompile
            sourceSets.main.java.srcDirs += tasks.cupCompile.generateDir

            dependencies {
                compile 'com.github.vbmacher:java-cup-runtime:11b-20160615'
            }
        }
    }
}
