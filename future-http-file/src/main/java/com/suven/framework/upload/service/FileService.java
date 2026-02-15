package com.suven.framework.upload.service;

import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.upload.dto.request.FileRequestDto;
import com.suven.framework.upload.dto.response.FileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * SaaS平台文件上传下载服务接口
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
public interface FileService {

    /**
     * 上传文件
     * 
     * @param requestDto 文件上传请求参数
     * @param file 上传的文件
     * @return SaaSFileResponseDto 文件上传响应
     */
    FileResponseDto uploadFile(FileRequestDto requestDto, MultipartFile file);

    /**
     * 下载文件
     * 
     * @param requestDto 文件下载请求参数
     * @return SaaSFileResponseDto 文件下载响应
     */
    FileResponseDto downloadFile(FileRequestDto requestDto);

    /**
     * 生成大数据文件(调用第三方接口)
     * 
     * @param requestDto 文件生成请求参数
     * @return SaaSFileResponseDto 文件生成响应
     */
    FileResponseDto generateFile(FileRequestDto requestDto);

    /**
     * 查询文件列表
     * 
     * @param requestDto 文件查询请求参数
     * @return List<SaaSFileResponseDto> 文件列表
     */
    List<FileResponseDto> queryFileList(FileRequestDto requestDto);

    /**
     * 分页查询文件列表
     * 
     * @param requestDto 文件查询请求参数
     * @param pager 分页参数
     * @return PageResult<SaaSFileResponseDto> 分页文件列表
     */
    PageResult<FileResponseDto> queryFilePage(FileRequestDto requestDto, Pager<FileRequestDto> pager);

    /**
     * 获取文件详情
     * 
     * @param fileUploadStorageId 文件存储ID
     * @return SaaSFileResponseDto 文件详情
     */
    FileResponseDto getFileDetail(long fileUploadStorageId);

    /**
     * 删除文件
     * 
     * @param fileUploadStorageId 文件存储ID
     * @return boolean 删除结果
     */
    boolean deleteFile(long fileUploadStorageId);

    /**
     * 批量删除文件
     * 
     * @param fileUploadStorageIds 文件存储ID列表
     * @return int 删除数量
     */
    int deleteFiles(List<Long> fileUploadStorageIds);

    /**
     * 根据ID列表获取文件列表
     * 
     * @param idList 文件存储ID列表
     * @return List<SaaSFileResponseDto> 文件列表
     */
    List<FileResponseDto> getFileListByIdList(List<Long> idList);

    /**
     * 生成临时访问URL
     * 
     * @param fileUploadStorageId 文件存储ID
     * @param expirationTime 有效期(秒)
     * @return String 临时访问URL
     */
    String generateTempUrl(long fileUploadStorageId, int expirationTime);
}
