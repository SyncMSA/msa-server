package com.syncfitoutterapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommonjpa", "com.syncfitoutterapiservice"})
public class SyncfitOutterapiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncfitOutterapiServiceApplication.class, args);
	}

}
