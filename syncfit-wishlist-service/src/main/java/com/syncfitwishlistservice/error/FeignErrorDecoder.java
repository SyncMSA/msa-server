package com.syncfitwishlistservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    Environment env;

    @Autowired
    public FeignErrorDecoder(Environment env) {
        this.env = env;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch(response.status()) {
            case 400:
                break;
            case 404:
                if (methodKey.contains("uploadImage")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                           env.getProperty("image-service.exception.file-upload-failed"));
                } else if (methodKey.contains("storeImageInfo")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                           env.getProperty("image-service.exception.file-info-save-failed"));
                } else if (methodKey.contains("getMember")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                           env.getProperty("member-service.exception.member-not-found"));
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
