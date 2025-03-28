package com.syncfitwishlistservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

    Long memberId;
    String nickname;
    String profileImageUrl;

    @Builder
    public MemberInfoResponse(Long memberId, String nickname, String profileImageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
