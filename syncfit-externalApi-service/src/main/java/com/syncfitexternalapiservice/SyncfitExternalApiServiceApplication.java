package com.syncfitexternalapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommonjpa", "com.syncfitexternalapiservice"})
public class SyncfitExternalApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncfitExternalApiServiceApplication.class, args);
	}

}
