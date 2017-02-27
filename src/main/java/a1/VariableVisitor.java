package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableVisitor extends VoidVisitorAdapter<Void>{
	
	List<VariableDeclarator> variable_list ;
	@Override
	public void visit(VariableDeclarationExpr n, Void arg){
		super.visit(n, arg);
		
		System.out.println(n.getVariables());
		//System.out.println(n.getModifiers());
		for(VariableDeclarator var: variable_list)
			System.out.println(var.getNameAsString());
		
	}
	public List<VariableDeclarator> get_variable_list(){
		
		return variable_list;
		
	}
}
