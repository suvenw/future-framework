package com.suven.framework.upload.service;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.upload.dto.request.FileDataQueryRequestDto;
import com.suven.framework.upload.entity.FileDownloadRecord;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.vo.request.SaaSFileDownloadQueryRequestVo;

import java.util.List;
import java.util.Map;

/**
 * SaaS文件生成与下载服务接口
 * 
 * 功能：实现批量大数据文件生成，包括调用业务接口获取数据、生成XLS/CSV文件、上传到文件存储服务等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
public interface FileGenerateService {

    /**
     * 申请生成文件
     * 
     * @param requestDto 数据查询请求DTO
     * @return FileDownloadRecord 下载记录
     */
    FileDownloadRecord applyGenerateFile(FileDataQueryRequestDto requestDto);

    /**
     * 同步生成文件（直接返回文件下载URL）
     * 
     * @param requestDto 数据查询请求DTO
     * @return String 文件下载URL
     */
    String syncGenerateFile(FileDataQueryRequestDto requestDto);

    /**
     * 异步生成文件（返回任务ID）
     * 
     * @param requestDto 数据查询请求DTO
     * @param callbackUrl 生成完成后的回调URL（可选）
     * @return long 下载记录ID
     */
    long asyncGenerateFile(FileDataQueryRequestDto requestDto, String callbackUrl);

    /**
     * 获取文件生成状态
     * 
     * @param downloadRecordId 下载记录ID
     * @return FileDownloadRecord
     */
    FileDownloadRecord getGenerateStatus(long downloadRecordId);

    /**
     * 获取文件下载URL
     * 
     * @param downloadRecordId 下载记录ID
     * @return String 文件下载URL
     */
    String getDownloadUrl(long downloadRecordId);

    /**
     * 分页查询下载记录列表
     * 
     * @param requestVo 查询请求VO
     * @param pager 分页参数
     * @return PageResult<FileDownloadRecord>
     */
    PageResult<FileDownloadRecord> pageQueryDownloadRecords(
            SaaSFileDownloadQueryRequestVo requestVo, Pager pager);

    /**
     * 根据业务唯一码查询下载记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @param pager 分页参数
     * @return PageResult<FileDownloadRecord>
     */
    PageResult<FileDownloadRecord> queryByBusinessCode(
            String businessUniqueCode, Pager pager);

    /**
     * 获取文件生成进度
     * 
     * @param downloadRecordId 下载记录ID
     * @return Map 进度信息
     */
    Map<String, Object> getGenerateProgress(long downloadRecordId);

    /**
     * 取消文件生成任务
     * 
     * @param downloadRecordId 下载记录ID
     * @return boolean 是否成功
     */
    boolean cancelGenerate(long downloadRecordId);

    /**
     * 删除下载记录
     * 
     * @param downloadRecordId 下载记录ID
     * @return boolean 是否成功
     */
    boolean deleteDownloadRecord(long downloadRecordId);

    /**
     * 下载次数+1
     * 
     * @param downloadRecordId 下载记录ID
     * @return boolean 是否成功
     */
    boolean incrementDownloadCount(long downloadRecordId);

    /**
     * 根据字段映射将英文字段转换为中文
     * 
     * @param dataList 数据列表（字段为英文）
     * @param fieldMappings 字段映射列表
     * @return List<Map<String, Object>> 转换后的数据列表（字段为中文）
     */
    List<Map<String, Object>> convertFieldsToChinese(
            List<JSONObject> dataList,
            List<FileFieldMapping> fieldMappings);

    /**
     * 生成表头（中文）
     * 
     * @param fieldMappings 字段映射列表
     * @return List<String> 中文表头列表
     */
    List<String> generateChineseHeaders(List<FileFieldMapping> fieldMappings);

    /**
     * 获取字段映射
     * 
     * @param businessUniqueCode 业务唯一码
     * @return List<SaaSFileFieldMapping>
     */
    List<FileFieldMapping> getFieldMappings(String businessUniqueCode);
}
