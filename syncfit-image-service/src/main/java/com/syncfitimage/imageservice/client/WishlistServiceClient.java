package com.syncfitimage.imageservice.client;

import com.syncfitimage.imageservice.error.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="syncfit-wishlist-service", configuration = FeignErrorDecoder.class)
public interface WishlistServiceClient {
    @GetMapping("/syncfit-wishlist-service/wishlist/{wishlistId}/wishlist-image-url")
    String getWishlistImageUrl(@PathVariable Long wishlistId);
}