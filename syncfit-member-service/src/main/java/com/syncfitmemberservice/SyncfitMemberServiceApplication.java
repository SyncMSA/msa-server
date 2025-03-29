package com.syncfitmemberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.syncfitcommonjpa", "com.syncfitcommoncore", "com.syncfitmemberservice"})
public class SyncfitMemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncfitMemberServiceApplication.class, args);
    }

}
