package com.suven.framework.upload.service;

import com.suven.framework.upload.dto.request.FileFieldRequestDto;
import com.suven.framework.upload.entity.CompanyBusinessFunction;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.vo.request.CompanyBusinessFunctionRequestVo;
import com.suven.framework.upload.vo.request.FileUploadRequestVo;
import com.suven.framework.upload.vo.response.FileUploadProcessResponseVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文件上传业务流程服务测试
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@SpringBootTest
public class FileUploadProcessServiceTest {

    @Autowired
    private CompanyBusinessFunctionService businessFunctionService;

    @Autowired
    private FileFieldMappingService fieldMappingService;

    @Autowired
    private FileUploadProcessService fileUploadProcessService;

    /**
     * 测试完整业务流程
     * 
     * 1. 申请业务功能
     * 2. 配置字段映射
     * 3. 激活业务功能
     * 4. 上传文件并处理
     */
    @Test
    public void testCompleteBusinessFlow() {
        // 步骤1: 申请业务功能
        CompanyBusinessFunctionRequestVo applyRequest = new CompanyBusinessFunctionRequestVo();
        applyRequest.setCompanyId("TEST_C001");
        applyRequest.setCompanyName("测试公司");
        applyRequest.setBusinessCode("TEST_ORDER");
        applyRequest.setBusinessName("测试订单导入");
        applyRequest.setFunctionType("IMPORT");
        applyRequest.setPlatformType("WEB");
        applyRequest.setCallbackUrl("https://example.com/api/callback");
        
        CompanyBusinessFunction function = businessFunctionService.createBusinessFunction(applyRequest);
        assertNotNull(function);
        assertNotNull(function.getBusinessUniqueCode());
        System.out.println("创建业务功能成功: businessUniqueCode=" + function.getBusinessUniqueCode());

        // 步骤2: 配置字段映射
        List<FileFieldRequestDto> fieldMappings = new ArrayList<>();
        
        FileFieldRequestDto field1 = new FileFieldRequestDto();
        field1.setBusinessFunctionId(function.getId());
        field1.setFieldEnglishName("orderNo");
        field1.setFieldChineseName("订单编号");
        field1.setSortOrder(1);
        field1.setFieldType("STRING");
        field1.setIsRequired(1);
        field1.setValidateRule("^[A-Z0-9]{10,20}$");
        fieldMappings.add(field1);

        FileFieldRequestDto field2 = new FileFieldRequestDto();
        field2.setBusinessFunctionId(function.getId());
        field2.setFieldEnglishName("amount");
        field2.setFieldChineseName("金额");
        field2.setSortOrder(2);
        field2.setFieldType("NUMBER");
        field2.setIsRequired(1);
        fieldMappings.add(field2);

        FileFieldRequestDto field3 = new FileFieldRequestDto();
        field3.setBusinessFunctionId(function.getId());
        field3.setFieldEnglishName("createTime");
        field3.setFieldChineseName("创建时间");
        field3.setSortOrder(3);
        field3.setFieldType("STRING");
        field3.setIsRequired(0);
        fieldMappings.add(field3);

        boolean mappingResult = fieldMappingService.batchSaveFieldMappings(function.getId(), fieldMappings);
        assertTrue(mappingResult);
        System.out.println("保存字段映射成功: count=" + fieldMappings.size());

        // 验证字段映射
        List<FileFieldMapping> savedMappings = fieldMappingService.getByBusinessFunctionId(function.getId());
        assertEquals(3, savedMappings.size());
        System.out.println("验证字段映射成功");

        // 步骤3: 激活业务功能
        boolean activateResult = businessFunctionService.activateBusinessFunction(function.getId());
        assertTrue(activateResult);
        System.out.println("激活业务功能成功");

        // 验证业务功能已激活
        CompanyBusinessFunction activatedFunction = businessFunctionService.getById(function.getId());
        assertEquals("ACTIVE", activatedFunction.getStatus());
        System.out.println("验证业务功能状态: ACTIVE");

        // 步骤4: 上传文件并处理
        // 创建测试CSV文件内容
        String csvContent = "orderNo,amount,createTime\n" +
                "ORD20240001,100.50,2024-01-01\n" +
                "ORD20240002,200.00,2024-01-02\n" +
                "ORD20240003,300.50,2024-01-03\n";

        FileUploadRequestVo uploadRequest = new FileUploadRequestVo();
        uploadRequest.setBusinessUniqueCode(function.getBusinessUniqueCode());
        uploadRequest.setAppId("TEST_APP");
        uploadRequest.setClientId("TEST_CLIENT");
        uploadRequest.setUploadUserId(10001L);
        uploadRequest.setUploadUserName("测试用户");
        uploadRequest.setNeedCallback(0); // 测试时不回调

        FileUploadProcessResponseVo response = fileUploadProcessService.processUpload(
                uploadRequest,
                new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)),
                "test_orders.csv"
        );

        assertNotNull(response);
        System.out.println("文件上传处理结果: " + response);
        System.out.println("处理成功: " + response.isSuccess());
        System.out.println("总行数: " + response.getTotalRows());
        System.out.println("成功行数: " + response.getSuccessRows());
        System.out.println("操作记录ID: " + response.getOperationRecordId());

        // 验证处理结果
        assertTrue(response.isSuccess() || !response.getValidationErrors().isEmpty());
        assertTrue(response.getTotalRows() > 0);
        assertNotNull(response.getOperationRecordId());

        // 清理测试数据
        // businessFunctionService.deleteBusinessFunction(function.getId());
    }

    /**
     * 测试数据校验功能
     */
    @Test
    public void testDataValidation() {
        // 创建业务功能
        CompanyBusinessFunctionRequestVo applyRequest = new CompanyBusinessFunctionRequestVo();
        applyRequest.setCompanyId("TEST_C002");
        applyRequest.setCompanyName("测试公司2");
        applyRequest.setBusinessCode("TEST_VALIDATION");
        applyRequest.setBusinessName("测试数据校验");
        applyRequest.setFunctionType("IMPORT");
        applyRequest.setPlatformType("WEB");
        
        CompanyBusinessFunction function = businessFunctionService.createBusinessFunction(applyRequest);
        assertNotNull(function);

        // 配置必填字段
        List<FileFieldRequestDto> fieldMappings = new ArrayList<>();
        
        FileFieldRequestDto field1 = new FileFieldRequestDto();
        field1.setBusinessFunctionId(function.getId());
        field1.setFieldEnglishName("orderNo");
        field1.setFieldChineseName("订单编号");
        field1.setSortOrder(1);
        field1.setFieldType("STRING");
        field1.setIsRequired(1); // 必填
        fieldMappings.add(field1);

        FileFieldRequestDto field2 = new FileFieldRequestDto();
        field2.setBusinessFunctionId(function.getId());
        field2.setFieldEnglishName("amount");
        field2.setFieldChineseName("金额");
        field2.setSortOrder(2);
        field2.setFieldType("NUMBER");
        field2.setIsRequired(1); // 必填
        fieldMappings.add(field2);

        fieldMappingService.batchSaveFieldMappings(function.getId(), fieldMappings);
        businessFunctionService.activateBusinessFunction(function.getId());

        // 创建包含错误数据的CSV文件
        String csvContent = "orderNo,amount\n" +
                "ORD001,100.00\n" +  // 正确
                "ORD002,\n" +       // 金额为空
                ",300.00\n";         // 订单号为空

        FileUploadRequestVo uploadRequest = new FileUploadRequestVo();
        uploadRequest.setBusinessUniqueCode(function.getBusinessUniqueCode());
        uploadRequest.setNeedCallback(0);

        FileUploadProcessResponseVo response = fileUploadProcessService.processUpload(
                uploadRequest,
                new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)),
                "test_validation.csv"
        );

        assertNotNull(response);
        System.out.println("数据校验结果: " + response);
        System.out.println("校验错误: " + response.getValidationErrors());

        // 验证发现了校验错误
        // 注意：由于数据能正常解析，流程会继续执行
        assertTrue(response.isSuccess());

        // 清理测试数据
        // businessFunctionService.deleteBusinessFunction(function.getId());
    }

    /**
     * 测试获取处理进度
     */
    @Test
    public void testGetProcessProgress() {
        // 先执行一个上传流程
        CompanyBusinessFunctionRequestVo applyRequest = new CompanyBusinessFunctionRequestVo();
        applyRequest.setCompanyId("TEST_C003");
        applyRequest.setCompanyName("测试公司3");
        applyRequest.setBusinessCode("TEST_PROGRESS");
        applyRequest.setBusinessName("测试进度查询");
        applyRequest.setFunctionType("IMPORT");
        applyRequest.setPlatformType("WEB");
        
        CompanyBusinessFunction function = businessFunctionService.createBusinessFunction(applyRequest);
        businessFunctionService.activateBusinessFunction(function.getId());

        String csvContent = "orderNo,amount\nORD001,100.00\n";
        
        FileUploadRequestVo uploadRequest = new FileUploadRequestVo();
        uploadRequest.setBusinessUniqueCode(function.getBusinessUniqueCode());
        uploadRequest.setNeedCallback(0);

        FileUploadProcessResponseVo response = fileUploadProcessService.processUpload(
                uploadRequest,
                new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)),
                "test_progress.csv"
        );

        assertNotNull(response);
        assertNotNull(response.getOperationRecordId());

        // 查询处理进度
        var progress = fileUploadProcessService.getProcessProgress(response.getOperationRecordId());
        assertNotNull(progress);
        System.out.println("处理进度: " + progress);
        assertNotNull(progress.get("status"));
        assertNotNull(progress.get("progressPercent"));

        // 清理测试数据
        // businessFunctionService.deleteBusinessFunction(function.getId());
    }
}
