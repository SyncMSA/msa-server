package com.syncfittrackservice.client;

//import com.example.orderservice.vo.ResponseCatalog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="SYNCFIT-WISHLIST-SERVICE")
public interface WishlistServiceClient {
    // 위시리스트 엔티티를 가져오기 위한 컨트롤러
//    @GetMapping("/wishlist")
//    List<WishlistInfoResponse> findAllWishlist();
    @GetMapping("/wishlist/ownership/{wishListId}")
    Boolean validateOwnership(@RequestParam Long wishlistId, @RequestParam Long memberId);

}