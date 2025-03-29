package com.syncfitmemberservice.controller;

import com.syncfitmemberservice.dto.MemberInfoResponse;
import com.syncfitmemberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인한 회원 정보 조회 API
    @GetMapping("/me")
    public MemberInfoResponse memberInfo() {
        return memberService.getMemberInfo();
    }

    // OpenFeign 호출용 API
    @GetMapping("/{memberId}")
    public MemberInfoResponse getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberById(memberId);
    }
}