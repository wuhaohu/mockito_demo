package com.saic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: create by nadao
 * @version: v1.0
 * @description: com.saic
 * @date:2019-02-21
 */
@SpringBootApplication
@MapperScan("com.saic.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
