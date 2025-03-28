package com.syncfitoutterapiservice.infra.config.spotify;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spotify")
public record SpotifyProperties(String clientId, String clientSecret) {
}
