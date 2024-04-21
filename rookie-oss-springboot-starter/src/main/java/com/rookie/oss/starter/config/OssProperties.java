package com.rookie.oss.starter.config;

import com.rookie.oss.starter.common.enums.OssType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author eumenides
 * @description
 * @date 2024/4/20
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * 存储对象服务器类型
     */
    private OssType type;
    private String endpoint;
    private String bucketName;
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
}
