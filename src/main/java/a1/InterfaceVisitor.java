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
		interface_list = n.getImplementedTypes();
		
	}
		
	public NodeList<ClassOrInterfaceType> get_interface_class_list(){
		if(interface_list.size() != 0)
			return interface_list;
		return null;
	}

}
