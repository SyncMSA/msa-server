package com.syncfittrackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = {"com.syncfitcommon", "com.syncfittrackservice"})
@EnableFeignClients(basePackages = {"com.syncfittrackservice.client"})
public class SyncfitTrackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncfitTrackServiceApplication.class, args);
    }

}

