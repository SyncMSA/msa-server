package com.syncfittrackservice.dto.request;

public record TrackCreateRequest(
        Long wishlistId,
        String artistName,
        String title,
        String albumName,
        String imageUrl) {
}
