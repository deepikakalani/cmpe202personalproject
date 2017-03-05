package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ExtendedClassVisitor extends VoidVisitorAdapter<Void>{
	NodeList<ClassOrInterfaceType> extended_class_list = new NodeList<ClassOrInterfaceType>();
	//List<String> extended_class_list = new ArrayList<String>();
	String class_extended = null;
	@Override
	public void visit(ClassOrInterfaceDeclaration n,Void arg){
		super.visit(n, arg);
		extended_class_list = n.getExtendedTypes();
	}
		
	public String get_extended_class_list(){
		if(extended_class_list.size()!=0)
			return extended_class_list.get(0).getNameAsString();
		return "";
	}
}
