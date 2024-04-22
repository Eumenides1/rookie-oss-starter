package com.rookie.oss.starter.handler;

import cn.hutool.core.lang.UUID;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.rookie.oss.starter.common.domain.req.ApiResult;
import com.rookie.oss.starter.core.AbstractOssCore;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/21
 */
public class TencentOssHandler extends AbstractOssCore {

    private COSClient cosClient;
    public TencentOssHandler(String bucketName, COSClient client) {
        super(bucketName);
        this.cosClient = client;
    }

    @Override
    public ApiResult<String> uploadFile(MultipartFile file, String bucket) throws IOException {

        // 生成随机文件名
        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        File tempFile = Files.createTempFile("upload_", "_" + file.getOriginalFilename()).toFile();

        file.transferTo(tempFile);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, tempFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        // 使用完临时文件后确保它被删除
        tempFile.deleteOnExit();

        return ApiResult.success(filename);
    }

    @Override
    public ApiResult<String> getFileTmpPath(String fileName, String bucketName) throws Exception {
        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;

        URL url = cosClient.generatePresignedUrl(bucketName, fileName, expirationDate, method);

        return ApiResult.success(url.toString());
    }

    @Override
    public InputStream downloadFile(String fileName, String bucketName) throws Exception {
        return null;
    }

    @Override
    public ApiResult<List<Bucket>> listBuckets() {
        return ApiResult.success(cosClient.listBuckets());
    }
}
