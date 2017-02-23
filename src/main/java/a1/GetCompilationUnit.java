package a1;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GetCompilationUnit {
	public static void main(String[] args) throws Exception
	{
		FileInputStream infile = new FileInputStream("/home/deepika/Projects/cmpe202/umlparser/uml-parser-test-4/Pessimist.java");
		
			//File f = new File("/home/deepika/Projects/cmpe202/umlparser/uml-parser-test-1/A.java");
			CompilationUnit c = JavaParser.parse(infile);
			System.out.println(c.toString());
			new MethodVisitor().visit(c, null);
			
			
		
	}	

	public static class MethodVisitor extends VoidVisitorAdapter<Void>{
	
		@Override
		public void visit(MethodDeclaration n, Void arg) {
			// TODO Auto-generated method stub
			System.out.println("================== \n" + n.getNameAsString());
			super.visit(n, arg);
		}
		
	}

}