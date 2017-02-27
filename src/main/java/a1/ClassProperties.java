package a1;

import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class ClassProperties {
	
	CompilationUnit c;
	List<String> method_list;
	List<VariableDeclarator> variable_list;
	List<ClassOrInterfaceType> extended_class_list;
	List<ClassOrInterfaceType> interface_list;
	String class_name;
	String result;
	
	public ClassProperties(String filename) throws FileNotFoundException{
		FileInputStream infile = new FileInputStream(filename);
		c = JavaParser.parse(infile);
		System.out.println(c);
		//System.out.println(filename);
	}
	
	public void getclassdata(){
		MethodVisitor methodvisitor = new MethodVisitor();
		VariableVisitor variablevisitor = new VariableVisitor();
		ExtendedClassVisitor extendedclassvisitor = new ExtendedClassVisitor();
		InterfaceVisitor interfacevisitor = new InterfaceVisitor();
		ClassNameVisitor classnamevisitor = new ClassNameVisitor();
		
		classnamevisitor.visit(c, null);
		result = classnamevisitor.getClassName();
		//System.out.println(result);
		
		methodvisitor.visit(c, null);
		method_list = methodvisitor.get_method_list();
		//System.out.println(method_list);
		
		variablevisitor.visit(c, null);
		variable_list = variablevisitor.get_variable_list();
		//System.out.println(variable_list);
		
		extendedclassvisitor.visit(c, null);
		extended_class_list = extendedclassvisitor.get_extended_class_list();
		//System.out.println(extended_class_list);
		
		interfacevisitor.visit(c, null);
		interface_list = interfacevisitor.get_interface_class_list();
		//System.out.println(interface_list);
		
		
		
	}
	
	public void appendString(BufferedWriter builder, String value) throws Exception
	{
	    builder.append(value + System.getProperty("line.separator"));
	}
	
	
	public void writeToJavaFile(BufferedWriter bw) throws Exception{
		getclassdata();
		
		appendString(bw, "/**");
		appendString(bw, "* @opt all");
		appendString(bw, "*/");
		appendString(bw, "class " + result);
		if(!extended_class_list.isEmpty()){
			System.out.println(extended_class_list);
		}
		
		appendString(bw, "{");
		//System.out.println(variable_list);
		//System.out.println(variable_list.size());
		
		appendString(bw, "}");
		
		
	}
	
	
}
