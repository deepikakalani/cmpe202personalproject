

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;

public aspect SeqAspect {

	Stack<String> st = new Stack<String>();
	
	String output="";
	int countLevel = 0;
	pointcut function(): call(* *.*(..)) && !call(* java..*.*(..)) &&
	 !within(SeqAspect);

	pointcut mainfunction(): call(void Main.main(..));
	
	
	pointcut constructors(): !within(SeqAspect) && 
							 execution(*.new(..));
	
	
	
	before(): constructors()
	{
		countLevel++;
	}
	after(): constructors()
	{
		countLevel--;
	}
	
	before(): mainfunction()
	{
		appendString("@startuml");
		appendString("activate Main");
	}
	
	after(): mainfunction()
	{
		appendString("deactivate Main");
		appendString("@enduml");
		//System.out.println(output);
		//todo: generate sequence diagram here
		
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter("sequence.java"));
			bw.write(output);
			bw.close();
		}
		catch(Exception e){}
		
		try {
			Runtime.getRuntime().exec("java -jar plantuml.jar sequence.java");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	before(): function()
	{
		if(countLevel != 0)
			return;
		String className="";
		JoinPoint point = thisJoinPoint;
		if(point != null)
		{
			className = point.getTarget().getClass().getName();
			//System.out.println(className);
		}
		else
			return;
		
		String caller="";
		String callie="";
		String returnType="";
		String methodName="";
		
		if(st.size() == 0)
		{
			caller = "Main";
		}
		else
			caller = st.peek().toString();
		
		callie=className;
		
		String pointString = point.toString();
		String methodDeclaration = pointString.substring(pointString.indexOf('(', 0)+1, pointString.lastIndexOf(')'));
		String[] nodeType = methodDeclaration.split(" ");
		returnType = nodeType[0];
		methodName = nodeType[1];
		String currentSequence = caller+"->"+callie+":"+methodName + ":" + returnType + "\n" + "activate " + callie ;
		appendString(currentSequence);
		
		//System.out.println(currentSequence);
		st.push(callie);
	
	}
	
	private void appendString( String value)
	{
		//b.append(value + System.getProperty("line.separator"));
		//System.out.println(value);
		output = output + value + "\n";
	}
	
	after(): function()
	{
		if(st != null && st.size() != 0)
		{
			String top = st.peek();
			String deactivate = "deactivate " + top;
			appendString(deactivate);
			st.pop();
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
	// TODO Auto-generated method stub
		//if(args.length == 0){
		//	System.out.println("Please enter the folder path to generate sequence diagram");
		//}
		//String path = args[0];
		//System.out.println(path);
		//Runtime.getRuntime().exec("cp "+ path + "/* .");
		
		Main.main(args);
	}
	
}
