package a1;

import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

//this class is holding metadata per class of given source code
public class ClassData {
	
	
	public Map<String, Map> classcoreData; 
	/*String will have values like "variable", "extended", "implements", "classandinterface" and Map 
	 * will hold theList of maps where each map is a different type of Hashmap.
	 * Ex: for variable_map: map is of <Integer, List ofStrings> 
	 * where 0th position of list has modifier<stored as string>, 1 has type and 2 has variable_name;
	 * 
	 */
	public List<MethodDeclaration> nlist;	//
	public ConstructorDeclaration const_dec;
	
}
