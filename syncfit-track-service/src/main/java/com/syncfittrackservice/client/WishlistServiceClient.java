package com.syncfittrackservice.client;

import com.syncfittrackservice.dto.response.ValidateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="wishlist-service")
public interface WishlistServiceClient {
    // 위시리스트 엔티티를 가져오기 위한 컨트롤러
    @GetMapping("/ownership/{wishListId}")
    ResponseEntity<ValidateResponse> findWishlistById(@PathVariable Long wishListId, @RequestParam Long memberId);

}