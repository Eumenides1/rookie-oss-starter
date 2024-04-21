package com.rookie.oss.test;

import com.qcloud.cos.model.Bucket;
import com.rookie.oss.starter.core.AbstractOssCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/20
 */
@RestController
public class Test {

    @Autowired
    private AbstractOssCore core;

    @PostMapping("/upload")
    public String test(@RequestParam("file") MultipartFile file, @RequestParam("bucketName")String bucketName) throws Exception {
        String fileName = core.uploadFile(file, bucketName);
        return core.getFileTmpPath(fileName, bucketName);
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("fileName") String fileName, @RequestParam("bucketName")String bucketName) throws Exception {
        InputStream inputStream = core.downloadFile(fileName, bucketName);
        InputStreamResource resource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @GetMapping("listBuckets")
    public List<Bucket> bucketList() throws Exception {
        return core.listBuckets();
    }

}
