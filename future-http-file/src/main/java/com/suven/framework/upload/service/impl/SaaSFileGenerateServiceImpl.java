package com.suven.framework.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.file.client.FileClient;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.request.SaaSFileDataQueryRequestDto;
import com.suven.framework.upload.entity.DataSourceModuleName;
import com.suven.framework.upload.entity.SaaSCompanyBusinessFunction;
import com.suven.framework.upload.entity.SaaSFileDownloadRecord;
import com.suven.framework.upload.entity.SaaSFileFieldMapping;
import com.suven.framework.upload.mapper.SaaSFileDownloadRecordMapper;
import com.suven.framework.upload.repository.CompanyBusinessFunctionRepository;
import com.suven.framework.upload.repository.FileDownloadRecordRepository;
import com.suven.framework.upload.repository.SaaSFileFieldMappingRepository;
import com.suven.framework.upload.service.SaaSFileGenerateService;
import com.suven.framework.upload.util.http.OkHttpClients;
import com.suven.framework.upload.vo.request.SaaSFileDownloadQueryRequestVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * SaaS文件生成与下载服务实现
 * 
 * 功能：实现批量大数据文件生成，包括调用业务接口获取数据、生成XLS/CSV文件、上传到文件存储服务等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class SaaSFileGenerateServiceImpl implements SaaSFileGenerateService {

    private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private FileDownloadRecordRepository downloadRecordRepository;

    @Autowired
    private SaaSFileFieldMappingRepository fieldMappingRepository;

    @Autowired
    private CompanyBusinessFunctionRepository businessFunctionRepository;

    @Autowired
    private SaaSFileDownloadRecordMapper downloadRecordMapper;

    /** 文件存储客户端 */
    @Autowired(required = false)
    private FileClient fileClient;

    /** 文件存储域名 */
    @Value("${file.storage.domain:https://file.example.com}")
    private String storageDomain;

    /** 文件存储基础路径 */
    @Value("${file.storage.basePath:saas/export}")
    private String storageBasePath;

    /**
     * 申请生成文件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaaSFileDownloadRecord applyGenerateFile(SaaSFileDataQueryRequestDto requestDto) {
        log.info("申请生成文件: businessUniqueCode={}", requestDto.getBusinessUniqueCode());

        // 验证参数
        validateRequestDto(requestDto);

        // 创建下载记录
        SaaSFileDownloadRecord record = createDownloadRecord(requestDto);

        // 异步生成文件
        asyncGenerateFileInternal(record.getId(), requestDto);

        return record;
    }

    /**
     * 同步生成文件
     */
    @Override
    public String syncGenerateFile(SaaSFileDataQueryRequestDto requestDto) {
        log.info("同步生成文件: businessUniqueCode={}", requestDto.getBusinessUniqueCode());

        // 验证参数
        validateRequestDto(requestDto);

        // 创建下载记录
        SaaSFileDownloadRecord record = createDownloadRecord(requestDto);

        try {
            // 执行生成
            executeGenerate(record.getId(), requestDto);

            // 返回下载URL
            return getDownloadUrl(record.getId());

        } catch (Exception e) {
            log.error("同步生成文件失败", e);
            updateRecordError(record.getId(), "生成失败: " + e.getMessage());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "文件生成失败: " + e.getMessage());
        }
    }

    /**
     * 异步生成文件
     */
    @Override
    public long asyncGenerateFile(SaaSFileDataQueryRequestDto requestDto, String callbackUrl) {
        log.info("异步生成文件: businessUniqueCode={}", requestDto.getBusinessUniqueCode());

        // 验证参数
        validateRequestDto(requestDto);

        // 创建下载记录
        SaaSFileDownloadRecord record = createDownloadRecord(requestDto);

        // 异步执行生成
        asyncGenerateFileInternal(record.getId(), requestDto);

        return record.getId();
    }

    /**
     * 异步执行文件生成
     */
    @Async("fileGenerateExecutor")
    public CompletableFuture<Void> asyncGenerateFileInternal(long downloadRecordId, 
                                                            SaaSFileDataQueryRequestDto requestDto) {
        try {
            executeGenerate(downloadRecordId, requestDto);
        } catch (Exception e) {
            log.error("异步生成文件失败: downloadRecordId={}", downloadRecordId, e);
            updateRecordError(downloadRecordId, "生成失败: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 执行文件生成
     */
    private void executeGenerate(long downloadRecordId, SaaSFileDataQueryRequestDto requestDto) {
        log.info("执行文件生成: downloadRecordId={}", downloadRecordId);

        SaaSFileDownloadRecord record = downloadRecordRepository.getById(downloadRecordId);
        if (record == null) {
            throw new RuntimeException("下载记录不存在: " + downloadRecordId);
        }

        try {
            // 更新状态为生成中
            record.setGenerateStatus("PROCESSING");
            record.setProgressPercent(10);
            record.setGenerateStartTime(LocalDateTime.now());
            downloadRecordRepository.updateById(record);

            // 获取字段映射
            List<SaaSFileFieldMapping> fieldMappings = getFieldMappings(requestDto.getBusinessUniqueCode());

            // 获取业务配置
            SaaSCompanyBusinessFunction function = getBusinessFunction(requestDto.getBusinessUniqueCode());
            if (function != null) {
                record.setBusinessFunctionId(function.getId());
                record.setBusinessType(function.getBusinessName());
                downloadRecordRepository.updateById(record);
            }

            // 分页查询数据
            List<Map<String, Object>> allData = queryDataByPages(requestDto);

            record.setProgressPercent(50);
            record.setTotalQueryCount(allData.size());
            record.setQueryTimeMs(System.currentTimeMillis() - record.getGenerateStartTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
            downloadRecordRepository.updateById(record);

            // 转换字段（英文转中文）
            List<Map<String, Object>> convertedData = convertFieldsToChinese(allData, fieldMappings);

            // 生成文件
            record.setProgressPercent(70);
            downloadRecordRepository.updateById(record);

            // 生成表头
            List<String> headers = generateChineseHeaders(fieldMappings);

            // 生成文件
            String fileName = generateFileName(requestDto.getFileNamePrefix(), requestDto.getFileType());
            String filePath = generateFile(fileName, headers, convertedData, requestDto.getFileType());

            // 计算文件信息
            File file = new File(filePath);
            long fileSize = file.length();
            String fileMd5 = calculateFileMd5(file);

            // 更新记录
            record.setFileName(fileName);
            record.setFileType(requestDto.getFileType());
            record.setFileSize(fileSize);
            record.setFilePath(filePath);
            record.setFileMd5(fileMd5);
            record.setGenerateStatus("COMPLETED");
            record.setProgressPercent(100);
            record.setExportCount(convertedData.size());
            record.setSuccessCount(convertedData.size());
            record.setMessage("文件生成成功");
            record.setGenerateEndTime(LocalDateTime.now());
            record.setGenerateTimeMs(System.currentTimeMillis() - record.getGenerateStartTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
            downloadRecordRepository.updateById(record);

            log.info("文件生成成功: downloadRecordId={}, fileName={}", downloadRecordId, fileName);

        } catch (Exception e) {
            log.error("文件生成异常: downloadRecordId={}", downloadRecordId, e);
            updateRecordError(downloadRecordId, "生成失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件生成状态
     */
    @Override
    public SaaSFileDownloadRecord getGenerateStatus(long downloadRecordId) {
        return downloadRecordRepository.getById(downloadRecordId);
    }

    /**
     * 获取文件下载URL
     */
    @Override
    public String getDownloadUrl(long downloadRecordId) {
        SaaSFileDownloadRecord record = downloadRecordRepository.getById(downloadRecordId);
        if (record == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "下载记录不存在");
        }

        if (!"COMPLETED".equals(record.getGenerateStatus())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "文件尚未生成完成");
        }

        // 这里应该调用文件存储服务获取下载URL
        // 暂时返回记录中的文件访问URL
        if (ObjectTrue.isNotEmpty(record.getFileAccessUrl())) {
            return record.getFileAccessUrl();
        }

        // 如果没有下载URL，需要上传到文件存储服务
        String accessUrl = uploadToFileStorage(record);
        if (ObjectTrue.isNotEmpty(accessUrl)) {
            record.setFileAccessUrl(accessUrl);
            record.setAccessExpireTime(LocalDateTime.now().plusDays(7)); // 默认7天过期
            downloadRecordRepository.updateById(record);
            return accessUrl;
        }

        throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "无法获取下载URL");
    }

    /**
     * 分页查询下载记录
     */
    @Override
    public PageResult<SaaSFileDownloadRecord> pageQueryDownloadRecords(
            SaaSFileDownloadQueryRequestVo requestVo, Pager pager) {
        log.info("分页查询下载记录: pageNo={}, pageSize={}", pager.getPageNo(), pager.getPageSize());

        LambdaQueryWrapper<SaaSFileDownloadRecord> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(requestVo.getBusinessUniqueCode())) {
            queryWrapper.eq(SaaSFileDownloadRecord::getBusinessUniqueCode, requestVo.getBusinessUniqueCode());
        }
        if (requestVo.getBusinessFunctionId() != null && requestVo.getBusinessFunctionId() > 0) {
            queryWrapper.eq(SaaSFileDownloadRecord::getBusinessFunctionId, requestVo.getBusinessFunctionId());
        }
        if (StringUtils.isNotBlank(requestVo.getGenerateStatus())) {
            queryWrapper.eq(SaaSFileDownloadRecord::getGenerateStatus, requestVo.getGenerateStatus());
        }
        if (StringUtils.isNotBlank(requestVo.getFileType())) {
            queryWrapper.eq(SaaSFileDownloadRecord::getFileType, requestVo.getFileType());
        }
        if (StringUtils.isNotBlank(requestVo.getFileName())) {
            queryWrapper.like(SaaSFileDownloadRecord::getFileName, requestVo.getFileName());
        }
        queryWrapper.eq(SaaSFileDownloadRecord::getDeleted, 0);
        queryWrapper.orderByDesc(SaaSFileDownloadRecord::getId);

        PageResult<SaaSFileDownloadRecord> result = new PageResult<>();
        result.setTotal(pager.getTotal());
        result.setList(downloadRecordMapper.selectPage(
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pager.getPageNo(), pager.getPageSize()),
                queryWrapper
        ).getRecords());

        return result;
    }

    /**
     * 根据业务唯一码查询下载记录
     */
    @Override
    public PageResult<SaaSFileDownloadRecord> queryByBusinessCode(String businessUniqueCode, Pager pager) {
        log.info("根据业务唯一码查询下载记录: businessUniqueCode={}", businessUniqueCode);

        if (StringUtils.isBlank(businessUniqueCode)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        List<SaaSFileDownloadRecord> records = downloadRecordRepository.getByBusinessUniqueCode(businessUniqueCode);

        PageResult<SaaSFileDownloadRecord> result = new PageResult<>();
        result.setTotal(records.size());

        int pageNo = pager.getPageNo() <= 0 ? 1 : pager.getPageNo();
        int pageSize = pager.getPageSize() <= 0 ? 20 : pager.getPageSize();
        int fromIndex = (pageNo - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, records.size());

        if (fromIndex < records.size()) {
            result.setList(records.subList(fromIndex, toIndex));
        } else {
            result.setList(new ArrayList<>());
        }

        return result;
    }

    /**
     * 获取文件生成进度
     */
    @Override
    public Map<String, Object> getGenerateProgress(long downloadRecordId) {
        SaaSFileDownloadRecord record = downloadRecordRepository.getById(downloadRecordId);
        if (record == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "下载记录不存在");
        }

        Map<String, Object> progress = new HashMap<>();
        progress.put("downloadRecordId", downloadRecordId);
        progress.put("generateStatus", record.getGenerateStatus());
        progress.put("progressPercent", record.getProgressPercent());
        progress.put("message", record.getMessage());
        progress.put("exportCount", record.getExportCount());
        progress.put("successCount", record.getSuccessCount());
        progress.put("failCount", record.getFailCount());
        progress.put("queryTimeMs", record.getQueryTimeMs());
        progress.put("generateTimeMs", record.getGenerateTimeMs());
        progress.put("generateStartTime", record.getGenerateStartTime() != null ? 
                record.getGenerateStartTime().format(DATETIME_FORMATTER) : null);
        progress.put("generateEndTime", record.getGenerateEndTime() != null ? 
                record.getGenerateEndTime().format(DATETIME_FORMATTER) : null);

        return progress;
    }

    /**
     * 取消文件生成任务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelGenerate(long downloadRecordId) {
        log.info("取消文件生成任务: downloadRecordId={}", downloadRecordId);

        SaaSFileDownloadRecord record = downloadRecordRepository.getById(downloadRecordId);
        if (record == null) {
            return false;
        }

        if ("COMPLETED".equals(record.getGenerateStatus()) || "FAILED".equals(record.getGenerateStatus())) {
            log.warn("无法取消已完成或已失败的记录");
            return false;
        }

        record.setGenerateStatus("CANCELLED");
        record.setMessage("用户取消生成");
        record.setModifyDate(LocalDateTime.now());

        return downloadRecordRepository.updateById(record);
    }

    /**
     * 删除下载记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDownloadRecord(long downloadRecordId) {
        log.info("删除下载记录: downloadRecordId={}", downloadRecordId);

        SaaSFileDownloadRecord record = downloadRecordRepository.getById(downloadRecordId);
        if (record == null) {
            return false;
        }

        record.setDeleted(1);
        record.setModifyDate(LocalDateTime.now());

        return downloadRecordRepository.updateById(record);
    }

    /**
     * 下载次数+1
     */
    @Override
    public boolean incrementDownloadCount(long downloadRecordId) {
        return downloadRecordRepository.incrementDownloadCount(downloadRecordId);
    }

    /**
     * 根据字段映射将英文字段转换为中文
     */
    @Override
    public List<Map<String, Object>> convertFieldsToChinese(
            List<Map<String, Object>> dataList,
            List<SaaSFileFieldMapping> fieldMappings) {

        if (ObjectTrue.isEmpty(dataList) || ObjectTrue.isEmpty(fieldMappings)) {
            return dataList;
        }

        // 创建字段映射表
        Map<String, String> fieldNameMapping = new HashMap<>();
        for (SaaSFileFieldMapping mapping : fieldMappings) {
            fieldNameMapping.put(mapping.getFieldEnglishName(), mapping.getFieldChineseName());
        }

        // 转换每行数据
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : dataList) {
            Map<String, Object> newRow = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String chineseName = fieldNameMapping.getOrDefault(entry.getKey(), entry.getKey());
                newRow.put(chineseName, entry.getValue());
            }
            result.add(newRow);
        }

        return result;
    }

    /**
     * 生成表头（中文）
     */
    @Override
    public List<String> generateChineseHeaders(List<SaaSFileFieldMapping> fieldMappings) {
        if (ObjectTrue.isEmpty(fieldMappings)) {
            return new ArrayList<>();
        }

        List<String> headers = new ArrayList<>();
        // 按排编号排序
        fieldMappings.sort(Comparator.comparingInt(SaaSFileFieldMapping::getSortOrder));

        for (SaaSFileFieldMapping mapping : fieldMappings) {
            headers.add(mapping.getFieldChineseName());
        }

        return headers;
    }

    /**
     * 获取字段映射
     */
    @Override
    public List<SaaSFileFieldMapping> getFieldMappings(String businessUniqueCode) {
        if (StringUtils.isBlank(businessUniqueCode)) {
            return new ArrayList<>();
        }

        // 获取业务功能配置
        SaaSCompanyBusinessFunction function = getBusinessFunction(businessUniqueCode);
        if (function == null) {
            return new ArrayList<>();
        }

        // 获取字段映射
        return fieldMappingRepository.getByBusinessFunctionId(function.getId());
    }

    // ==================== 私有方法 ====================

    /**
     * 验证请求参数
     */
    private void validateRequestDto(SaaSFileDataQueryRequestDto requestDto) {
        if (StringUtils.isBlank(requestDto.getBusinessUniqueCode())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }
        if (StringUtils.isBlank(requestDto.getDataQueryUrl())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "数据查询URL不能为空");
        }
    }

    /**
     * 创建下载记录
     */
    private SaaSFileDownloadRecord createDownloadRecord(SaaSFileDataQueryRequestDto requestDto) {
        SaaSFileDownloadRecord record = new SaaSFileDownloadRecord();
        record.setBusinessUniqueCode(requestDto.getBusinessUniqueCode());
        record.setDownloadUserId(requestDto.getDownloadUserId());
        record.setDownloadUserName(requestDto.getDownloadUserName());
        record.setDownloadDeptId(requestDto.getDownloadDeptId());
        record.setDownloadDeptName(requestDto.getDownloadDeptName());
        record.setQueryCondition(JSON.toJSONString(requestDto.getQueryParams()));
        record.setGenerateStatus("PENDING");
        record.setProgressPercent(0);
        record.setDownloadCount(0);
        record.setFileType(requestDto.getFileType() != null ? requestDto.getFileType().toUpperCase() : "XLSX");
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());

        return downloadRecordRepository.saveId(record);
    }

    /**
     * 获取业务功能配置
     */
    private SaaSCompanyBusinessFunction getBusinessFunction(String businessUniqueCode) {
        try {
            LambdaQueryWrapper<SaaSCompanyBusinessFunction> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SaaSCompanyBusinessFunction::getBusinessUniqueCode, businessUniqueCode);
            queryWrapper.eq(SaaSCompanyBusinessFunction::getStatus, "ACTIVE");
            queryWrapper.eq(SaaSCompanyBusinessFunction::getDeleted, 0);
            return businessFunctionRepository.getOne(queryWrapper);
        } catch (Exception e) {
            log.warn("获取业务功能配置失败: {}", businessUniqueCode, e);
            return null;
        }
    }

    /**
     * 分页查询数据
     */
    private List<Map<String, Object>> queryDataByPages(SaaSFileDataQueryRequestDto requestDto) {
        List<Map<String, Object>> allData = new ArrayList<>();
        int pageNo = 1;
        int pageSize = requestDto.getPageSize() > 0 ? requestDto.getPageSize() : 1000;
        int timeoutMs = requestDto.getTimeoutMs() > 0 ? requestDto.getTimeoutMs() : 30000;

        while (true) {
            try {
                // 构建请求参数
                Map<String, Object> params = new HashMap<>();
                if (requestDto.getQueryParams() != null) {
                    params.putAll(requestDto.getQueryParams());
                }
                params.put("pageNo", pageNo);
                params.put("pageSize", pageSize);

                // 调用业务接口
                String jsonResult = httpPost(requestDto.getDataQueryUrl(), params, timeoutMs);
                if (StringUtils.isBlank(jsonResult)) {
                    log.warn("查询接口返回空数据: url={}, pageNo={}", requestDto.getDataQueryUrl(), pageNo);
                    break;
                }

                // 解析返回数据
                JSONObject result = JSON.parseObject(jsonResult);
                JSONArray dataArray = result.getJSONArray("list");
                if (dataArray == null || dataArray.isEmpty()) {
                    break;
                }

                for (int i = 0; i < dataArray.size(); i++) {
                    allData.add(dataArray.getJSONObject(i));
                }

                // 检查是否还有更多数据
                Integer total = result.getInteger("total");
                Integer totalPages = result.getInteger("totalPages");
                if (total == null || totalPages == null) {
                    // 如果没有分页信息，假设只有一页
                    break;
                }

                if (pageNo >= totalPages) {
                    break;
                }

                pageNo++;

            } catch (Exception e) {
                log.error("分页查询数据失败: pageNo={}", pageNo, e);
                break;
            }
        }

        log.info("分页查询数据完成: totalRows={}", allData.size());
        return allData;
    }

    /**
     * HTTP POST请求
     */
    private String httpPost(String url, Map<String, Object> params, int timeoutMs) {
        try {
            String jsonParams = JSON.toJSONString(params);
            Response response = OkHttpClients.postHttp(url, jsonParams);

            if (response == null || !response.isSuccessful()) {
                log.warn("HTTP请求失败: url={}, code={}", url, response != null ? response.code() : "null");
                return null;
            }

            return response.body() != null ? response.body().string() : null;
        } catch (Exception e) {
            log.error("HTTP请求异常: url={}", url, e);
            return null;
        }
    }

    /**
     * 生成文件名
     */
    private String generateFileName(String prefix, String fileType) {
        String type = StringUtils.isNotBlank(fileType) ? fileType.toUpperCase() : "XLSX";
        String timeStr = LocalDateTime.now().format(FILE_NAME_FORMATTER);
        String filePrefix = StringUtils.isNotBlank(prefix) ? prefix : "EXPORT";
        return filePrefix + "_" + timeStr + "." + type.toLowerCase();
    }

    /**
     * 生成文件
     */
    private String generateFile(String fileName, List<String> headers, 
                                List<Map<String, Object>> dataList, String fileType) {
        String type = StringUtils.isNotBlank(fileType) ? fileType.toUpperCase() : "XLSX";

        // 生成临时文件
        String tempDir = System.getProperty("java.io.tmpdir");
        String filePath = tempDir + File.separator + fileName;

        try {
            if ("CSV".equals(type)) {
                return generateCSVFile(filePath, headers, dataList);
            } else {
                return generateXLSFile(filePath, headers, dataList);
            }
        } catch (Exception e) {
            log.error("生成文件失败: filePath={}", filePath, e);
            throw new RuntimeException("生成文件失败: " + e.getMessage());
        }
    }

    /**
     * 生成CSV文件
     */
    private String generateCSVFile(String filePath, List<String> headers, 
                                   List<Map<String, Object>> dataList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            // 写入BOM，解决Excel打开CSV乱码问题
            writer.write('\uFEFF');

            // 写入表头
            writer.write(String.join(",", headers));
            writer.newLine();

            // 写入数据
            for (Map<String, Object> row : dataList) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    Object value = row.get(header);
                    String strValue = value != null ? escapeCSV(value.toString()) : "";
                    values.add(strValue);
                }
                writer.write(String.join(",", values));
                writer.newLine();
            }
        }

        log.info("CSV文件生成完成: filePath={}, rows={}", filePath, dataList.size());
        return filePath;
    }

    /**
     * 转义CSV值
     */
    private String escapeCSV(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * 生成XLS文件（使用CSV格式，Excel可以打开）
     */
    private String generateXLSFile(String filePath, List<String> headers, 
                                   List<Map<String, Object>> dataList) throws IOException {
        // 简单实现：生成CSV文件，将扩展名改为.xls
        // 实际生产环境应使用Apache POI或EasyExcel
        String csvPath = filePath.replace(".xlsx", ".csv").replace(".xls", ".csv");
        generateCSVFile(csvPath, headers, dataList);

        // 重命名为xls扩展名
        File csvFile = new File(csvPath);
        File xlsFile = new File(filePath);
        csvFile.renameTo(xlsFile);

        return filePath;
    }

    /**
     * 计算文件MD5
     */
    private String calculateFileMd5(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    md.update(buffer, 0, read);
                }
                byte[] digest = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("计算文件MD5失败: filePath={}", file.getAbsolutePath(), e);
            return "";
        }
    }

    /**
     * 上传到文件存储服务
     * 
     * 功能：将生成的文件上传到OSS/S3等文件存储服务，并返回访问URL
     */
    private String uploadToFileStorage(SaaSFileDownloadRecord record) {
        log.info("上传文件到存储服务: filePath={}", record.getFilePath());

        // 检查文件是否存在
        File file = new File(record.getFilePath());
        if (!file.exists() || !file.isFile()) {
            log.error("文件不存在: filePath={}", record.getFilePath());
            return null;
        }

        try {
            // 读取文件内容
            byte[] fileContent = readFileToBytes(file);
            
            // 生成OSS存储路径
            String ossPath = generateOssPath(record);
            
            // 获取文件类型
            String fileType = record.getFileType();
            
            // 调用FileClient上传文件
            if (fileClient != null) {
                String accessUrl = fileClient.upload(fileContent, ossPath, fileType);
                log.info("文件上传成功: ossPath={}, accessUrl={}", ossPath, accessUrl);
                
                // 更新下载记录中的文件存储信息
                record.setFilePath(ossPath);
                record.setFileMd5(calculateContentMd5(fileContent));
                record.setFileSize(file.length());
                record.setFileAccessUrl(accessUrl);
                record.setAccessExpireTime(LocalDateTime.now().plusDays(7)); // 默认7天过期
                
                // 更新记录
                downloadRecordRepository.updateById(record);
                
                return accessUrl;
            } else {
                log.warn("FileClient未配置，无法上传文件到存储服务");
                // 如果没有配置FileClient，返回本地文件路径
                return storageDomain + "/" + ossPath;
            }
            
        } catch (Exception e) {
            log.error("文件上传失败: filePath={}", record.getFilePath(), e);
            return null;
        }
    }

    /**
     * 生成OSS存储路径
     */
    private String generateOssPath(SaaSFileDownloadRecord record) {
        // 格式: saas/export/{businessCode}/{date}/{filename}
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String businessCode = record.getBusinessUniqueCode();
        if (businessCode == null) {
            businessCode = "default";
        }
        
        // 构建路径
        StringBuilder path = new StringBuilder();
        path.append(storageBasePath);
        path.append("/");
        path.append(businessCode);
        path.append("/");
        path.append(dateStr);
        path.append("/");
        path.append(record.getFileName());
        
        return path.toString();
    }

    /**
     * 读取文件内容为字节数组
     */
    private byte[] readFileToBytes(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }
        return bos.toByteArray();
    }

    /**
     * 计算字节数组的MD5
     */
    private String calculateContentMd5(byte[] content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(content);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5计算失败", e);
            return "";
        }
    }

    /**
     * 更新记录错误信息
     */
    private void updateRecordError(long downloadRecordId, String errorMessage) {
        try {
            SaaSFileDownloadRecord record = downloadRecordRepository.getById(downloadRecordId);
            if (record != null) {
                record.setGenerateStatus("FAILED");
                record.setErrorMessage(errorMessage);
                record.setModifyDate(LocalDateTime.now());
                downloadRecordRepository.updateById(record);
            }
        } catch (Exception e) {
            log.error("更新记录错误信息失败: downloadRecordId={}", downloadRecordId, e);
        }
    }
}
