package com.rookie.oss.starter.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
