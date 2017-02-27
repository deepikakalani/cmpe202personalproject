package a1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ClassProperties {
	
	CompilationUnit c;
	List<String> method_list;
	List<String> variable_list;
	List<String> extended_class_list;
	List<String> interface_list;
	
	public ClassProperties(String filename) throws FileNotFoundException{

		FileInputStream infile = new FileInputStream(filename);
		c = JavaParser.parse(infile);
		System.out.println(filename);
		
	}
	
	public void getclassdata(){
		MethodVisitor methodvisitor = new MethodVisitor();
		VariableVisitor variablevisitor = new VariableVisitor();
		ExtendedClassVisitor extendedclassvisitor = new ExtendedClassVisitor();
		InterfaceVisitor interfacevisitor = new InterfaceVisitor();
		
		methodvisitor.visit(c, null);
		method_list = methodvisitor.get_method_list();
		System.out.println(method_list);
		
		variablevisitor.visit(c, null);
		variable_list = methodvisitor.get_method_list();
		System.out.println(variable_list);
		
		extendedclassvisitor.visit(c, null);
		extended_class_list = methodvisitor.get_method_list();
		System.out.println(extended_class_list);
		
		interfacevisitor.visit(c, null);
		interface_list = methodvisitor.get_method_list();
		System.out.println(interface_list);
		
	}
	
	
}
