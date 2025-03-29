package com.syncfitimage.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommonjpa", "com.syncfitimage.imageservice"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.syncfitimage.imageservice.client"})
public class SyncfitImageserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncfitImageserviceApplication.class, args);
	}

}
