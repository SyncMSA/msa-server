package com.syncfitmemberservice.service;

import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import com.syncfitcommonjpa.util.MemberUtil;
import com.syncfitmemberservice.dto.MemberInfoResponse;
import com.syncfitmemberservice.entity.Member;
import com.syncfitmemberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberUtil memberContextUtil;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo() {
        Member member = memberRepository.findById(memberContextUtil.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResponse.from(member);
    }
}