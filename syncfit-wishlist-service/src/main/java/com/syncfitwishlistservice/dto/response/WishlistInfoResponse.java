package com.syncfitwishlistservice.dto.response;


import com.syncfitwishlistservice.domain.Wishlist;

public record WishlistInfoResponse(Long id, String title, String imageUrl) {
    public static WishlistInfoResponse from(Wishlist wishlist) {
        return new WishlistInfoResponse(wishlist.getId(), wishlist.getTitle(), wishlist.getImageUrl());
    }
}