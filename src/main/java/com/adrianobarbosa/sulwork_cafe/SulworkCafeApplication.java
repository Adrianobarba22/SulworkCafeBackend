package com.adrianobarbosa.sulwork_cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SulworkCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SulworkCafeApplication.class, args);
	}

}
