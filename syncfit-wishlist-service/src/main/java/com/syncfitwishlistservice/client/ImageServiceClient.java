package com.syncfitwishlistservice.client;

import com.syncfitwishlistservice.dto.response.WishlistImageUrlResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="image-service")
public interface ImageServiceClient {

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Headers("Content-Type: multipart/form-data")
    ResponseEntity<WishlistImageUrlResponse> uploadImage(@RequestPart("files") MultipartFile file);
}
