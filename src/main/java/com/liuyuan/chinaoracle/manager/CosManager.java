package com.liuyuan.chinaoracle.manager;

import com.liuyuan.chinaoracle.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * Cos 对象存储操作.
 */
@Component
public class CosManager {
    /**
     * COS客户端配置对象.
     */
    @Resource
    private CosClientConfig cosClientConfig;
    /**
     * COS客户端对象.
     */
    @Resource
    private COSClient cosClient;

    /**
     * 上传对象.
     *
     * @param key           唯一键
     * @param localFilePath 本地文件路径
     * @return PutObjectResult对象
     */
    public PutObjectResult putObject(String key,
                                     String localFilePath) {
        PutObjectRequest putObjectRequest =
            new PutObjectRequest(cosClientConfig.getBucket(), key,
                new File(localFilePath));
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传对象.
     *
     * @param key  唯一键
     * @param file 文件
     * @return PutObjectResult对象
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest =
            new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }
}
