package com.syncfitimage.imageservice.api;

import com.syncfitimage.imageservice.application.ImageService;
import com.syncfitimage.imageservice.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
//    private final ImageService imageService;
//    private final S3Service s3Service;

    @PostMapping
    public void uploadImage(@RequestPart("wishlistId") String wishlistId,
                            @RequestPart("files") MultipartFile file) {
//        imageService.uploadWishlistImage(file, wishlistId);
//        S3Service.uploadFile(file);
    }

    /*
    @GetMapping
    public ResponseEntity<byte[]> downloadImage(@RequestParam("wishlistId") String wishlistId) {
        byte[] imageData = imageService.downloadImage(wishlistId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.set("Content-Disposition", "attachment; filename=image.jpg");

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
     */
}
