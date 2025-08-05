/**
 * file 模块，主要提供两块能力：
 * 1. 提供文件上传和下载能力，支撑上层的通用与核心业务。
 * 2. 研发工具，提升研发效率与质量。 例如说：代码生成器、接口文档等等
 * 3.文件上传存储中间键:见client 文件夹,已实现可配置,可选择,已实现支持本地,ftp,sftp,s3
 *  其中s3,统一标准化,实现minio中间存储标准,支持阿里云,腾讯云,七牛云,华为云
 *       -节点地址
 *       -1. MinIO：https://www.iocoder.cn/Spring-Boot/MinIO 。例如说，http://127.0.0.1:9000
 *      - 2. 阿里云：https://help.aliyun.com/document_detail/31837.html
 *      - 3. 腾讯云：https://cloud.tencent.com/document/product/436/6224
 *      - 4. 七牛云：https://developer.qiniu.com/kodo/4088/s3-access-domainname
 *      - 5. 华为云：https://developer.huaweicloud.com/endpoint?OBS
 *
 * 1. Controller URL：以 /file/ 开头，避免和其它 Module 冲突
 * 2. DataObject 表名：以 file_ 开头，方便在数据库中区分
 */
package com.suven.framework.file;
