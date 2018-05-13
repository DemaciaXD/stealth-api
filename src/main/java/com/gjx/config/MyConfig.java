package com.gjx.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置文件类
 * @author Guo
 *
 */

@Component
@ConfigurationProperties(prefix="small")
@PropertySource(value="classpath:config/config.yml")
@Data
public class MyConfig {
	private String appid;
	private String secret;
	private String grant_type;
	
}
