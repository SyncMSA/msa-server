package com.syncfitauthservice.repository;

import com.syncfitauthservice.entity.Member;
import com.syncfitauthservice.entity.OauthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthInfo(OauthInfo oauthInfo);
}
