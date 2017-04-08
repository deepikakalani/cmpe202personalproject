package a1;

import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class ClassProperties {
	
	CompilationUnit c;
	List<String> method_list;
	List<FieldDeclaration> variable_list;
	String extended_class;
	String class_name;
	NodeList<ClassOrInterfaceType> interface_classes;
	ClassOrInterfaceDeclaration result;
	Map<String, Map> classprop = new HashMap<String, Map>();
	List<MethodDeclaration> nlist;
	ConstructorDeclaration constructor_dec;
	
	public List<MethodDeclaration> getMethodDeclaration()
	{
		return nlist;
	}
	
	public ConstructorDeclaration getConstructorDeclaration()
	{
		return constructor_dec;
	}
	
	public List<FieldDeclaration> getVariableDeclaration(){
		return variable_list;
	}
	
	public ClassProperties(String filename) throws FileNotFoundException{
		FileInputStream infile = new FileInputStream(filename);
		c = JavaParser.parse(infile);
		
		//System.out.println(c);
		//System.out.println(filename);
	}
	
	public Map<String, Map> getclassdata(){
		MethodVisitor methodvisitor = new MethodVisitor();
		VariableVisitor variablevisitor = new VariableVisitor();
		ExtendedClassVisitor extendedclassvisitor = new ExtendedClassVisitor();
		InterfaceVisitor interfacevisitor = new InterfaceVisitor();
		ClassNameVisitor classnamevisitor = new ClassNameVisitor();
		ConstructorVisitor constructorvisitor = new ConstructorVisitor();
		
		classnamevisitor.visit(c, null);
		Map<Integer, String> result = classnamevisitor.getClassName();
		classprop.put("classandinterface", result);
		//System.out.println(result.get("classname").getNameAsString());
		//System.out.println(result);
		 		
		
		
		variablevisitor.visit(c, null);
		variable_list = variablevisitor.get_variable_list();
		Map<Integer,List> variable_map = new HashMap<Integer,List>();
		//List<List> var_info = new ArrayList<List>();
		
		//for(int i =0; i < variable_list.size();i++)
		int counter = 0;
		for(FieldDeclaration var: variable_list){
			
			List<String> var_ent = new ArrayList<String>();
			var_ent.add(var.getModifiers().toString().toLowerCase());
			var_ent.add(var.getElementType().toString());
			var_ent.add(var.getVariables().get(0).toString());
			variable_map.put(counter,var_ent);
			counter = counter+1;
		}
		
		//variable_map.put(counter++, var_info);
		classprop.put("variables", variable_map);
		//System.out.println(classprop.get("variables"));
		//System.out.println(variable_list);
		//Map<String, String> variable_data = new HashMap<String, String>();
		//for(FieldDeclaration var: variable_list)
			//(var.getElementType());
		
		
		methodvisitor.visit(c, null);
		nlist = methodvisitor.get_method_list();

		
		
		extendedclassvisitor.visit(c, null);
		extended_class = extendedclassvisitor.get_extended_class_list();
		Map<String,String> extended_map = new HashMap<String,String>();
		extended_map.put("extended", extended_class);	
		classprop.put("extended", extended_map);
		
		
		interfacevisitor.visit(c, null);
		interface_classes = interfacevisitor.get_interface_class_list();
		Map<Integer, String> interface_map = new HashMap<Integer, String>();
		if(interface_classes!=null)
		{
			for(int i = 0; i< interface_classes.size(); i++)
			{
				System.out.println(interface_classes.get(i).toString());
				interface_map.put(i, interface_classes.get(i).toString());
			}
		}
		classprop.put("implements", interface_map);

		constructorvisitor.visit(c, null);
		constructor_dec = constructorvisitor.get_constructor();
		
		return classprop;
		
		
	}
	
	public void appendString(BufferedWriter builder, String value) throws Exception
	{
	    builder.append(value + System.getProperty("line.separator"));
	}
	
	
}
