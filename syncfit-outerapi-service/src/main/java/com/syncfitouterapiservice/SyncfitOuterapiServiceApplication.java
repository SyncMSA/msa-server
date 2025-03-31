package com.syncfitouterapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommonjpa", "com.syncfitouterapiservice"})
public class SyncfitOuterapiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncfitOuterapiServiceApplication.class, args);
	}

}
