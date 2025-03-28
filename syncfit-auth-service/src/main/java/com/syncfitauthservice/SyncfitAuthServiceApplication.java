package com.syncfitauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommon", "com.syncfitauthservice"})
@EnableDiscoveryClient
@EnableFeignClients
public class SyncfitAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncfitAuthServiceApplication.class, args);
    }

}
