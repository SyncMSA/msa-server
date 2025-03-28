package com.syncfitimage.imageservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    // 환경변수 또는 AWS 설정 파일(~/.aws/credentials)에서 자격증명을 가져옴
    // 테스트용 터미널에 aws cli가 깔려 있고, aws configure을 했기 때문에 정상 작동
    private final S3Client s3Client = S3Client.builder().build();

    @Value("${aws.s3.bucket}")
    private String bucketName;

    /*
    String downloadPath = "C://git-local-repo";

    public static String uploadFile(MultipartFile file) {
        String fileName = "wishlist/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .acl(ObjectCannedACL.PUBLIC_READ)  // 퍼블릭 접근 허용
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getBytes())));

            return getFileUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }
    }

    public byte[] downloadFile(String fileName) {

        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

//        return s3Client.getObject(getRequest).readAllBytes();
        return s3Client.getObject(getRequest, Paths.get(downloadPath));
    }

    private String getFileUrl(String fileName) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
    */
}
