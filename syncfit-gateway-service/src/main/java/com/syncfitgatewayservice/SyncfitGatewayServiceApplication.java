package com.syncfitgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommoncore", "com.syncfitgatewayservice"})
public class SyncfitGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncfitGatewayServiceApplication.class, args);
	}

}
