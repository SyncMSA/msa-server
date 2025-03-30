package com.syncfitauthservice.controller;

import com.syncfitauthservice.dto.request.AuthCodeRequest;
import com.syncfitauthservice.dto.request.RefreshTokenRequest;
import com.syncfitauthservice.dto.response.SocialLoginResponse;
import com.syncfitauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/social-login")
    public SocialLoginResponse memberSocialLogin(@RequestBody AuthCodeRequest request) {
        return authService.socialLoginMember(request);
    }

    @PostMapping("/refresh")
    public SocialLoginResponse refreshToken(@RequestBody RefreshTokenRequest request){
        return authService.refreshToken(request);
    }
}