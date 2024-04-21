package com.rookie.oss.starter.handler;

import cn.hutool.core.lang.UUID;
import com.rookie.oss.starter.core.AbstractOssCore;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author eumenides
 * @description MinIO文件上传Handler
 * @date 2024/4/20
 */
public class MinIoHandler extends AbstractOssCore {
    private MinioClient minioClient;
    public MinIoHandler(String bucketName, MinioClient client) {
        super(bucketName);
        this.minioClient = client;
    }

    @Override
    public String uploadFile(MultipartFile file,String bucket) {

        // 生成随机文件名
        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

    @Override
    public String getFileTmpPath(String fileName,String bucket) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucket)
                        .object(fileName)
                        .expiry(60 * 60 * 24)
                        .build());
    }

    @Override
    public InputStream downloadFile(String fileName, String bucketName) throws Exception{
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }
}
