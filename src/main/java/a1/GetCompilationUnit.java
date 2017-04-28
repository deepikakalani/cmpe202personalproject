package a1;
import com.github.javaparser.JavaParser;



import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.*;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

	
	public List<File> extractJavaFiles(String path){
		//List<String> result = new ArrayList<String>();
		List<File> java_files = new ArrayList<File>();
		File[] files = new File(path).listFiles();
		for(File f: files){
			if(f.isFile() && (f.toString()).endsWith(".java")){
				java_files.add(f);
			}
		}
		
		return java_files;
		
	}
	
	public void appendString(BufferedWriter builder, String value) throws Exception
	{
	    builder.append(value + System.getProperty("line.separator"));
	}
	
	public static List<Pair<String, String>> removeDuplicatePairs(List<Pair<String, String>> s)
	{
		List<Pair<String, String>> ret = new ArrayList<Pair<String, String>>();
		Set<String> hash = new HashSet<String>();
		for (int z = 0; z < s.size(); z++)
		{
			String curr = s.get(z).getL() + s.get(z).getR();
			if(! hash.contains(curr))
			{
				ret.add(s.get(z));
				hash.add(curr);
			}
			
		}
		return ret;
	}
	
	public static void main(String[] args) throws Exception
	{		
		
			//extract args
			String folderPath = args[0];
			String parserFile = args[1];
			
			
			//define Lists for dependencies
			List<Pair<String, String>> list_association_one = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_association_many = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_dependency = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_interface = new ArrayList<Pair<String, String>>();
			List<Pair<String, String>> list_inheritance = new ArrayList<Pair<String, String>>();
			
			//Extract java files from given folder
			GetCompilationUnit gc = new GetCompilationUnit();
			List<File> java_files = gc.extractJavaFiles(folderPath);
			
			
			//create file for plantuml input
			String filename = parserFile;
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
				List<FieldDeclaration> variable_list = classproperties.getVariableDeclaration();
				List<MethodDeclaration> n = classproperties.getMethodDeclaration();
				ConstructorDeclaration constructor_dec = classproperties.getConstructorDeclaration();
				
				
				//create Object of ClassData class and add the map in List
				ClassData cd = new ClassData();
				cd.classcoreData = classprop;
				cd.variable_list = variable_list;
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
				List<FieldDeclaration> variable_list = cd.variable_list;
				
				String class_name;
				String extended_class;
				String[] implemented_class;
				List<String> l;
				String constructor_declaration = null;
				
				if(classDataList.get(i).classcoreData.get("classandinterface").get(0) != null){
					class_name = classprop.get("classandinterface").get(0).toString();
					gc.appendString(bw, "class " + class_name + " {"); //todo add extends or implements
				}
				else
				{
					class_name = classprop.get("classandinterface").get(1).toString();
					gc.appendString(bw, "interface " + class_name + " {");
				}
				
				List<FieldDeclaration> changeModifier = new ArrayList<FieldDeclaration>();
				for(int j = 0; j < n.size(); j++)
				{
					MethodDeclaration curM = n.get(j);
					String method_name = curM.getNameAsString();
					if (method_name.startsWith("set") || method_name.startsWith("get"))
					{
						//System.out.println("Inside this set and get method " + method_name);
						String var_name = method_name.substring(3).toLowerCase();
						for(int x=0; x<variable_list.size(); x++)
						{
							//System.out.println(variable_list.get(x).getVariables().get(0).toString());
							//System.out.println(var_name);
							if(variable_list.get(x).getVariables().get(0).toString().equals(var_name))//&& curM.getBody().get()!=null)
							{
								//System.out.println("Inside final loop");
								changeModifier.add(variable_list.get(x));
								n.remove(j);
								j--;
							}
						}
								
					}
				}
				
//				for(int y=0; y < changeModifier.size(); y++)
//				{
//					System.out.println(changeModifier.get(y).toString());
//				}
				
				//this for loop gets variable type and name and modifier
				
				for(int x=0; x<variable_list.size(); x++){
					String modifier = variable_list.get(x).getModifiers().toString().toLowerCase();
					for(int y=0; y < changeModifier.size(); y++)
					{
						String s1 = variable_list.get(x).getVariables().get(0).toString();
						String s2 = changeModifier.get(y).getVariables().get(0).toString();
						if(s1.equals(s2)){
							modifier = "public";
							System.out.println(changeModifier.get(y).getVariables().get(0).toString());
							break;
						}
					}
					
					//System.out.println(modifier);
					if (modifier.contains("private")){
						modifier = "-";
					}
					else if(modifier.contains("public"))
						modifier = "+";
					else
					{
						variable_list.remove(x);
						continue;
					}
					String type = variable_list.get(x).getElementType().toString();
					String name = variable_list.get(x).getVariable(0).toString();
					String type_name;
					
					//if else block to check if the Field Declaration is a variable type or Class type or Collection of class type.
					//todo: check if collection is substr of type. If yes find class name and add it association in todo:2
					if(type.contains("Collection"))
					{
						type_name = type.substring(type.indexOf('<')+1, type.indexOf('>'));
						list_association_many.add(new Pair<String, String>(type_name, class_name));
						//System.out.println(class_name + " many associate " + type_name);
					}
					else if(mapIsClass.get(type) == null)
						gc.appendString(bw, modifier +" " + name + ":" + type);
					else 
					{
						list_association_one.add(new Pair<String, String>(type, class_name));
						//System.out.println(class_name + " associate " + type);
						
					}
					
					
				}
				/*for(int j = 0; j<classprop.get("variables").size(); j++)
				{
					l = (List<String>) classprop.get("variables").get(j);
					String modifier = l.get(0).toString();
					if (modifier.contains("private")){
						modifier = "-";
					}
					else if(modifier.contains("public"))
						modifier = "+";
					else
					{
						classprop.get("variables").remove(j);
						continue;
					}
						
					String type = (l.get(1)).toString();
					String name = (l.get(2)).toString();
					String type_name;
					
					
					//if else block to check if the Field Declation is a variable type or Class type or Collection of class type.
					//todo: check if colletion is substr of type. If yes find class name and add it association in todo:2
					if(type.contains("Collection"))
					{
						type_name = type.substring(type.indexOf('<')+1, type.indexOf('>'));
						list_association_many.add(new Pair<String, String>(type_name, class_name));
						//System.out.println(class_name + " many associate " + type_name);
					}
					else if(mapIsClass.get(type) == null)
						gc.appendString(bw, modifier +" " + name + ":" + type);
					else 
					{
						list_association_one.add(new Pair<String, String>(type, class_name));
						//System.out.println(class_name + " associate " + type);
						
					}
				}*/
				
				if(classprop.get("extended").get("extended").toString() != "")
				{
					extended_class = classprop.get("extended").get("extended").toString();	
					list_inheritance.add(new Pair<String, String>(extended_class, class_name));
					//System.out.println(class_name + "extends" + extended_class);
				}
				
				if(classprop.get("implements") != null)
				{
					
					Map<Integer, String> interface_map = classprop.get("implements");
					
					for(int z = 0; z<interface_map.size(); z++)
					{
						String imple_class = interface_map.get(z).toString();
						list_interface.add(new Pair<String, String>(imple_class, class_name));
					}
				}
				
				
				//create constructor string
				String constructor_string = null;
				//System.out.println(constructor);
				if(constructor != null)
				{
					NodeList<Parameter> constructor_param = constructor.getParameters();
					int count = constructor_param.size();
					String parameter_list = "(";
					for(int z = 0; z<constructor_param.size(); z++)
					{
						String type = constructor_param.get(z).getType().toString();
						String name = constructor_param.get(z).getNameAsString();
						parameter_list = parameter_list + name + ":" + type;
						if(count > 1)
						{
							parameter_list = parameter_list + ", ";
							count --;
						}
					}
					parameter_list = parameter_list + ")";
					
					String const_declaration = "+";
					
					constructor_declaration = constructor.getDeclarationAsString(true, false, true);
					if(constructor_declaration.contains("public"))
					{	
						String constructor_name = constructor.getNameAsString();
						constructor_string = const_declaration + " " + constructor_name + parameter_list;
					}
					
					for(int m = 0; m < constructor_param.size(); m++)
					{
						Parameter cP = constructor_param.get(m);
						String t = cP.getType().toString();
						if(mapIsClass.get(t) != null && mapIsClass.get(t) == 1 && mapIsClass.get(class_name) != 1)
						{
							list_dependency.add(new Pair<String, String>(t, class_name));
							
						}
							
					}
				}
				//Append the constructor string
				if(constructor_string != null)
				{
					gc.appendString(bw, constructor_string);
				}
				
				//append method string for all methods where 'n' is lit of all method declarations			
				for(int j = 0; j < n.size(); j++)
				{
					MethodDeclaration curM = n.get(j);
					String method_name = curM.getNameAsString();
					String method_type = curM.getType().toString();
					
					NodeList<Parameter> lp = curM.getParameters();
					
					String method_declaration = curM.getDeclarationAsString();
					String declaration = "+";
					if(method_declaration.contains("public"))
					{	
						int count = lp.size();
						String parameter_list = "(";
						for(int z = 0; z<lp.size(); z++)
						{
							String type = lp.get(z).getType().toString();
							String name = lp.get(z).getNameAsString();
							parameter_list = parameter_list + name + ":" + type;
							if(count > 1)
							{
								parameter_list = parameter_list + ", ";
								count --;
							}
						}
						parameter_list = parameter_list + ")";
						
						declaration = declaration + " " + method_name + parameter_list + ":" + method_type;
					}
					else
					{
						n.remove(j);
						continue;
					}
					
					gc.appendString(bw, declaration);
					
					//Getting dependency relationship from method declaration and method body
					for(int m = 0; m < lp.size(); m++)
					{
						Parameter cP = lp.get(m);
						String t = cP.getType().toString();
						if(mapIsClass.get(t) != null && mapIsClass.get(t) == 1 && mapIsClass.get(class_name) != 1)
						{
							list_dependency.add(new Pair<String, String>(t, class_name));
							
						}
							
					}
					String method_body = curM.getBody().toString();
					if(method_body != "Optional.empty")
					{
						System.out.println(method_body);
						
						for(int z = 0; z < java_files.size(); z++){
							String file_name = java_files.get(z).getName().toString().substring(0, java_files.get(z).getName().toString().indexOf("."));
							
							System.out.println(file_name);
							if (method_body.contains(file_name + " "))
							{
								list_dependency.add(new Pair<String, String>(file_name, class_name));
							}	
						}
						
					}
		
				}
						
				gc.appendString(bw, "}");
		
			}
			
			System.out.println("Outside");
			//remove 
			List<Pair<String, String>> temp = new ArrayList<Pair<String, String>>();
			for(int m = 0; m<list_association_one.size(); m++)
			{
				
				for(int n = m+1; n<list_association_one.size(); n++){
					
					if((list_association_one.get(m).getL().equals(list_association_one.get(n).getR())) && (list_association_one.get(m).getR().equals( list_association_one.get(n).getL()))){
						temp.add(new Pair<String, String>(list_association_one.get(m).getL(), list_association_one.get(m).getR()));
					}
				}
			}
				
			for(int m = 0; m<temp.size(); m++){
				for(int n = m+1 ; n <list_association_one.size(); n++){
					if(temp.get(m).getL().equals(list_association_one.get(n).getL()) && temp.get(m).getR().equals(list_association_one.get(n).getR())){
						list_association_one.remove(n);
						n--;
					}
				}
			}
			
			List<Pair<String, String>> to_remove_list = new ArrayList<Pair<String, String>>();
			for(int m = 0; m < list_association_one.size(); m++){
				for(int n = 0; n<list_association_many.size(); n++){
					if(list_association_one.get(m).getR().equals(list_association_many.get(n).getL()) && list_association_one.get(m).getL().equals(list_association_many.get(n).getR())){
						to_remove_list.add(new Pair<String, String>(list_association_one.get(m).getL(), list_association_one.get(m).getR()));
						//list_association_one.remove(m);
					}
				}
			}
			
			System.out.println("Remove list entries");
			
			for(int i =0; i< to_remove_list.size(); i++){
				System.out.println(to_remove_list.get(i).getL() + " " + to_remove_list.get(i).getR());
			}
			
			for(int m=0; m<to_remove_list.size(); m++){
				for(int n = 0; n<list_association_one.size(); n++)
				{
					if(to_remove_list.get(m).getR().equals(list_association_one.get(n).getR()) && to_remove_list.get(m).getL().equals(list_association_one.get(n).getL())){
						//System.out.println();
						list_association_one.remove(n);
						n--;
					}
				}
			}
			
			//remove duplicate entries in list_dependency
			List<Pair<String, String>> list_dependency_new = removeDuplicatePairs(list_dependency);
			List<Pair<String, String>> list_inheritance_new = removeDuplicatePairs(list_inheritance);
			List<Pair<String, String>> list_association_one_new = removeDuplicatePairs(list_association_one);
			List<Pair<String, String>> list_association_many_new = removeDuplicatePairs(list_association_many);
			List<Pair<String, String>> list_interface_new = removeDuplicatePairs(list_interface);
			
			for(int x = 0; x<list_interface_new.size();x++)
			{
				System.out.println(list_interface_new.get(x).getL() + list_interface_new.get(x).getR());
			}
			//code for appending relationships in parser file using lists generated above
			for(int z=0;z<list_inheritance_new.size();z++)
			{
				gc.appendString(bw, list_inheritance_new.get(z).getL() +" <|-- " + list_inheritance_new.get(z).getR());
			}
			for(int z=0;z<list_association_one_new.size();z++)
			{
				gc.appendString(bw, list_association_one_new.get(z).getL() +" -- " + list_association_one_new.get(z).getR());
			}
			for(int z=0;z<list_association_many_new.size();z++)
			{
				gc.appendString(bw, list_association_many_new.get(z).getL() +" \"*\" -- \"1\" "  + list_association_many_new.get(z).getR());
			}
			for(int z=0;z<list_dependency_new.size();z++)
			{
				gc.appendString(bw, list_dependency_new.get(z).getL() +" <.. " + list_dependency_new.get(z).getR());
			}
			for(int z=0;z<list_interface_new.size();z++)
			{
				gc.appendString(bw, list_interface_new.get(z).getL() +" <|.. " + list_interface_new.get(z).getR());
			}
			gc.appendString(bw, "@enduml");
			bw.close();
			
			Process proc = Runtime.getRuntime().exec("java -jar plantuml.jar " + filename + ".java");
			
			
			
	}	


}