package com.gjx;

public class Son extends Parent {
	static int b = 0;

	static {
		System.out.println("子类静态代码块,b=" + b);
	}
	
	{
		a = 1;
		System.out.println("子类代码块,b=" + b);
	}

	public Son() {
		System.out.println("子类构造,b=" + b);
	}
	
	public static void main(String[] args) {
		new Son();
	}
}
