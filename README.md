A gradle plugin for the [CUP](http://www2.cs.tum.edu/projects/cup/) (Construction of Useful Parsers) parser generator.

Note that JCenter somehow uploaded the wrong version of CUP again.  
I've requested another refresh, in the meantime use this at the beginning of your build.gradle to force the download from Maven Central:

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
}
```