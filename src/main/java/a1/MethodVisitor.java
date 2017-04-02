package a1;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Void>{
	
	List<MethodDeclaration> nlist = new ArrayList<MethodDeclaration>();
	@Override
	public void visit(MethodDeclaration n, Void arg) {
		// TODO Auto-generated method stub
//		method_list.add(n.getNameAsString());
//		System.out.println(n.getNameAsString());
//		System.out.println("" + n.getDeclarationAsString(true, false, true));
//		super.visit(n, arg);
		nlist.add(n);
		//for(int i =0;i<nlist.size(); i++)
			//System.out.println(nlist.get(i));
		
	}
	
	public List<MethodDeclaration> get_method_list(){
		return nlist;
	}
	
}
