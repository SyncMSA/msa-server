package com.syncfitcommoncore.dto;

import com.syncfitcommoncore.entity.MemberRole;

public record AccessTokenDto(Long memberId, MemberRole role, String accessTokenValue) {
    public static AccessTokenDto of(Long memberId, MemberRole role, String accessTokenValue) {
        return new AccessTokenDto(memberId, role, accessTokenValue);
    }
}
