package a1;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InterfaceVisitor extends VoidVisitorAdapter<Void> {
	NodeList<ClassOrInterfaceType> interface_list = new NodeList<ClassOrInterfaceType>();
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n,Void arg){
		super.visit(n, arg);
		NodeList<ClassOrInterfaceType> interface_list = n.getImplementedTypes();
		
		for(int i=0; i<interface_list.size(); i++)
			System.out.println(interface_list.get(i).getName());
	}
		
	public NodeList<ClassOrInterfaceType> get_extended_class_list(){
		return interface_list;
	}

}
