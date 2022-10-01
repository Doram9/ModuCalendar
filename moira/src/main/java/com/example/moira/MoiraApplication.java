package com.example.moira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //SpringSecurity 자동 실행 off
public class MoiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoiraApplication.class, args);
	}

}
