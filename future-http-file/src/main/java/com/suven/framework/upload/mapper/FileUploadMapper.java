package com.suven.framework.upload.mapper;

import com.suven.framework.upload.entity.FileUpload;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传Mapper
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Mapper
public interface FileUploadMapper extends BaseMapper<FileUpload> {
}
