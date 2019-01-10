#!/bin/sh -x

./gradlew bootRun -Pargs=one,two 

# ./gradlew bootRun -Pargs=--customArgument=custom
# ./gradlew bootRun -Pargs=--spring.main.banner-mode=off,--customArgument=custom

