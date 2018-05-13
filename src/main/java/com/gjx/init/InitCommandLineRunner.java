package com.gjx.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		System.err.println("fist out ..");
	}
}
