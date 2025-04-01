package com.syncfitimage.imageservice.dto;

import lombok.Getter;

@Getter
public class WishlistImageUrlResponse {
    String imageUrl;

    public WishlistImageUrlResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
