package com.syncfitwishlistservice.api;

import com.syncfitwishlistservice.application.WishlistService;
import com.syncfitwishlistservice.dto.response.WishlistImageUrlResponse;
import com.syncfitwishlistservice.dto.response.WishlistInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<Void> createWishlist(@RequestPart String title,
                                               @RequestPart MultipartFile file) throws IOException {
        // HttpServletRequest에서 인증 헤더 가져오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");

        // 인증 헤더를 서비스로 전달
        wishlistService.createWishlist(title, file, authHeader);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<WishlistInfoResponse> findAllWishlist(){
        return wishlistService.findAllWishlist();
    }

    @GetMapping("/ownership/{wishlistId}")
    public Boolean findWishlistById(@PathVariable Long wishlistId, @RequestParam Long memberId){
        return wishlistService.validateOwnership(wishlistId, memberId);
    }

    @GetMapping("/wishlist-image-url/{wishlistId}")
    public WishlistImageUrlResponse getWishlistImageUrl(@PathVariable Long wishlistId){
        return wishlistService.getWishlistImageUrl(wishlistId);
    }

    @PatchMapping("/{wishlistId}")
    public ResponseEntity<Void> editWishlist(@PathVariable Long wishlistId,
                                                   @RequestPart(value = "title", required = false) String title,
                                                   @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        // HttpServletRequest에서 인증 헤더 가져오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");

        wishlistService.editWishlist(wishlistId, title, file, authHeader);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long wishlistId){
        wishlistService.deleteWishlist(wishlistId);

        return ResponseEntity.ok().build();
    }
}
