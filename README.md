A gradle plugin for the [CUP](http://www2.cs.tum.edu/projects/cup/) (Construction of Useful Parsers) parser generator.

Note: due to the fact that the JCenter version of the CUP library is incomplete,
despite being tagged as being the same version as the one on Maven central, all 
build.gradle scripts using this plugin need to start with

```
buildscript {
    repositories {
        mavenCentral()
    }
}
```

even when using the new plug-in sytnax, to ensure the download of the correct library.