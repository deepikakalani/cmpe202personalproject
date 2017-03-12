package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.description.JavadocInlineTag.Type;

public class VariableVisitor extends VoidVisitorAdapter<Void>{
	
	List<FieldDeclaration> variable_list = new ArrayList<FieldDeclaration>();
	@Override
	public void visit(FieldDeclaration n, Void arg){
		super.visit(n, arg);
		variable_list.add(n);
		
	}
	
	
	
	public List<FieldDeclaration> get_variable_list(){
		//for(FieldDeclaration var: variable_list)
		//	System.out.println(var);
		return variable_list;
		
	}
}
