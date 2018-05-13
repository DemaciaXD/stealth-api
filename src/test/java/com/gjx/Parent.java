package com.gjx;

public class Parent {
	
	static int a = 0;
	
	static {
		System.out.println("父类静态代码块,a= " + a);
	}
	
	{
		a = 1;
		System.out.println("父类代码块,a=" + a);
	}

	public Parent() {
		System.out.println("父类构造,a=" + a);
	}
}
