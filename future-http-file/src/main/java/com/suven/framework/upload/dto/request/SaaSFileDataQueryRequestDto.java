package com.suven.framework.upload.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * SaaS文件生成数据查询请求DTO
 * 
 * 功能：封装业务方提供的数据查询接口信息
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaaSFileDataQueryRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务唯一码
     */
    private String businessUniqueCode;

    /**
     * 数据查询接口URL
     */
    private String dataQueryUrl;

    /**
     * HTTP请求方法: GET, POST
     */
    private String httpMethod;

    /**
     * 查询条件Map
     */
    private Map<String, Object> queryParams;

    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * 超时时间（毫秒）
     */
    private int timeoutMs;

    /**
     * 是否异步生成
     */
    private boolean asyncGenerate;

    /**
     * 生成文件类型: XLS, XLSX, CSV
     */
    private String fileType;

    /**
     * 自定义文件名称前缀
     */
    private String fileNamePrefix;

    /**
     * 下载用户ID
     */
    private long downloadUserId;

    /**
     * 下载用户名称
     */
    private String downloadUserName;

    /**
     * 下载用户部门ID
     */
    private long downloadDeptId;

    /**
     * 下载用户部门名称
     */
    private String downloadDeptName;

    /**
     * 额外请求头
     */
    private Map<String, String> headers;

    /**
     * 创建查询请求
     * 
     * @param businessUniqueCode 业务唯一码
     * @return SaaSFileDataQueryRequestDto
     */
    public static SaaSFileDataQueryRequestDto create(String businessUniqueCode) {
        return SaaSFileDataQueryRequestDto.builder()
                .businessUniqueCode(businessUniqueCode)
                .httpMethod("POST")
                .pageSize(1000)
                .timeoutMs(30000)
                .asyncGenerate(true)
                .fileType("XLSX")
                .build();
    }

    /**
     * 设置查询条件
     * 
     * @param key 键
     * @param value 值
     * @return this
     */
    public SaaSFileDataQueryRequestDto addQueryParam(String key, Object value) {
        if (this.queryParams == null) {
            this.queryParams = new java.util.HashMap<>();
        }
        this.queryParams.put(key, value);
        return this;
    }

    /**
     * 设置请求头
     * 
     * @param key 键
     * @param value 值
     * @return this
     */
    public SaaSFileDataQueryRequestDto addHeader(String key, String value) {
        if (this.headers == null) {
            this.headers = new java.util.HashMap<>();
        }
        this.headers.put(key, value);
        return this;
    }
}
