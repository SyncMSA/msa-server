package com.syncfitwishlistservice.application;

import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import com.syncfitcommonjpa.util.MemberUtil;
import com.syncfitwishlistservice.dao.WishlistRepository;
import com.syncfitwishlistservice.domain.Wishlist;
import com.syncfitwishlistservice.dto.response.WishlistImageUrlResponse;
import com.syncfitwishlistservice.dto.response.WishlistInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberUtil memberUtil;
    private final RestTemplate restTemplate;

    @Value("${image-service.url}")
    private String IMAGE_SERVICE_URL;

    public void createWishlist(String title, MultipartFile file, String authHeader) throws IOException {
        Long memberId = memberUtil.getMemberId();

        String imageUrl = uploadImage(file, authHeader);

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

    public void editWishlist(Long wishlistId, String title, MultipartFile file, String authHeader) throws IOException {
        Long memberId = memberUtil.getMemberId();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, memberId);

        if (title != null && !title.isBlank()) {
            wishlist.updateTitle(title);
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = uploadImage(file, authHeader);
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

    public WishlistImageUrlResponse getWishlistImageUrl(Long wishlistId) {
        Wishlist wishlist = findWishlistById(wishlistId);

        return new WishlistImageUrlResponse(wishlist.getImageUrl());
    }

    public String uploadImage(MultipartFile file, String authHeader) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 기존 헤더에서 받은 인증 정보를 사용
        headers.set("Authorization", authHeader);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("files", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename(); // 파일명을 유지해야 함
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<WishlistImageUrlResponse> response =
                restTemplate.postForEntity(IMAGE_SERVICE_URL, requestEntity, WishlistImageUrlResponse.class);

        return Objects.requireNonNull(response.getBody()).imageUrl();
    }
}

