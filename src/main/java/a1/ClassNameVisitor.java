package a1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassNameVisitor extends VoidVisitorAdapter<Void>{
	
	ClassOrInterfaceDeclaration class_name ;
	ClassOrInterfaceDeclaration interface_name;
	
	Map<String,ClassOrInterfaceDeclaration> map = new HashMap();
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg){
		super.visit(n, arg);
		
		if (!n.isInterface())
			map.put("classname", n);
		else
			map.put("interfacename", n);
		//else
			//interface_name = n.getNameAsString();
	}
		
	public Map<String,ClassOrInterfaceDeclaration> getClassName(){
		return map;
	}
	

}
