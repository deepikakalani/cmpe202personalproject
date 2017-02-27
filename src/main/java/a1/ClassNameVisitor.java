package a1;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassNameVisitor extends VoidVisitorAdapter<Void>{
	
	String class_name;
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg){
		super.visit(n, arg);
		
			//System.out.println(n.getName());
			class_name = n.getNameAsString();
	}
		
	public String getClassName(){
		return class_name;
	}
	

}
