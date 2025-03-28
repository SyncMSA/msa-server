package com.syncfitimage.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SyncfitImageserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncfitImageserviceApplication.class, args);
	}

}
