package com.syncfitimage.imageservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import com.syncfitimage.imageservice.client.WishlistServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    // 환경변수 또는 AWS 설정 파일(~/.aws/credentials)에서 자격증명을 가져옴
    // 테스트용 터미널에 aws cli가 깔려 있고, aws configure을 했기 때문에 정상 작동
    //private final S3Client s3Client = S3Client.builder().build();

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String bucketRegion;

    private final AmazonS3 amazonS3;
    private final WishlistServiceClient wishlistServiceClient;

    public String uploadImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            // AWS S3 버킷에 이미지 파일을 업로드
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            // 이미지 url
            String fileUrl = "https://" + bucket + ".s3." + bucketRegion + ".amazonaws.com/" + fileName;
            return fileUrl;

        } catch (IOException e){
            throw new CustomException(ErrorCode.S3_UPLOAD_EXCEPTION);
        }

    }

    public byte[] downloadImage(String wishlistId) {
        try {
            // 위시리스트 DB에서 해당하는 이미지 url 가져오기
            String imageUrl = wishlistServiceClient.getWishlistImageUrl(Long.parseLong(wishlistId));
            // AWS S3 버킷에서 이미지 파일을 다운로드
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            try (InputStream in = connection.getInputStream()) {
                return in.readAllBytes();
            }

        } catch (Exception e) {
            throw new CustomException(ErrorCode.S3_DOWNLOAD_ERROR);
        }
    }

    // 파일명을 난수화하기 위해 UUID 를 활용하여 난수를 돌린다.
    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    //  "."의 존재 유무만 판단
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
            throw new CustomException(ErrorCode.S3_UPLOAD_EXCEPTION);
        }
    }
}