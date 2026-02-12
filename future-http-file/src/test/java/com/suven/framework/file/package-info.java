/**
 * File 模块测试包
 * ===============================
 *
 * 采用 Spring Cloud 测试架构，提供文件存储和预览的全面测试支持
 *
 * 测试覆盖范围：
 * 1. 文件存储客户端测试
 *    ├─ OSSFileClientTest
 *    ├─ S3FileClientTest
 *    ├─ FastDFSClientTest
 *    ├─ SFTPFileClientTest
 *    └─ LocalFileClientTest
 *
 * 2. Excel 在线预览测试
 *    ├─ LuckysheetPreviewServiceTest
 *    ├─ LuckysheetCellDataTest
 *    └─ LuckysheetSheetConfigTest
 *
 * 3. 文件工具测试
 *    ├─ FileUploadHelperTest
 *    ├─ OssUtilsTest
 *    └─ ContentTypeEnumTest
 *
 * Mock 支持：
 * - FileClient          : 文件存储客户端 Mock
 * - OSSClient           : OSS SDK Mock
 * - S3Client            : AWS S3 SDK Mock
 *
 * ===============================
 *
 * @author suven
 * @version 1.0.0
 * @since 2024-01-01
 */
package com.suven.framework.file;
