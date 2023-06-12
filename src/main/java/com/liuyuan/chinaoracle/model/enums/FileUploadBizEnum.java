package com.liuyuan.chinaoracle.model.enums;

import cn.hutool.core.io.FileUtil;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传业务类型枚举.
 * 文件大小单位: 字节Byte
 * 8bit = 1Byte 1024Byte=1KB
 */
public enum FileUploadBizEnum {

    /**
     * 用户头像文件枚举.
     */
    USER_AVATAR("用户头像", "user_avatar", 2 ^ 20),

    /**
     * 用户仓库文件枚举.
     */
    USER_REPOSITORY("用户仓库", "user_repository", 50 * 2 ^ 20);

    /**
     * 文本信息.
     */
    private final String text;

    /**
     * 值.
     */
    private final String value;

    /**
     * 文件尺寸最大值 -1不设限.
     */
    private final long maxSize;

    FileUploadBizEnum(String text, String value, long maxSize) {
        this.text = text;
        this.value = value;
        this.maxSize = maxSize;
    }

    /**
     * 获取value列表.
     *
     * @return value列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value)
            .collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举.
     *
     * @param value 枚举value
     * @return 文件上传枚举
     */
    public static FileUploadBizEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (FileUploadBizEnum anEnum : FileUploadBizEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 校验文件的大小是否合法.
     *
     * @param file              文件
     * @param fileUploadBizEnum 文件上传枚举
     */
    public void verifyFile(MultipartFile file,
                           FileUploadBizEnum fileUploadBizEnum) {
        // 1.校验文件大小
        if (file.getSize() > fileUploadBizEnum.maxSize) {
            long max = fileUploadBizEnum.maxSize / 2 ^ 20;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,
                "文件大小不能超过" + max + "MB");
        }

        // 2.校验上传文件的业务类型
        switch (fileUploadBizEnum.getValue()) {
            case ("user_avatar"):
                verifyAvatar(file);
                break;
            case ("user_repository"):
                verifyRepository();
                break;
            default:
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "业务类型错误");
        }
    }

    /**
     * 校验头像.
     *
     * @param avatar 头像文件
     */
    private void verifyAvatar(MultipartFile avatar) {

        String suffix = FileUtil.getSuffix(avatar.getOriginalFilename());

        if (!Arrays.asList("jpg", "svg", "png", "webp").contains(suffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }
    }

    /**
     * 校验仓库.
     */
    private void verifyRepository() {

    }

    /**
     * 获取文件枚举的value.
     *
     * @return 字符串
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取文件枚举的文本信息.
     *
     * @return 字符串
     */
    public String getText() {
        return text;
    }

    /**
     * 获取文件的大小限制值.
     *
     * @return 自大尺寸的字节数
     */
    public long getMaxSize() {
        return maxSize;
    }
}
