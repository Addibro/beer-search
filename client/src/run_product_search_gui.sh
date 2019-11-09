#!/bin/bash

PATHSEP=":"
if [[ $OS == "Windows_NT" ]] || [[ $OSTYPE == "cygwin" ]]
then
    PATHSEP=";"
fi
CP=".${PATHSEP}lib/org.json.jar"
#CP="."
java -cp .:lib/org.json.jar:lib/httpclient-4.5.10.jar se.itu.systemet.main.ProductSearch
