package com.example.discovery_serivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoverySerivceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoverySerivceApplication.class, args);
	}

}
