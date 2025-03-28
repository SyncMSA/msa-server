package com.syncfitimage.imageservice.controller;

import com.syncfitimage.imageservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    Environment env;
    ImageService imageService;

    @Autowired
    public ImageController(Environment env, ImageService imageService) {
        this.env = env;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestPart("wishlistId") String wishlistId,
                                              @RequestPart("files") MultipartFile file) {
        return ResponseEntity.ok((imageService.uploadWishlistImage(file, wishlistId)));
    }


    @GetMapping
    public ResponseEntity<byte[]> downloadImage(@RequestParam("wishlistId") String wishlistId) {
        byte[] imageData = imageService.downloadImage(wishlistId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.set("Content-Disposition", "attachment; filename=image.jpg");

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

}
