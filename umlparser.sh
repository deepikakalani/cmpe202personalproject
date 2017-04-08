#!/bin/bash

foldername="";
if [ "$1" != "" ] && [ "$2" != "" ];
then
	foldername=$PWD/$1;
	echo $foldername;
	image_name=$2
else
	echo "Input vaild params";
fi
