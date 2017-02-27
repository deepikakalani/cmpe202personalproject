package a1;
import com.github.javaparser.JavaParser;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.*;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GetCompilationUnit {
	static File file;
	static BufferedWriter bw;

	//File parsed_code = new File();
	
	public List<String> extractJavaFiles(String path){
		//List<String> result = new ArrayList<String>();
		List<String> java_files = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		for(File f: files){
			if(f.isFile() && (f.toString()).endsWith(".java"))
				java_files.add(f.toString());
		}
		

		return java_files;
		
	}
	
	
	
	public static void main(String[] args) throws Exception
	{		
				
			GetCompilationUnit gc = new GetCompilationUnit();
			List<String> java_files = gc.extractJavaFiles("/home/deepika/Projects/cmpe202/umlparser/uml-parser-test-1/");
			String filename = "/home/deepika/parseroutput";
			
			file = new File(filename +".java");
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			for(int i = 0; i<java_files.size(); i++){
				
				ClassProperties classproperties = new ClassProperties(java_files.get(i).toString());
				classproperties.writeToJavaFile(bw);
			}
			bw.close();
			
			
	}	


}