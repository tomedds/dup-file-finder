#!/bin/bash -x
JAVA_EXE=/usr/bin/java
JAR=./build/libs/dup-file-finder-0.0.1-SNAPSHOT.jar
$JAVA_EXE -jar $JAR ~/tmp ~/dev/courses
