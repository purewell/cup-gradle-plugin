package edu.di.unimi.it.compilers.cup.gradle.tasks

import java_cup.Main
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class CupCompileTask extends DefaultTask {

    @InputDirectory
    @Optional
    File source = project.file("src/main/cup")

    @OutputDirectory
    @Optional
    File generateDir = project.file("$project.buildDir/generated-src/cup")

    @Optional
    int expectedErrors = 0

    @TaskAction
    void buildCleanCup() {
        project.mkdir(generateDir)
        cupCompile()
    }

    void cupCompile() {
        def cupFiles = project.fileTree(dir: source, include: "**/*.cup")

        if (cupFiles.filter { !it.directory }.empty) {
            logger.warn("no cup files found")
        }
        else {
            cupFiles.visit { FileVisitDetails cupFile ->
                if (cupFile.directory)
                    return

                try {
                    def dirPath = generateDir.toPath().resolve(
                            source.toPath().relativize(
                                    cupFile.file.getParentFile().toPath()))
                            .toAbsolutePath()

                    project.mkdir(dirPath.toFile())

                    String[] args = ["-expect", expectedErrors.toString(),
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
