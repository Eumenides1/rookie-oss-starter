package com.rookie.oss.starter.config;

import com.rookie.oss.starter.core.AbstractOssCore;
import com.rookie.oss.starter.handler.MinIoHandler;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author eumenides
 * @description OSS 配置类
 * @date 2024/4/20
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfig {

    @Autowired
    private OssProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AbstractOssCore getMinIoClient() {
        if (properties.getType() == null) {
            throw new IllegalStateException("OSS type is not configured");
        }
        switch (properties.getType()) {
            case MINIO:
                MinioClient minioClientent = MinioClient.builder()
                        .endpoint(properties.getEndpoint())
                        .credentials(properties.getAccessKeyId(), properties.getAccessKeySecret())
                        .build();
                return new MinIoHandler(properties.getBucketName(), minioClientent);
            default:
                throw new IllegalStateException("Unsupported OSS type: " + properties.getType());
        }
    }
}
