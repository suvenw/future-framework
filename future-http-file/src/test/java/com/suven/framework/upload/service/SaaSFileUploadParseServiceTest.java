package com.suven.framework.upload.service;

import com.suven.framework.upload.AbstractUnitTest;
import com.suven.framework.upload.dto.response.SaaSFileParseResultDto;
import com.suven.framework.upload.repository.SaaSFileInterpretRecordRepository;
import com.suven.framework.upload.repository.SaaSFileUploadRepository;
import com.suven.framework.upload.service.impl.SaaSFileUploadParseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * SaaS 文件上传解析服务测试
 * ===============================
 *
 * 测试文件上传和解析的完整流程：
 * 1. 文件上传并解析
 * 2. 解释记录管理
 * 3. 回调处理
 * 4. 数据回写
 *
 * @author suven
 * @version 1.0.0
 * @since 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class SaaSFileUploadParseServiceTest extends AbstractUnitTest {

    @InjectMocks
    private SaaSFileUploadParseServiceImpl fileUploadParseService;

    @Mock
    private SaaSFileUploadRepository fileUploadRepository;

    @Mock
    private SaaSFileInterpretRecordRepository interpretRecordRepository;

    @Mock
    private SaaSFileParseServiceImpl fileParseService;

    private SaaSCompanyBusinessFunction businessFunction;
    private SaaSFileFieldMapping fieldMapping;
    private SaaSFileUpload fileUpload;
    private SaaSFileParseResultDto parseResult;

    @BeforeEach
    void setUp() {
        logTestStart("SaaSFileUploadParseServiceTest");

        // 初始化业务功能配置
        businessFunction = createBusinessFunction();

        // 初始化字段映射
        fieldMapping = createFieldMapping();

        // 初始化文件上传记录
        fileUpload = createFileUpload();

        // 初始化解析结果
        parseResult = createParseResult();
    }

    @Test
    @DisplayName("测试：上传文件并解析 - 完整流程")
    void testUploadAndParse() {
        logStep("测试上传文件并解析完整流程");

        // given
        Long fileUploadId = 1L;
        when(fileUploadRepository.findById(fileUploadId)).thenReturn(Optional.of(fileUpload));
        when(fileParseService.parseWithMapping(any(), any(), any(), any())).thenReturn(parseResult);
        when(interpretRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(fileUploadRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        SaaSFileUpload result = fileUploadParseService.parseUploadedFile(fileUploadId);

        // then
        assertNotNull(result);
        assertEquals("COMPLETED", result.getInterpretStatus());
        verify(fileParseService).parseWithMapping(any(), any(), any(), any());
        verify(interpretRecordRepository, atLeast(1)).save(any());
        verify(fileUploadRepository).save(any());

        logResult("上传解析", "成功");
    }

    @Test
    @DisplayName("测试：根据文件上传ID查询解释记录")
    void testGetInterpretRecordsByFileUploadId() {
        logStep("测试查询解释记录");

        // given
        Long fileUploadId = 1L;
        List<SaaSFileInterpretRecord> records = createInterpretRecords();
        when(interpretRecordRepository.findByFileUploadId(fileUploadId)).thenReturn(records);

        // when
        List<SaaSFileInterpretRecord> result = fileUploadParseService.getInterpretRecordsByFileUploadId(fileUploadId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        logResult("解释记录查询", "成功");
    }

    @Test
    @DisplayName("测试：分页查询业务解释记录")
    void testPageQueryInterpretByBusiness() {
        logStep("测试分页查询业务解释记录");

        // given
        String businessUniqueCode = "BIZ_001";
        int pageNo = 1;
        int pageSize = 10;
        List<SaaSFileInterpretRecord> records = createInterpretRecords();

        when(interpretRecordRepository.findByBusinessUniqueCode(businessUniqueCode, pageNo, pageSize))
                .thenReturn(records);

        // when
        List<SaaSFileInterpretRecord> result = fileUploadParseService.pageQueryInterpretByBusiness(
                businessUniqueCode, pageNo, pageSize);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        logResult("分页查询", "成功");
    }

    @Test
    @DisplayName("测试：回写处理结果")
    void testWriteBackProcessResult() {
        logStep("测试回写处理结果");

        // given
        Long recordId = 1L;
        String processResult = "PROCESSED";
        String processRemark = "数据已处理";
        SaaSFileInterpretRecord record = createInterpretRecord(1L);

        when(interpretRecordRepository.findById(recordId)).thenReturn(Optional.of(record));
        when(interpretRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        boolean result = fileUploadParseService.writeBackProcessResult(
                recordId, processResult, processRemark);

        // then
        assertTrue(result);
        assertEquals(processResult, record.getBusinessProcessResult());
        assertEquals(processRemark, record.getBusinessProcessRemark());
        assertNotNull(record.getBusinessProcessTime());

        verify(interpretRecordRepository).save(record);

        logResult("回写处理", "成功");
    }

    @Test
    @DisplayName("测试：查询待解释记录")
    void testQueryPendingInterpretRecords() {
        logStep("测试查询待解释记录");

        // given
        List<SaaSFileInterpretRecord> pendingRecords = createInterpretRecords();
        when(interpretRecordRepository.findByCheckStatus(0)).thenReturn(pendingRecords);

        // when
        List<SaaSFileInterpretRecord> result = fileUploadParseService.queryPendingInterpretRecords();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        logResult("待解释记录查询", "成功");
    }

    @Test
    @DisplayName("测试：获取文件上传记录")
    void testGetFileUpload() {
        logStep("测试获取文件上传记录");

        // given
        Long fileUploadId = 1L;
        when(fileUploadRepository.findById(fileUploadId)).thenReturn(Optional.of(fileUpload));

        // when
        Optional<SaaSFileUpload> result = fileUploadParseService.getFileUpload(fileUploadId);

        // then
        assertTrue(result.isPresent());
        assertEquals(fileUploadId, result.get().getId());

        logResult("获取上传记录", "成功");
    }

    @Test
    @DisplayName("测试：检查是否支持解析")
    void testIsParseSupported() {
        logStep("测试检查解析支持");

        // when & then
        assertTrue(fileUploadParseService.isParseSupported("xlsx"));
        assertTrue(fileUploadParseService.isParseSupported("xls"));
        assertTrue(fileUploadParseService.isParseSupported("csv"));
        assertFalse(fileUploadParseService.isParseSupported("txt"));

        logResult("解析支持检查", "通过");
    }

    // ========== 测试数据工厂方法 ==========

    private SaaSCompanyBusinessFunction createBusinessFunction() {
        return SaaSCompanyBusinessFunction.builder()
                .id(1L)
                .tenantId(100L)
                .companyId(200L)
                .platformType("SaaS")
                .businessType("IMPORT")
                .functionType("FILE_IMPORT")
                .businessUniqueCode("BIZ_001")
                .callbackUrl("https://business.example.com/callback")
                .queryUrl("https://business.example.com/query")
                .accessMethod("POST")
                .build();
    }

    private SaaSFileFieldMapping createFieldMapping() {
        return SaaSFileFieldMapping.builder()
                .id(1L)
                .businessFunctionId(1L)
                .englishName("name")
                .chineseName("姓名")
                .sortOrder(1)
                .fieldType("java.lang.String")
                .required(true)
                .build();
    }

    private SaaSFileUpload createFileUpload() {
        return SaaSFileUpload.builder()
                .id(1L)
                .tenantId(100L)
                .companyId(200L)
                .businessFunctionId(1L)
                .fieldMappingId(1L)
                .businessUniqueCode("BIZ_001")
                .uploadBatchNo("BATCH_20240101_001")
                .fileSourceName("test.xlsx")
                .fileType("xlsx")
                .interpretFlag(true)
                .interpretStatus("PENDING")
                .build();
    }

    private SaaSFileParseResultDto createParseResult() {
        return SaaSFileParseResultDto.builder()
                .success(true)
                .totalRows(100)
                .successRows(98)
                .failRows(2)
                .skipRows(0)
                .message("解析完成")
                .build();
    }

    private List<SaaSFileInterpretRecord> createInterpretRecords() {
        List<SaaSFileInterpretRecord> records = new ArrayList<>();
        records.add(createInterpretRecord(1L));
        records.add(createInterpretRecord(2L));
        return records;
    }

    private SaaSFileInterpretRecord createInterpretRecord(Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John");
        data.put("age", 25);

        return SaaSFileInterpretRecord.builder()
                .id(id)
                .fileUploadId(1L)
                .businessUniqueCode("BIZ_001")
                .rowNumber(id.intValue())
                .dataJson("{\"name\":\"John\",\"age\":25}")
                .rawDataJson("John,25")
                .checkStatus(0)
                .errorCode(null)
                .errorMessage(null)
                .createTime(LocalDateTime.now())
                .build();
    }
}
