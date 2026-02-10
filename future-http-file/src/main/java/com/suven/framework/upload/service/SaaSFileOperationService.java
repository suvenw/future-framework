package com.suven.framework.upload.service;

import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.upload.dto.request.SaaSFileInterpretRequestDto;
import com.suven.framework.upload.dto.request.SaaSFileOperationRequestDto;
import com.suven.framework.upload.dto.response.SaaSFileInterpretResponseDto;
import com.suven.framework.upload.dto.response.SaaSFileOperationResponseDto;
import com.suven.framework.upload.vo.request.SaaSFileCallbackRequestVo;
import com.suven.framework.upload.vo.request.SaaSFileInterpretPageRequestVo;

import java.util.List;

/**
 * SaaS文件业务操作服务接口
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
public interface SaaSFileOperationService {

    /**
     * 创建文件操作记录
     * 
     * @param requestDto 操作记录请求DTO
     * @return SaaSFileOperationResponseDto 操作记录响应DTO
     */
    SaaSFileOperationResponseDto createOperationRecord(SaaSFileOperationRequestDto requestDto);

    /**
     * 查询操作记录详情
     * 
     * @param id 操作记录ID
     * @return SaaSFileOperationResponseDto 操作记录响应DTO
     */
    SaaSFileOperationResponseDto getOperationDetail(long id);

    /**
     * 分页查询操作记录列表
     * 
     * @param requestDto 查询请求DTO
     * @param pager 分页参数
     * @return PageResult<SaaSFileOperationResponseDto> 分页结果
     */
    PageResult<SaaSFileOperationResponseDto> queryOperationPage(SaaSFileOperationRequestDto requestDto, Pager pager);

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
    boolean addFieldMappings(long operationId, List<SaaSFileInterpretRequestDto> fieldMappings);

    /**
     * 创建解释记录
     * 
     * @param requestDto 解释记录请求DTO
     * @return SaaSFileInterpretResponseDto 解释记录响应DTO
     */
    SaaSFileInterpretResponseDto createInterpretRecord(SaaSFileInterpretRequestDto requestDto);

    /**
     * 查询解释记录详情
     * 
     * @param id 解释记录ID
     * @return SaaSFileInterpretResponseDto 解释记录响应DTO
     */
    SaaSFileInterpretResponseDto getInterpretDetail(long id);

    /**
     * 分页查询解释记录列表
     * 
     * @param operationId 操作记录ID
     * @param pager 分页参数
     * @return PageResult<SaaSFileInterpretResponseDto> 分页结果
     */
    PageResult<SaaSFileInterpretResponseDto> queryInterpretPage(long operationId, Pager pager);

    /**
     * 更新解释记录
     * 
     * @param requestDto 解释记录请求DTO
     * @return boolean 是否成功
     */
    boolean updateInterpretRecord(SaaSFileInterpretRequestDto requestDto);

    /**
     * 业务回调-处理结果回写
     * 
     * @param callbackRequestVo 回调请求VO
     * @return boolean 是否成功
     */
    boolean handleBusinessCallback(SaaSFileCallbackRequestVo callbackRequestVo);

    /**
     * 查询待处理的解释记录（用于业务方分批获取）
     * 
     * @param operationId 操作记录ID
     * @param status 状态
     * @param pager 分页参数
     * @return PageResult<SaaSFileInterpretResponseDto> 分页结果
     */
    PageResult<SaaSFileInterpretResponseDto> queryPendingInterpretRecords(long operationId, String status, Pager pager);

    /**
     * 获取解释记录通过业务唯一码
     * 
     * @param businessUniqueCode 业务唯一码
     * @return List<SaaSFileInterpretResponseDto> 解释记录列表
     */
    List<SaaSFileInterpretResponseDto> getInterpretRecordsByBusinessCode(String businessUniqueCode);

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
    PageResult<SaaSFileInterpretResponseDto> pageQueryInterpretByBusiness(SaaSFileInterpretPageRequestVo requestVo);
}
