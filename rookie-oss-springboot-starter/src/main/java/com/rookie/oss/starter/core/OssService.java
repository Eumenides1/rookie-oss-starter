package com.rookie.oss.starter.core;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author eumenides
 * @description 文件上传核心接口
 * @date 2024/4/20
 */
public interface OssService<T> {

    /**
     * 上传文件,并返回生成的文件名
     * @param file
     * @param bucket
     * @return
     * @throws IOException
     */
    String uploadFile(MultipartFile file,String bucket) throws IOException;

    /**
     * 返回临时带签名、过期时间一天、GET请求方式的访问URL
     * @param fileName
     * @return
     */
    String getFileTmpPath(String fileName,String bucketName) throws Exception;

    /**
     * 通过文件名下载文件
     * @param fileName
     * @param bucketName
     * @return
     */
    InputStream downloadFile(String fileName, String bucketName) throws Exception;


    List<T>  listBuckets() throws Exception;

}
