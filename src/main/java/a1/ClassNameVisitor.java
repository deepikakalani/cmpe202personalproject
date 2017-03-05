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
	
	Map<Integer, String> map = new HashMap();
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg){
		
		//System.out.println(n.getDefaultConstructor());
		//System.out.println(n.getMembers().get(0));
		if (!n.isInterface())
			map.put(0, n.getName().toString());
		else
			map.put(1, n.getName().toString());
		//else
			//interface_name = n.getNameAsString();

			
	}
		
	public Map<Integer, String> getClassName(){
		return map;
	}
	

}
