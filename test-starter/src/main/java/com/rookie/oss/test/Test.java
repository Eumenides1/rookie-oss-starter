package com.rookie.oss.test;

import com.rookie.oss.starter.core.AbstractOssCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public String test(@RequestParam("file") MultipartFile file) throws IOException {
        return core.uploadFile(file,"test");
    }

}
