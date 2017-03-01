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
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class ClassProperties {
	
	CompilationUnit c;
	List<String> method_list;
	List<FieldDeclaration> variable_list;
	List<ClassOrInterfaceType> extended_class_list;
	List<ClassOrInterfaceType> interface_list;
	String class_name;
	String interface_name;
	ClassOrInterfaceDeclaration result;
	Map<String, Map> classprop = new HashMap<String, Map>();
	
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
		
		classnamevisitor.visit(c, null);
		Map<String,ClassOrInterfaceDeclaration> result = classnamevisitor.getClassName();
		classprop.put("classandinterface", result);
		System.out.println(result.get("classname").getNameAsString());
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
		System.out.println(classprop.get("variables"));
		System.out.println(variable_list);
		//Map<String, String> variable_data = new HashMap<String, String>();
		//for(FieldDeclaration var: variable_list)
			//(var.getElementType());
		
		
		methodvisitor.visit(c, null);
		method_list = methodvisitor.get_method_list();
		//System.out.println(method_list);
		
		
		
		extendedclassvisitor.visit(c, null);
		extended_class_list = extendedclassvisitor.get_extended_class_list();
		//System.out.println(extended_class_list);
		
		interfacevisitor.visit(c, null);
		interface_list = interfacevisitor.get_interface_class_list();
		//System.out.println(interface_list);
		
		return classprop;
		
		
	}
	
	public void appendString(BufferedWriter builder, String value) throws Exception
	{
	    builder.append(value + System.getProperty("line.separator"));
	}
	
	
	/*public void writeToJavaFile(BufferedWriter bw) throws Exception{
		getclassdata();
		
		appendString(bw, "/**");
		appendString(bw, "* @opt all");
		appendString(bw, " ");
		appendString(bw, "class " + result);
		if(!extended_class_list.isEmpty()){
			System.out.println(extended_class_list);
		}
		
		appendString(bw, "{");
		//System.out.println(variable_list);
		//System.out.println(variable_list.size());
		
		appendString(bw, "}");
		
		
	}*/
	
	public void writeToJavaFile(BufferedWriter bw) throws Exception{
		getclassdata();
		
		if(!result.isInterface())
		{
			class_name = result.getNameAsString();
			appendString(bw, "class " + class_name + " {");
		}
		else
		{
			interface_name = result.getNameAsString();
			appendString(bw, "interface " + interface_name + " {");
		}
		
		for(FieldDeclaration var: variable_list)
			if(var.isPrivate())
				appendString(bw, "- " + var.getElementType().toString()+" " + var.getVariables().get(0).toString());
			
		appendString(bw, "}");
	
	}
	
	
}
