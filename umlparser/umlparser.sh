if [ $# -ne 0 ]
then
    echo $1 $2
fi

command1="java -jar umlparserdeepika.jar $1 $2"
eval $command1
#command2="java -jar plantuml.jar $2"
#eval $command2
