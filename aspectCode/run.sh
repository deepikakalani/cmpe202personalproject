#!/bin/bash

SRC_FOLDER=$1

if [ "$1" == "" ]; then
	echo "please pass source folder as argument"
	exit 1
fi

export CLASSPATH=$CLASSPATH:./

rm -rf out
mkdir out

ajc -1.8 -d out $SRC_FOLDER/*.java ./*.aj

cd out
java SeqAspect

java -jar ../plantuml.jar sequence.java
