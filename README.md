# cmpe202personalproject
this is a java source code converter into uml diagram(Class and Sequence diagram)

Class Diagram : 
Liabraries used : Javaparser http://www.javadoc.io/doc/com.github.javaparser/javaparser-core/3.1.0
                  PlantUml http://plantuml.com/class-diagram

There is a script umlparser.sh that runs the jar file for generating class diagram. Class diagram is genarated using JavaParser to parse the java code and visualization of diagram is done using PlanUML. umlparser.sh takes two inputs 
- Java code source folder
- name of the png file to be generated.

Ex : umlparser.sh <path to source folder> <name of image file to be generated>



Sequence diagram:
Technologies/Libraries used: AspectJ, PlantUml

Sequence diagram is generated given the source code is already present in the folder where my .aj file is. I have created the JAR which alreay has the the source code and the main function from the Main file is executed.

To run the jar:

