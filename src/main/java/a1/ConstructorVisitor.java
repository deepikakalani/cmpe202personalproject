package a1;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConstructorVisitor extends VoidVisitorAdapter<Void>{

	ConstructorDeclaration n;
	@Override
	public void visit(ConstructorDeclaration n, Void arg){
		this.n = n;
		//System.out.println(n.getDeclarationAsString());
		
	}
	
	public ConstructorDeclaration get_constructor(){
		return n;
	}
}
