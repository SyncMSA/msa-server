package com.syncfitwishlistservice.dto.response;

import lombok.Getter;

public record WishlistImageUrlResponse(String imageUrl) {
    public static WishlistImageUrlResponse from(String imageUrl) {
        return new WishlistImageUrlResponse(imageUrl);
    }
}
