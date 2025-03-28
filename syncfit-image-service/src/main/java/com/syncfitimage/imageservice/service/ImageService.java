package com.syncfitimage.imageservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    public String uploadWishlistImage(MultipartFile file, String wishlistId) {

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
            // 위시리스트 DB에 이미지 url을 저장
            String fileUrl = "https://" + bucket + ".s3." + bucketRegion + ".amazonaws.com/" + fileName;
            /*
            Optional<Wishlist> target = wishlistRepository.findById(Long.parseLong(wishlistId));
            if (target.isPresent()) {
                Wishlist wishlist = target.get();
                wishlist.setImageUrl(fileUrl);
                wishlistRepository.save(wishlist);
            } else {
                throw new CustomException(ErrorCode.WISHLIST_NOT_FOUND);
            }
             */
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return fileName;
    }

    public byte[] downloadImage(String wishlistId) {
        try {
            // 위시리스트 DB에서 해당하는 이미지 url 가져오기

            // AWS S3 버킷에서 이미지 파일을 다운로드
            String imageUrl = "https://khstestbucket0328.s3.ap-northeast-2.amazonaws.com/d37a5196-6072-4a27-b601-13cafa810d97.png";
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            try (InputStream in = connection.getInputStream()) {
                return in.readAllBytes();
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다.");
        }
    }

    // 파일명을 난수화하기 위해 UUID 를 활용하여 난수를 돌린다.
    public String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    //  "."의 존재 유무만 판단
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
        }
    }
}
