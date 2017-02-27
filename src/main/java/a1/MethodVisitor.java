package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Void>{
	List<String> method_list = new ArrayList<String>();
	@Override
	public void visit(MethodDeclaration n, Void arg) {
		// TODO Auto-generated method stub
		method_list.add(n.getNameAsString());
		System.out.println(n.getNameAsString());
		System.out.println("" + n.getDeclarationAsString(true, false, true));
		super.visit(n, arg);
	}
	
	public List<String> get_method_list(){
		return method_list;
	}
	
}
