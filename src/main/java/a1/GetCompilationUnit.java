package a1;
import com.github.javaparser.JavaParser;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GetCompilationUnit {
	public static void main(String[] args) throws Exception
	{		
			
			
			GetCompilationUnit gc = new GetCompilationUnit();
			List<String> java_files = gc.extractJavaFiles("/home/deepika/Projects/cmpe202/umlparser/uml-parser-test-2/");
			
			for(int i = 0; i<java_files.size(); i++){
				FileInputStream infile = new FileInputStream(java_files.get(i));
				System.out.println(java_files.get(i)+ "in order method variable extended class and Inherited class");
				CompilationUnit c = JavaParser.parse(infile);
				//System.out.println(c.toString());
				new MethodVisitor().visit(c, null);
				new VariableVisitor().visit(c, null);
				new getExtendedClass().visit(c, null);
				new getInterfaces().visit(c, null);
			}
	}	
	
	public List<String> extractJavaFiles(String path){
		//List<String> result = new ArrayList<String>();
		List<String> java_files = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		for(File f: files){
			if(f.isFile() && (f.toString()).endsWith(".java"))
				java_files.add(f.toString());
		}
		
		/*for(String s:java_files){
			for(String ret:s.split("/"))
				if (ret.endsWith(".java"))
					result.add(ret);	
		}
		for(int i =0; i<result.size(); i++){
			System.out.println(result.get(i));
		}*/
		return java_files;
		
	}
	
	public static class MethodVisitor extends VoidVisitorAdapter<Void>{
	
		@Override
		public void visit(MethodDeclaration n, Void arg) {
			// TODO Auto-generated method stub
			System.out.println(n.getNameAsString());
			System.out.println("" + n.getDeclarationAsString(true, false, true));
			super.visit(n, arg);
		}
		
	}
	
	public static class VariableVisitor extends VoidVisitorAdapter<Void>{
		@Override
		public void visit(VariableDeclarator n, Void arg){
			super.visit(n, arg);
			System.out.println(n.getName() + "\n" +n.getType());
			
			
		}
	}
	
	
	public static class getExtendedClass extends VoidVisitorAdapter<Void>{
		@Override
		public void visit(ClassOrInterfaceDeclaration n,Void arg){
			super.visit(n, arg);
			NodeList<ClassOrInterfaceType> list = n.getExtendedTypes();
			for(int i=0; i<list.size(); i++)
				System.out.println(list.get(i).getName());
		}
			
	}
	
	public static class getInterfaces extends VoidVisitorAdapter<Void>{
		
		@Override
		public void visit(ClassOrInterfaceDeclaration n,Void arg){
			super.visit(n, arg);
			NodeList<ClassOrInterfaceType> list = n.getImplementedTypes();
			for(int i=0; i<list.size(); i++)
				System.out.println(list.get(i).getName());
			
		}
	} 
	
	
	
	
	

}