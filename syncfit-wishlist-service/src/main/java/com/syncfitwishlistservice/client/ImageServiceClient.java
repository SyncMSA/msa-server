package com.syncfitwishlistservice.client;

import com.syncfitwishlistservice.error.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="image-service", configuration = FeignErrorDecoder.class)
public interface ImageServiceClient {

    @PostMapping("/image")
    String uploadImage(@RequestPart("files") MultipartFile file);
}
