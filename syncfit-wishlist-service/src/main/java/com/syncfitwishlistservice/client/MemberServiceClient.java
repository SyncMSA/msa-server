package com.syncfitwishlistservice.client;

import com.syncfitwishlistservice.dto.response.MemberInfoResponse;
import com.syncfitwishlistservice.error.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="syncfit-member-service", configuration = FeignErrorDecoder.class)
public interface MemberServiceClient {

    @GetMapping("/syncfit-member-service/members/me")
    MemberInfoResponse getMember();
}