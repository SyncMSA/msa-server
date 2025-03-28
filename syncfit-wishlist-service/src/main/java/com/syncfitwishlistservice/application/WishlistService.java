package com.syncfitwishlistservice.application;

import com.syncfitwishlistservice.client.ImageServiceClient;
import com.syncfitwishlistservice.client.MemberServiceClient;
import com.syncfitwishlistservice.dao.WishlistRepository;
import com.syncfitwishlistservice.domain.Wishlist;
import com.syncfitwishlistservice.dto.response.MemberInfoResponse;
import com.syncfitwishlistservice.dto.response.WishlistInfoResponse;
import com.syncfitcommon.error.exception.CustomException;
import com.syncfitcommon.error.exception.ErrorCode;
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
    private final MemberServiceClient memberServiceClient;
    private final ImageServiceClient imageServiceClient;

    public void createWishlist(String title, MultipartFile file) {
        MemberInfoResponse member = memberServiceClient.getMember();

        String imageUrl = imageServiceClient.uploadImage(file);

        Wishlist wishlist = wishlistRepository.save(Wishlist.createWishlist(member.getMemberId(), title, imageUrl));
        imageServiceClient.storeImageInfo(imageUrl, wishlist.getId().toString());

        new WishlistInfoResponse(wishlist.getId(), wishlist.getTitle(), wishlist.getImageUrl());
    }

    public void deleteWishlist(Long wishlistId) {
        MemberInfoResponse currentMember = memberServiceClient.getMember();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, currentMember.getMemberId());

        wishlistRepository.delete(wishlist);
    }

    public List<WishlistInfoResponse> findAllWishlist() {
        MemberInfoResponse currentMember = memberServiceClient.getMember();

        return wishlistRepository.findByMemberId(currentMember.getMemberId())
                .stream()
                .map(WishlistInfoResponse::from)
                .toList();
    }

    public void editWishlist(Long wishlistId, String title, MultipartFile file) {
        MemberInfoResponse currentMember = memberServiceClient.getMember();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, currentMember.getMemberId());

        if (title != null && !title.isBlank()) {
            wishlist.updateTitle(title);
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageServiceClient.uploadImage(file);
            wishlist.updateImageUrl(imageUrl);
            imageServiceClient.storeImageInfo(imageUrl, wishlist.getId().toString());
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
}

