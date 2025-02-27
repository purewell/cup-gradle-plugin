package edu.di.unimi.it.compilers.cup.gradle.tasks

import edu.di.unimi.it.compilers.cup.gradle.CupPluginExtension
import java_cup.Main
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class CupCompileTask extends DefaultTask {

    @InputDirectory
    File sourceDir

    @OutputDirectory
    File generateDir

    @Input
    @Optional
    String[] arguments

    @TaskAction
    void run() {
        def cupFiles = project.fileTree(dir: sourceDir, include: "**/*.cup")

        if (cupFiles.filter { !it.directory }.empty) {
            logger.warn("no cup files found")
        }
        else {
            cupFiles.visit { FileVisitDetails cupFile ->
                if (cupFile.directory)
                    return

                def dirPath = generateDir.toPath().resolve(
                        sourceDir.toPath().relativize(
                                cupFile.file.getParentFile().toPath()))
                        .toAbsolutePath()

                project.mkdir(dirPath.toFile())

                String[] cupArgs = [arguments, ["-destdir", dirPath.toString(),
                                    cupFile.file.absolutePath]].flatten()

                def myErr = new ByteArrayOutputStream()

                def result = project.javaexec {
                        args cupArgs
                        classpath = project.buildscript.configurations.classpath
                        mainClass = Main.class.getName()
                        errorOutput = myErr
                        ignoreExitValue = true
                }

                if (result.exitValue != 0) {
                    throw new GradleException(myErr.toString("UTF-8"))
                }
            }
        }
    }

    void loadConfig() {
        def ext = project.extensions.getByType(CupPluginExtension)
        sourceDir = project.file(ext.sourceDir)
        generateDir = project.file(ext.generateDir)
        arguments = ext.args
    }
}
