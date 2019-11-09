#!/bin/bash

./clean.sh

PATHSEP=":"
if [[ $OS == "Windows_NT" ]] || [[ $OSTYPE == "cygwin" ]]
then
    PATHSEP=";"
fi
CP=".${PATHSEP}lib/org.json.jar${PATHSEP}lib/httpsclient-4.5.10.jar"

javac -cp $CP se/itu/systemet/main/*.java
