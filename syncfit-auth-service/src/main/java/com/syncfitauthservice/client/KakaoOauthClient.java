package com.syncfitauthservice.client;

import com.syncfitauthservice.dto.response.IdTokenResponse;
import com.syncfitcommonjpa.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoOauthClient",
        url = "https://kauth.kakao.com",
        configuration = FeignConfig.class)
public interface KakaoOauthClient {
    @PostMapping(value = "/oauth/token")
    IdTokenResponse getIdToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret);
}
