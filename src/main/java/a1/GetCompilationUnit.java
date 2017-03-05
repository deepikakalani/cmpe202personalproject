package a1;
import com.github.javaparser.JavaParser;



import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.*;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class Pair<L,R> {
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
}


public class GetCompilationUnit {
	static File file;
	static BufferedWriter bw;

	//File parsed_code = new File();
	
	public List<String> extractJavaFiles(String path){
		//List<String> result = new ArrayList<String>();
		List<String> java_files = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		for(File f: files){
			if(f.isFile() && (f.toString()).endsWith(".java"))
				java_files.add(f.toString());
		}
		

		return java_files;
		
	}
	
	public void appendString(BufferedWriter builder, String value) throws Exception
	{
	    builder.append(value + System.getProperty("line.separator"));
	}
	
	public static void main(String[] args) throws Exception
	{		
			//define Lists for dependencies
			List<Pair<String, String>> list_association_one = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_association_many = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_dependency = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_interface = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_inheritance = new ArrayList<Pair<String, String>>();
			//Extract java files from given folder
			GetCompilationUnit gc = new GetCompilationUnit();
			List<String> java_files = gc.extractJavaFiles("/home/deepika/Projects/cmpe202/umlparser/uml-parser-test-4/");
			
			
			//List<String> class_list = new ArrayList<String>();
			//Map<String,String> class_map = new HashMap<String,String>();
			
			
			//for(int i =0; i<java_files.size();i++)
				//class_list.add(java_files.get(i).split("/")[7].split(".java")[0].toString());
			
			//create file for plantuml input
			String filename = "/home/deepika/parseroutput";
			file = new File(filename +".java");
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			gc.appendString(bw, "@startuml");
			gc.appendString(bw, "skinparam classAttributeIconSize 0");
			
			//List for storing data per class
			List<ClassData> classDataList = new ArrayList<ClassData>();
			//Map for checking if it is interface or class, class - 0 , interface - 1
			Map<String, Integer> mapIsClass = new HashMap<String, Integer>();
			//pre-process class data
			for(int i = 0; i<java_files.size(); i++){
				
				//call class properties for each class
				ClassProperties classproperties = new ClassProperties(java_files.get(i).toString());
				//classproperties.writeToJavaFile(bw);
				Map<String, Map> classprop= classproperties.getclassdata();
				
				List<MethodDeclaration> n = classproperties.getMethodDeclaration();
				ConstructorDeclaration constructor_dec = classproperties.getConstructorDeclaration();

				//create Object of ClassData class and add the map in List
				ClassData cd = new ClassData();
				cd.classcoreData = classprop;
				cd.nlist= n;
				cd.const_dec = constructor_dec;
				classDataList.add(cd);
			}	
			
			//creating map to get 0 or 1 for class and interface respectively
			for(int i = 0; i < classDataList.size(); i++){
				if(classDataList.get(i).classcoreData.get("classandinterface").get(0) == null )
					mapIsClass.put(classDataList.get(i).classcoreData.get("classandinterface").get(1).toString(), 1);
				else
					mapIsClass.put(classDataList.get(i).classcoreData.get("classandinterface").get(0).toString(), 0);	
			}
			

			//this for loop is for fetching data of all classes stored in classDataList to create file for plant uml
			for(int i = 0; i<classDataList.size(); i++){
				ClassData cd = classDataList.get(i);
				Map<String, Map> classprop = cd.classcoreData;
				List<MethodDeclaration> n = cd.nlist;
				ConstructorDeclaration constructor = cd.const_dec;
				
				String class_name;
				String extended_class;
				String implemented_class;
				List<String> l;
				
				if(classDataList.get(i).classcoreData.get("classandinterface").get(0) != null){
					class_name = classprop.get("classandinterface").get(0).toString();
					gc.appendString(bw, "class " + class_name + " {"); //todo add extends or implements
				}
				else
				{
					class_name = classprop.get("classandinterface").get(1).toString();
					gc.appendString(bw, "interface " + class_name + " {");
					
				}
				if(constructor != null)
					System.out.println(constructor.getDeclarationAsString());
				//System.out.println("class : " + class_name);
				for(int w = 0; w < n.size(); w++)
					//System.out.println(n.get(w).getDeclarationAsString(true, true, true));
					
				//this for loop gets variable type and name and modifier
				for(int j = 0; j<classprop.get("variables").size(); j++)
				{
					l = (List<String>) classprop.get("variables").get(j);
					String modifier = l.get(0).toString();
					if (modifier.contains("private")){
						modifier = "-";
					}
					else if(modifier.contains("public"))
						modifier = "+";
					String type = (l.get(1)).toString();
					String name = (l.get(2)).toString();
					String type_name;
					
					//if else block to check if the Field Declation is a variable type or Class type or Collection of class type.
					//todo: check if colletion is substr of type. If yes find class name and add it association in todo:2
					if(type.contains("Collection"))
					{
						type_name = type.substring(type.indexOf('<')+1, type.indexOf('>'));
						list_association_many.add(new Pair<String, String>(class_name, type_name));
						//System.out.println(class_name + " many associate " + type_name);
					}
					else if(mapIsClass.get(type) == null)
						gc.appendString(bw, modifier +" " + type + ":" + name);
					else 
					{
						list_association_one.add(new Pair<String, String>(class_name, type));
						//System.out.println(class_name + " associate " + type);
						
					}
				}
				
				if(classprop.get("extended").get("extended").toString() != "")
				{
					extended_class = classprop.get("extended").get("extended").toString();	
					list_inheritance.add(new Pair<String, String>(extended_class, class_name));
					//System.out.println(class_name + "extends" + extended_class);
				}
				
				if(classprop.get("implements").get("implements").toString() != "")
				{
					implemented_class = classprop.get("implements").get("implements").toString();
					list_interface.add(new Pair<String, String>(implemented_class, class_name));
					//System.out.println(class_name + "implements" + implemented_class);
				}
				
				for(int j = 0; j < n.size(); j++)
				{
					MethodDeclaration curM = n.get(j);
					NodeList<Parameter> lp = curM.getParameters();
					for(int m = 0; m < lp.size(); m++)
					{
						Parameter cP = lp.get(m);
						String t = cP.getType().toString();
						if(mapIsClass.get(t) == 1)
						{
							list_dependency.add(new Pair<String, String>(t, class_name));
							//todo remove duplicate entries
						}
							
					}
				}
				
				constructor.getParameters();
				
				//todo3 : do same for methods
				
				//todo4 : write code for --> --->> using logic generated above
				
				gc.appendString(bw, "}");
			}
			
			gc.appendString(bw, "@enduml");
			bw.close();
			
			
			
	}	


}