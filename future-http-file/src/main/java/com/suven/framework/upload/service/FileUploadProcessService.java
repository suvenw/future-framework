package com.suven.framework.upload.service;

import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.dto.response.FileParseResultDto;
import com.suven.framework.upload.entity.FileInterpretRecord;
import com.suven.framework.upload.entity.FileOperationRecord;
import com.suven.framework.upload.vo.request.FileUploadRequestVo;
import com.suven.framework.upload.vo.response.FileUploadProcessResponseVo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件上传业务流程服务接口
 * 
 * 功能：实现完整的文件上传业务流程，包括：
 * 1. 业务功能配置校验
 * 2. 文件上传
 * 3. 文件解析
 * 4. 数据映射转换
 * 5. 数据校验
 * 6. 保存解释记录
 * 7. 回调业务服务
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
public interface FileUploadProcessService {

    /**
     * 执行完整的文件上传业务流程
     * 
     * 业务流程：
     * 1. 校验业务功能配置
     * 2. 创建操作记录
     * 3. 解析文件
     * 4. 数据映射转换
     * 5. 数据校验
     * 6. 保存解释记录
     * 7. 回调业务服务（如果需要）
     * 
     * @param requestVo 上传请求VO
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @return FileUploadProcessResponseVo 处理结果
     */
    FileUploadProcessResponseVo processUpload(
            FileUploadRequestVo requestVo,
            InputStream inputStream,
            String fileName);

    /**
     * 校验业务功能配置
     * 
     * @param businessUniqueCode 业务唯一码
     * @return boolean 是否通过校验
     */
    boolean validateBusinessFunction(String businessUniqueCode);

    /**
     * 解析文件
     * 
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param businessFunctionId 业务功能ID
     * @return FileParseResultDto 解析结果
     */
    FileParseResultDto parseFile(
            InputStream inputStream,
            String fileName,
            long businessFunctionId);

    /**
     * 校验数据
     * 
     * @param dataRows 数据行
     * @param businessFunctionId 业务功能ID
     * @return List<String> 错误信息列表
     */
    List<String> validateData(
            List<Map<String, Object>> dataRows,
            long businessFunctionId);

    /**
     * 保存解释记录
     * 
     * @param operationRecordId 操作记录ID
     * @param parseResult 解析结果
     * @param businessUniqueCode 业务唯一码
     * @return List<FileInterpretRecord> 保存的记录列表
     */
    List<FileInterpretRecord> saveInterpretRecords(
            long operationRecordId,
            FileParseResultDto parseResult,
            String businessUniqueCode);

    /**
     * 回调业务服务
     * 
     * @param operationRecordId 操作记录ID
     * @param interpretRecords 解释记录列表
     * @param callbackUrl 回调URL
     * @return boolean 是否成功
     */
    boolean callbackBusinessService(
            long operationRecordId,
            List<FileInterpretRecord> interpretRecords,
            String callbackUrl);

    /**
     * 异步回调业务服务
     * 
     * @param operationRecordId 操作记录ID
     * @param callbackUrl 回调URL
     */
    void asyncCallbackBusinessService(long operationRecordId, String callbackUrl);

    /**
     * 获取处理进度
     * 
     * @param operationRecordId 操作记录ID
     * @return Map<String, Object> 进度信息
     */
    Map<String, Object> getProcessProgress(long operationRecordId);

    /**
     * 重新处理失败的数据
     * 
     * @param operationRecordId 操作记录ID
     * @return FileUploadProcessResponseVo 处理结果
     */
    FileUploadProcessResponseVo retryFailedRecords(long operationRecordId);

    /**
     * 分页查询操作记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @param pager 分页参数
     * @return PageResult<FileOperationRecord> 分页结果
     */
    PageResult<FileOperationRecord> queryOperationRecords(
            String businessUniqueCode,
            Pager<FileOperationRecord> pager);

    /**
     * 获取操作记录详情
     * 
     * @param operationRecordId 操作记录ID
     * @return FileOperationRecord 操作记录
     */
    FileOperationRecord getOperationRecordDetail(long operationRecordId);
}
