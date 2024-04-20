package com.rookie.oss.starter.handler;

import cn.hutool.core.lang.UUID;
import com.rookie.oss.starter.core.AbstractOssCore;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Hello World";
    }
}
