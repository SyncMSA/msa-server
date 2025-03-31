package com.syncfitwishlistservice.application;

import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import com.syncfitcommonjpa.util.MemberUtil;
import com.syncfitwishlistservice.client.ImageServiceClient;
import com.syncfitwishlistservice.dao.WishlistRepository;
import com.syncfitwishlistservice.domain.Wishlist;
import com.syncfitwishlistservice.dto.response.WishlistInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ImageServiceClient imageServiceClient;
    private final MemberUtil memberUtil;

    public void createWishlist(String title, MultipartFile file) {
        Long memberId = memberUtil.getMemberId();

        String imageUrl = imageServiceClient.uploadImage(file);

        Wishlist wishlist = wishlistRepository.save(Wishlist.createWishlist(memberId, title, imageUrl));

        new WishlistInfoResponse(wishlist.getId(), wishlist.getTitle(), wishlist.getImageUrl());
    }

    public void deleteWishlist(Long wishlistId) {
        Long memberId = memberUtil.getMemberId();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, memberId);

        wishlistRepository.delete(wishlist);
    }

    public List<WishlistInfoResponse> findAllWishlist() {
        Long memberId = memberUtil.getMemberId();

        return wishlistRepository.findByMemberId(memberId)
                .stream()
                .map(WishlistInfoResponse::from)
                .toList();
    }

    public void editWishlist(Long wishlistId, String title, MultipartFile file) {
        Long memberId = memberUtil.getMemberId();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, memberId);

        if (title != null && !title.isBlank()) {
            wishlist.updateTitle(title);
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageServiceClient.uploadImage(file);
            wishlist.updateImageUrl(imageUrl);
        }
    }

    private void validateOwnership(Wishlist wishlist, Long currentMemberId) {
        if (!wishlist.getMemberId().equals(currentMemberId)) {
            throw new CustomException(ErrorCode.WISHLIST_MEMBER_MISMATCH);
        }
    }

    public Wishlist findWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new CustomException((ErrorCode.WISHLIST_NOT_FOUND)));
    }

    public boolean validateOwnership(Long wishlistId, Long memberId) {
        Wishlist wishlist = findWishlistById(wishlistId);

        try{
            validateOwnership(wishlist, memberId);
        } catch (CustomException e) {
            return false;
        }

        return true;
    }

    public String getWishlistImageUrl(Long wishlistId) {
        Wishlist wishlist = findWishlistById(wishlistId);

        return wishlist.getImageUrl();
    }
}

