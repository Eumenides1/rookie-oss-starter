# Rookie OSS Starter

支持多种云 OSS 服务以及 Minio 的文件上传Spring Boot Starter;
简单配置，快速上手，支持多种服务，易于扩展

# 当前支持
- MinIO
- 阿里云 OSS
- 腾讯云 OSS

其余待扩展

----

# 使用方式
在项目中引入当前 starter
```xml
<dependency>
    <groupId>com.rookie.starter</groupId>
    <artifactId>rookie-oss-springboot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```
在配置文件中添加对应配置
```yaml
oss:
  type: ${oss.type}
  endpoint: ${oss.endpoint}
  accessKeyId: ${oss.accessKeyId}
  accessKeySecret: ${oss.accessKeySecret}
```
```properties
## 可选值为：minio/tencent/aliyun
oss.type=对应的 oss 服务
oss.endpoint=http://xxx.xxx.xxx.xxxx:9000
oss.accessKeyId=xxxxxxx
oss.accessKeySecret=xxxxxxxx
```
在项目中使用
```java
@RestController
public class Test {

    @Autowired
    private AbstractOssCore core;

    @PostMapping("/upload")
    public String test(@RequestParam("file") MultipartFile file) throws IOException {
        return core.uploadFile(file,"test");
    }

}
```



