package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ExtendedClassVisitor extends VoidVisitorAdapter<Void>{
	NodeList<ClassOrInterfaceType> extended_class_list = new NodeList<ClassOrInterfaceType>();
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n,Void arg){
		super.visit(n, arg);
		NodeList<ClassOrInterfaceType> extended_class_list = n.getExtendedTypes();
		
		//for(int i=0; i<extended_class_list.size(); i++)
			//System.out.println(extended_class_list.get(i).getName());
	}
		
	public NodeList<ClassOrInterfaceType> get_extended_class_list(){
		return extended_class_list;
	}
}
