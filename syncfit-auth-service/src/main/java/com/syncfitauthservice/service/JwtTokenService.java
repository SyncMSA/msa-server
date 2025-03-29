package com.syncfitauthservice.service;

import com.syncfitauthservice.dto.AccessTokenDto;
import com.syncfitauthservice.dto.RefreshTokenDto;
import com.syncfitauthservice.entity.Member;
import com.syncfitauthservice.entity.MemberRole;
import com.syncfitauthservice.entity.RefreshToken;
import com.syncfitauthservice.repository.RefreshTokenRepository;
import com.syncfitauthservice.util.JwtUtil;
import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createAccessToken(Long memberId, MemberRole memberRole) {
        return jwtUtil.generateAccessToken(memberId, memberRole);
    }

    public String createRefreshToken(Long memberId) {
        String token = jwtUtil.generateRefreshToken(memberId);
        RefreshToken refreshToken =
                RefreshToken.builder()
                        .memberId(memberId)
                        .token(token)
                        .ttl(jwtUtil.getRefreshTokenExpirationTime())
                        .build();
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public RefreshTokenDto refreshRefreshToken(RefreshTokenDto oldRefreshTokenDto){
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findById(oldRefreshTokenDto.memberId())
                        .orElseThrow(() -> new CustomException(ErrorCode.MISSING_JWT_TOKEN));

        RefreshTokenDto refreshTokenDto = jwtUtil.generateRefreshTokenDto(refreshToken.getMemberId());
        refreshToken.updateRefreshToken(refreshTokenDto.refreshTokenValue(), refreshTokenDto.ttl());
        refreshTokenRepository.save(refreshToken);

        return refreshTokenDto;
    }

    public AccessTokenDto refreshAccessToken(Member member){
        return jwtUtil.generateAccessTokenDto(member.getId(), member.getRole());
    }

    public RefreshTokenDto validateRefreshToken(String refreshToken){
        return jwtUtil.parseRefreshToken(refreshToken);
    }
}