package com.gjx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import com.gjx.util.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class SmallRoutineTest {
	@Autowired
	private RedisUtil resRedisUtil;
	
	@Test
	public void contextLoads() {
		resRedisUtil.set("gjx", "gg");
		String string = resRedisUtil.get("gjx");
		System.out.println(string);
	}

}
