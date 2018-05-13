package com.gjx.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

//@Component
public class InitApplicationRunner implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		System.err.println("fist out");
	}
}
