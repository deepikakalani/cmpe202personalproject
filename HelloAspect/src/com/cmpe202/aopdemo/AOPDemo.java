package com.cmpe202.aopdemo;

public class AOPDemo {
	
	public static void main(String[] args){
		
		AOPDemo aopdemo = new AOPDemo();
		aopdemo.method1(10);
		aopdemo.method2("Deepika");
	}
	
	public void method1(int number){
		System.out.println("In method1");
	}
	
	public void method2(String string){
		System.out.println("In method2");
	}
}
