package com.syncfitmemberservice.entity;

import com.syncfitcommoncore.entity.MemberRole;
import com.syncfitcommonjpa.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String profileImageUrl;

    @Embedded
    private OauthInfo oauthInfo;

    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
