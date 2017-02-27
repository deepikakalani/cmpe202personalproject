package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableVisitor extends VoidVisitorAdapter<Void>{
	
	List<String> variable_list = new ArrayList<String>();
	@Override
	public void visit(VariableDeclarator n, Void arg){
		super.visit(n, arg);
		System.out.println(n.getName() + "\n" +n.getType());
		variable_list.add(n.getName() + "\n" +n.getType());
		
		
	}
	public List<String> get_method_list(){
		return variable_list;
	}
}
