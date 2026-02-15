package com.suven.framework.upload.service;

import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.entity.FileInterpretRecord;
import com.suven.framework.upload.entity.FileUpload;
import com.suven.framework.upload.vo.request.FileInterpretPageRequestVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 文件上传与解析服务接口
 * 
 * 功能：定义文件上传、解析、保存的完整业务流程接口
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
public interface FileUploadParseService {

    /**
     * 上传并解析文件
     * 
     * @param file 上传的文件
     * @param appId 应用ID
     * @param businessUniqueCode 业务唯一码
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return FileUpload 文件上传记录
     */
    FileUpload uploadAndParse(
            MultipartFile file,
            String appId,
            String businessUniqueCode,
            int needCallback,
            String callbackUrl);

    /**
     * 上传并解析文件（带字段映射）
     * 
     * @param file 上传的文件
     * @param appId 应用ID
     * @param businessUniqueCode 业务唯一码
     * @param fieldMappings 字段映射列表
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return FileUpload 文件上传记录
     */
    FileUpload uploadAndParseWithMapping(
            MultipartFile file,
            String appId,
            String businessUniqueCode,
            List<FileFieldMapping> fieldMappings,
            int needCallback,
            String callbackUrl);

    /**
     * 解析已上传的文件
     * 
     * @param fileUploadId 文件上传记录ID
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return  FileInterpretRecord 解释记录
     */
    FileInterpretRecord parseUploadedFile(
            long fileUploadId,
            int needCallback,
            String callbackUrl);

    /**
     * 解析输入流并保存记录
     * 
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param fileUploadId 文件上传记录ID
     * @param businessUniqueCode 业务唯一码
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return  FileInterpretRecord 解释记录
     */
     FileInterpretRecord parseAndSave(
            InputStream inputStream,
            String fileName,
            String fileType,
            long fileUploadId,
            String businessUniqueCode,
            int needCallback,
            String callbackUrl);
    /**
     * 解析输入流并保存记录
     *
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param fileUploadId 文件上传记录ID
     * @param businessUniqueCode 业务唯一码
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return  FileInterpretRecord 解释记录
     */
    FileInterpretRecord parseAndSaveWithMapping(
            InputStream inputStream,
            String fileName,
            String fileType,
            long fileUploadId,
            String businessUniqueCode,
            List<FileFieldMapping> fieldMappings,
            int needCallback,
            String callbackUrl);
    /**
     * 获取文件上传记录
     * 
     * @param id 记录ID
     * @return FileUpload
     */
    FileUpload getFileUpload(long id);

    /**
     * 分页查询文件上传记录
     * 
     * @param requestDto 查询条件
     * @param pager 分页参数
     * @return PageResult<FileUpload>
     */
    PageResult<FileUpload> pageQueryFileUpload(FileUpload requestDto, Pager<FileInterpretRecord> pager);

    /**
     * 获取解释记录
     * 
     * @param id 记录ID
     * @return  FileInterpretRecord
     */
    FileInterpretRecord getInterpretRecord(long id);

    /**
     * 根据文件上传ID获取解释记录列表
     * 
     * @param fileUploadId 文件上传ID
     * @return List< FileInterpretRecord>
     */
    List<FileInterpretRecord> getInterpretRecordsByFileUploadId(long fileUploadId);

    /**
     * 按业务唯一码分页查询解释记录
     * 
     * @param requestVo 查询请求VO
     * @return PageResult< FileInterpretRecord>
     */
    PageResult<FileInterpretRecord> pageQueryInterpretByBusiness( FileInterpretPageRequestVo requestVo);


    /**
     * 回写业务处理结果
     * 
     * @param interpretRecordId 解释记录ID
     * @param businessUniqueCode 业务唯一码
     * @param processStatus 处理状态
     * @param processResult 处理结果
     * @param exceptionInfo 异常信息
     * @return boolean
     */
    boolean writeBackProcessResult(
            long interpretRecordId,
            String businessUniqueCode,
            String processStatus,
            String processResult,
            String exceptionInfo);


    /**
     * 验证文件是否支持解析
     * 
     * @param fileName 文件名
     * @param fileType 文件类型
     * @return boolean
     */
    boolean isParseSupported(String fileName, String fileType);
}
