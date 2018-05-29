package edu.di.unimi.it.compilers.cup.gradle.tasks

import edu.di.unimi.it.compilers.cup.gradle.CupPluginExtension
import java_cup.Main
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class CupCompileTask extends DefaultTask {

    @InputDirectory
    File sourceDir

    @OutputDirectory
    File generateDir

    int expectedConflicts

    @TaskAction
    void buildCleanCup() {
        project.mkdir(generateDir)
        cupCompile()
    }

    void loadConfig() {
        def ext = project.extensions.getByType(CupPluginExtension)
        sourceDir = project.file(ext.sourceDir)
        generateDir = project.file(ext.generateDir)
        expectedConflicts = ext.expectedConflicts
    }

    void cupCompile() {
        def cupFiles = project.fileTree(dir: sourceDir, include: "**/*.cup")

        if (cupFiles.filter { !it.directory }.empty) {
            logger.warn("no cup files found")
        }
        else {
            cupFiles.visit { FileVisitDetails cupFile ->
                if (cupFile.directory)
                    return

                try {
                    def dirPath = generateDir.toPath().resolve(
                            sourceDir.toPath().relativize(
                                    cupFile.file.getParentFile().toPath()))
                            .toAbsolutePath()

                    project.mkdir(dirPath.toFile())

                    String[] args = ["-expect", expectedConflicts.toString(),
                                     "-destdir", dirPath.toString(),
                                     cupFile.file.absolutePath]
                    Main.main(args)
                }
                catch (Exception e) {
                    logger.error("cup generation failed", e)
                }
            }
        }
    }
}
