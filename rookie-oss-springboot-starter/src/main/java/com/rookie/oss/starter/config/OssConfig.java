package com.rookie.oss.starter.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.rookie.oss.starter.core.AbstractOssCore;
import com.rookie.oss.starter.handler.MinIoHandler;
import com.rookie.oss.starter.handler.TencentOssHandler;
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
            case COS:
                COSCredentials cred = new BasicCOSCredentials(properties.getAccessKeyId(), properties.getAccessKeySecret());
                Region region = new Region(properties.getRegion());
                ClientConfig clientConfig = new ClientConfig(region);
                // 这里建议设置使用 https 协议
                // 从 5.6.54 版本开始，默认使用了 https
                clientConfig.setHttpProtocol(HttpProtocol.https);
                // 3 生成 cos 客户端。
                COSClient cosClient = new COSClient(cred, clientConfig);
                return new TencentOssHandler(properties.getBucketName(), cosClient);

            default:
                throw new IllegalStateException("Unsupported OSS type: " + properties.getType());
        }
    }
}
