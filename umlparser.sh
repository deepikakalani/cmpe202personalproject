#!/bin/bash

foldername="";
if [ "$1" != "" ] && [ "$2" != "" ];
then
	foldername=$1;
	echo $foldername;
else
	echo "Input vaild params";
fi
