package com.syncfitauthservice.service;

import com.syncfitauthservice.client.KakaoOauthClient;
import com.syncfitauthservice.dto.response.IdTokenResponse;
import com.syncfitauthservice.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoOauthClient kakaoOauthClient;
    private final KakaoProperties kakaoProperties;

    public IdTokenResponse getIdToken(String code) {
        return kakaoOauthClient.getIdToken(
                kakaoProperties.grantType(),
                kakaoProperties.clientId(),
                kakaoProperties.redirectUri(),
                code,
                kakaoProperties.clientSecret());
    }
}