package com.fuck.test.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class FuckServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuckServerApplication.class, args);
	}
}
