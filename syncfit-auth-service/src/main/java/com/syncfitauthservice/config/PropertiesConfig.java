package com.syncfitauthservice.config;

import com.syncfitauthservice.properties.JwtProperties;
import com.syncfitauthservice.properties.KakaoProperties;
import com.syncfitauthservice.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        KakaoProperties.class,
        JwtProperties.class,
        RedisProperties.class
})
@Configuration
public class PropertiesConfig {
}