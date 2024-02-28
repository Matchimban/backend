package com.project.matchimban.common.modules;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String saveFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String savedFileName = UUID.randomUUID().toString()
                .concat(originalFileName.substring(originalFileName.lastIndexOf('.')));



        return savedFileName;
    }

//    public Optional<FileInfo> uploadFile(MultipartFile file) {
//        try {
//            String originalFileName = file.getOriginalFilename();
//            String today = new SimpleDateFormat("yyMMdd").format(new Date()) + "/"; // 230615
//            String extension = originalFileName.substring(originalFileName.lastIndexOf('.')); // .jpg
//            String savedFileName = UUID.randomUUID() + extension;
//            StringBuilder sb = new StringBuilder();
//            sb.append("https://");
//            sb.append(bucket);
//            sb.append(".s3.");
//            sb.append(region);
//            sb.append(".amazonaws.com/");
//            sb.append(today);
//            sb.append(savedFileName);
//            String savedURL = sb.toString();
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//            amazonS3Client.putObject(bucket, today + savedFileName, file.getInputStream(), metadata);
//            FileInfo fileinfo = FileInfo.builder()
//                    .originalFileName(originalFileName)
//                    .savedURL(savedURL)
//                    .build();
//            return Optional.ofNullable(fileinfo);
//
//        } catch (Exception e) {
//            //throw new SVCException(ErrorConstant.S3_ERROR_SAVE_FILE);
//        }
//        return
//    }

    public void getFile(String fileURL) {
        String key = fileURL.substring(60);
    }

    public void removeFile(String fileURL) {
        try {
            String key = fileURL.substring(61);
            amazonS3Client.deleteObject(bucket, key);
        } catch (Exception e) {
            System.out.println("AWS 예외 발생");
        }
    }
}