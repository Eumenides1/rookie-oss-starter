package com.rookie.oss.starter.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author eumenides
 * @description 文件上传核心接口
 * @date 2024/4/20
 */
public interface OssService {


    String uploadFile(MultipartFile file,String bucket) throws IOException;


}
