package com.rookie.oss.starter.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author eumenides
 * @description OSS操作核心抽象类
 * @date 2024/4/20
 */
public abstract class AbstractOssCore implements OssService{

    protected String bucketName;

    public AbstractOssCore(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public abstract String uploadFile(MultipartFile file,String bucket) throws IOException;

    @Override
    public abstract String getFileTmpPath(String fileName,String bucketName) throws Exception;

    @Override
    public abstract InputStream downloadFile(String fileName, String bucketName) throws Exception;
}
