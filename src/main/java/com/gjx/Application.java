package com.gjx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) 启动忽略不连数据库
@MapperScan("com.gjx.dao")
public class Application extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/**
	 * 需要把web项目打成war包部署到外部tomcat运行时需要改变启动方式
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	  return builder.sources(Application.class);
	}

}
