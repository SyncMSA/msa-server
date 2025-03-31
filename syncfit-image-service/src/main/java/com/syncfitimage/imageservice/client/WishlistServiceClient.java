package com.syncfitimage.imageservice.client;

import com.syncfitimage.imageservice.error.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="wishlist-service", configuration = FeignErrorDecoder.class)
public interface WishlistServiceClient {
    @GetMapping("/wishlist-image-url/{wishlistId}")
    String getWishlistImageUrl(@PathVariable Long wishlistId);
}