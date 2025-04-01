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
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
            case 404:
                if (methodKey.contains("uploadImage")) {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            env.getProperty("image-service.exception.file-upload-failed", "파일 업로드 실패"));
                }
                break;
            default:
                String reason = response.reason() != null ? response.reason() : "Unknown error";
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), reason);
        }

        return null;
    }
}
