package com.suven.framework.upload.service;

import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.upload.dto.request.FileInterpretRequestDto;
import com.suven.framework.upload.dto.request.FileOperationRequestDto;
import com.suven.framework.upload.dto.response.FileInterpretResponseDto;
import com.suven.framework.upload.dto.response.FileOperationResponseDto;
import com.suven.framework.upload.vo.request.FileCallbackRequestVo;
import com.suven.framework.upload.vo.request.FileInterpretPageRequestVo;

import java.util.List;

/**
 * SaaS文件业务操作服务接口
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
public interface FileOperationService {

    /**
     * 创建文件操作记录
     * 
     * @param requestDto 操作记录请求DTO
     * @return SaaSFileOperationResponseDto 操作记录响应DTO
     */
    FileOperationResponseDto createOperationRecord(FileOperationRequestDto requestDto);

    /**
     * 查询操作记录详情
     * 
     * @param id 操作记录ID
     * @return SaaSFileOperationResponseDto 操作记录响应DTO
     */
    FileOperationResponseDto getOperationDetail(long id);

    /**
     * 分页查询操作记录列表
     * 
     * @param requestDto 查询请求DTO
     * @param pager 分页参数
     * @return PageResult<SaaSFileOperationResponseDto> 分页结果
     */
    PageResult<FileOperationResponseDto> queryOperationPage(FileOperationRequestDto requestDto, Pager<FileOperationRequestDto> pager);

    /**
     * 更新操作记录状态
     * 
     * @param id 操作记录ID
     * @param status 状态
     * @param progressPercent 进度
     * @param message 消息
     * @return boolean 是否成功
     */
    boolean updateOperationStatus(long id, String status, int progressPercent, String message);

    /**
     * 添加字段映射
     * 
     * @param operationId 操作记录ID
     * @param fieldMappings 字段映射列表
     * @return boolean 是否成功
     */
    boolean addFieldMappings(long operationId, List<FileInterpretRequestDto> fieldMappings);

    /**
     * 创建解释记录
     * 
     * @param requestDto 解释记录请求DTO
     * @return SaaSFileInterpretResponseDto 解释记录响应DTO
     */
    FileInterpretResponseDto createInterpretRecord(FileInterpretRequestDto requestDto);

    /**
     * 查询解释记录详情
     * 
     * @param id 解释记录ID
     * @return SaaSFileInterpretResponseDto 解释记录响应DTO
     */
    FileInterpretResponseDto getInterpretDetail(long id);

    /**
     * 分页查询解释记录列表
     * 
     * @param operationId 操作记录ID
     * @param pager 分页参数
     * @return PageResult<SaaSFileInterpretResponseDto> 分页结果
     */
    PageResult<FileInterpretResponseDto> queryInterpretPage(long operationId, Pager<FileInterpretPageRequestVo> pager);

    /**
     * 更新解释记录
     * 
     * @param requestDto 解释记录请求DTO
     * @return boolean 是否成功
     */
    boolean updateInterpretRecord(FileInterpretRequestDto requestDto);

    /**
     * 业务回调-处理结果回写
     * 
     * @param callbackRequestVo 回调请求VO
     * @return boolean 是否成功
     */
    boolean handleBusinessCallback(FileCallbackRequestVo callbackRequestVo);

    /**
     * 查询待处理的解释记录（用于业务方分批获取）
     * 
     * @param operationId 操作记录ID
     * @param status 状态
     * @param pager 分页参数
     * @return PageResult<SaaSFileInterpretResponseDto> 分页结果
     */
    PageResult<FileInterpretResponseDto> queryPendingInterpretRecords(long operationId, String status, Pager<FileInterpretPageRequestVo> pager);

    /**
     * 获取解释记录通过业务唯一码
     * 
     * @param businessUniqueCode 业务唯一码
     * @return List<SaaSFileInterpretResponseDto> 解释记录列表
     */
    List<FileInterpretResponseDto> getInterpretRecordsByBusinessCode(String businessUniqueCode);

    /**
     * 删除操作记录
     * 
     * @param id 操作记录ID
     * @return boolean 是否成功
     */
    boolean deleteOperationRecord(long id);

    /**
     * 删除解释记录
     * 
     * @param id 解释记录ID
     * @return boolean 是否成功
     */
    boolean deleteInterpretRecord(long id);

    /**
     * 按业务唯一码分页查询解释记录列表
     *
     * @param requestVo 解释结果分页查询请求VO（包含 businessUniqueCode 与分页参数）
     * @return PageResult<SaaSFileInterpretResponseDto> 分页结果
     */
    PageResult<FileInterpretResponseDto> pageQueryInterpretByBusiness(FileInterpretPageRequestVo requestVo);
}
