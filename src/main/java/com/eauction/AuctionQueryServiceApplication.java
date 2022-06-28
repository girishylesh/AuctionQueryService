package com.eauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
@SpringBootApplication
public class AuctionQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionQueryServiceApplication.class, args);
	}
}
