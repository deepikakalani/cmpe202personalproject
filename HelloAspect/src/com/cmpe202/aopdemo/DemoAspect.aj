package com.cmpe202.aopdemo;

import org.aspectj.lang.JoinPoint;
public aspect DemoAspect {

	pointcut pointcut1():
		call(void com.cmpe202.aopdemo.AOPDemo.*(..));
	
	after(): pointcut1(){
		System.out.println("After demo aspect method1");
	}
	
	before(): pointcut1(){
		System.out.println("Before method1");
	}
	
	/*pointcut pointcut2():
		call(void com.cmpe202.aopdemo.AOPDemo.method2(*));
	
	after(): pointcut2(){
		System.out.println("After demo aspect method2");
	}
	
	before(): pointcut2(){
		System.out.println("Before method2");
	}*/
}
