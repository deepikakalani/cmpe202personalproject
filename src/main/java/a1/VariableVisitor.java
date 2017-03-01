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
		//System.out.println("****************");
		//System.out.println(n);
		//for(FieldDeclaration var: variable_list)
		//	System.out.println(var);
		
	}
	
	/*@Override
	public void visit(VariableDeclarator n, Void arg){
		super.visit(n, arg);
		System.out.println("here you are");
		List<VariableDeclarator> l = new ArrayList<VariableDeclarator>();
		l.add(n);
		n.getName().toString();
		String field_type = n.getType().toString();
		System.out.println(field_type + " ");
		System.out.print(n.getName().toString());
	}*/
	
	public List<FieldDeclaration> get_variable_list(){
		//for(FieldDeclaration var: variable_list)
		//	System.out.println(var);
		return variable_list;
		
	}
}
