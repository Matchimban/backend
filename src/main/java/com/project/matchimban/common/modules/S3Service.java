package com.project.matchimban.common.modules;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Optional<FileInfo> saveFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        // originalFileName이 null이 나올 수 있음. -> 파일이 없다는 뜻

        String extension = StringUtils.getFilenameExtension(originalFileName);
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String uuid = UUID.randomUUID().toString();

        if (extension == null)
            throw new SVCException(ErrorConstant.FILE_ERROR_UNKNOWN_EXTENSION);

        String savedFileName = today.concat(uuid).concat(".").concat(extension);
        String savedFileUrl = getSavedFileUrl(savedFileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(bucket, savedFileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);
        }

        return Optional.ofNullable(
                FileInfo.builder()
                        .originalFileName(originalFileName)
                        .savedFileUrl(savedFileUrl)
                        .build()
        );
    }

    private String getSavedFileUrl(String savedFileName) {
        return new StringBuilder().append("https://")
                .append(bucket)
                .append(".s3.")
                .append(region)
                .append(".amazonaws.com/")
                .append(savedFileName)
                .toString();
    }

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