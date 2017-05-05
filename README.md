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

To run Sequence Diagram: 
1. Ensure java is installed correctly
2. Download aspectj-1.8.10.jar from https://eclipse.org/aspectj/downloads.php
3. java -jar aspectj-1.8.10.jar
4. Follow the instructions as per the installer popped up
5. Set the CLASSPATH and PATH variabels correctly.
6. Copy the folder aspectCode to your working directory
7. Fire the run.sh script with parameter as the test zip folder. (./aspectCode/run.sh <path of java source folder>)
8. The output png would be found in the out directory generated in the aspectCode folder.

