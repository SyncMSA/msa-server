package com.syncfitauthservice.service;

import com.syncfitauthservice.dto.request.AuthCodeRequest;
import com.syncfitauthservice.dto.request.RefreshTokenRequest;
import com.syncfitauthservice.dto.response.IdTokenResponse;
import com.syncfitauthservice.dto.response.SocialLoginResponse;
import com.syncfitauthservice.entity.Member;
import com.syncfitauthservice.entity.OauthInfo;
import com.syncfitauthservice.repository.MemberRepository;
import com.syncfitcommoncore.dto.AccessTokenDto;
import com.syncfitcommoncore.dto.RefreshTokenDto;
import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoService kakaoService;
    private final JwtTokenService jwtTokenService;
    private final IdTokenVerifier idTokenVerifier;
    private final MemberRepository memberRepository;

    public SocialLoginResponse socialLoginMember(AuthCodeRequest request) {
        IdTokenResponse response = kakaoService.getIdToken(request.code());
        String idToken = response.id_token();
        OidcUser oidcUser = idTokenVerifier.getOidcUser(idToken);

        Optional<Member> optionalMember = findByOidcUser(oidcUser);
        Member member = optionalMember.orElseGet(() -> saveMember(oidcUser));

        return getLoginResponse(member);
    }

    public SocialLoginResponse refreshToken(RefreshTokenRequest request){
        RefreshTokenDto oldRefreshTokenDto = jwtTokenService.validateRefreshToken(request.refreshToken());

        if (oldRefreshTokenDto == null){
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        }

        RefreshTokenDto newRefreshTokenDto = jwtTokenService.refreshRefreshToken(oldRefreshTokenDto);
        AccessTokenDto accessTokenDto = jwtTokenService.refreshAccessToken(getMember(newRefreshTokenDto));
        return new SocialLoginResponse(accessTokenDto.accessTokenValue(), newRefreshTokenDto.refreshTokenValue());
    }

    private SocialLoginResponse getLoginResponse(Member member) {
        String accessToken = jwtTokenService.createAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtTokenService.createRefreshToken(member.getId());
        return SocialLoginResponse.of(accessToken, refreshToken);
    }

    private Optional<Member> findByOidcUser(OidcUser oidcUser) {
        OauthInfo oauthInfo = extractOauthInfo(oidcUser);
        return memberRepository.findByOauthInfo(oauthInfo);
    }

    private Member saveMember(OidcUser oidcUser) {
        OauthInfo oauthInfo = extractOauthInfo(oidcUser);
        Member member = Member.createMember(oidcUser.getNickName(), oidcUser.getPicture(), oauthInfo);
        return memberRepository.save(member);
    }

    private OauthInfo extractOauthInfo(OidcUser oidcUser) {
        return OauthInfo.createOauthInfo(
                oidcUser.getSubject(), oidcUser.getIssuer().toString());
    }

    private Member getMember(RefreshTokenDto refreshTokenDto){
        return memberRepository.findById(refreshTokenDto.memberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}