package com.syncfitwishlistservice.client;

import com.syncfitwishlistservice.error.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="syncfit-image-service", configuration = FeignErrorDecoder.class)
public interface ImageServiceClient {

    @PostMapping("/syncfit-image-service/image")
    String uploadImage(@RequestPart MultipartFile file);

    @PostMapping("/syncfit-image-service/image/info")
    void storeImageInfo(@RequestParam String fileUrl,@RequestParam String wishlistId);
}
