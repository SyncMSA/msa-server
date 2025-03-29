package com.syncfitmemberservice.dto;

import com.syncfitcommoncore.entity.MemberRole;
import com.syncfitmemberservice.entity.Member;

public record MemberInfoResponse(
        Long memberId,
        String nickname,
        String profileImageUrl,
        MemberRole role) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getRole());
    }
}
