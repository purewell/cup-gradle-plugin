A gradle plugin for the [CUP](http://www2.cs.tum.edu/projects/cup/) (Construction of Useful Parsers) parser generator.

### Importing the plugin

Add the plugin to the list of imported plugins in your build.gradle file:
```
plugins {
    id "cup.gradle.cup-gradle-plugin" version "1.2"
}
```

### Configuration
Default source directory is *src/main/cup*. All files ending with a *.cup* extension will be processed.  
Default output directory is *${project.buildDir}/generated-src/cup*. Directory structure will be maintained the same as the source directory.  

These defaults can be changed by using the *cup* section of your build.gradle file.  
Additionally, custom command line arguments can be supplied to the cup compiler as well:
```
cup {
    sourceDir = my/sources/directory
    generateDir = my/generated/sources/directory
    args = ["-interface", "-locations", "-parser", "MyParserClassName", "-symbols", "MySymbolsClassName"]
}
```

### Running
The plugin will automatically run cup compilation before Java compilation with no additional input required.  
If you want to generate sources manually, you can use the *cupCompile* task:
```
gradle cupCompile
```
