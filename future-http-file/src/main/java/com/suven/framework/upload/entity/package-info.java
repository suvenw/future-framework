/**
 * upload 模块，主要提供两块能力：
 * 1. 提供文件，支撑上传的通用与核心业务。
 * 2. 研发工具，提升研发效率与质量。 例如说：代码生成器、接口文档等等
 *
 * 一. 主要是文件上传后,数据地址记录相关信息,做成平台化,
 *  配置:
 *  1.创建app应用,  FileUploadApp 表存储
 *  2.每个应用支持多个存储空间配置; FileAppStorageConfig 表存储相关信息,与(1)的关系,多对1的关系,1个应用有多个配置存储空间信息
 *  3.一个app应用,有多种场景业务,FileUploadUseBusiness 表存储相关信息, 与(1),(2)的关系,是多对1的关系;即一个应用有支持多种业务场景使用;
 *  4.上传业务方文件数据;FileUploadStorage 表存储相关信息; 一个应用,根据存储空间配置信息,存储文件相关信息,和访问信息,与(1),(2),(3)的关系,是多对一的关系;
 *   4-1.根据(3)的属性 callbackService 配置信息和结合dataForm,formType,组装回调业务方,验证数据准确性
 *   4-2.根据"应用--对应业务场景的配置属性,和自定义上传的属性维护FileUploadStorage属性信息,优先自定义的,不存在时使用 FileUploadUseBusiness里的属性;
 *   4-3.根据interpretData 属性判断,是否需要解悉文件数据解释存储到 mogodb 中,提供界面修改,查询,展示和验证
 *   4-4.提供exel解决数据;提供txt数据解释,后面迭代增加;
 *   4-5.提供访问文件对应存储中间的文件访问完整地址;
 *   4-6.提供文件对应对应存储中间的文件名称,域名地址,后期由业务选择存储到具体数据逻辑数据库中,提供业务列表数据;
 *   4-7.提供文件生成临时访问算法逻辑,让业务方决定,临时授权访问逻辑
 *  5.根据 FileUploadStorage的属性 interpretData,和fileType 文件类型,实现对应的数据解释,生成json数据,存储到mongodb数据库中,FileDataDetailed为存储到mongodb表对象的基本通用信息;
 *  6.根据业务的通过信息,提供业务分页读取的数据逻辑实现,
 *    6-1.根据interpretData, interpretDataTotal值,结合分页逻辑,提供分页查询获取列表数据;
 *    6-2.验证数据是否准确 callbackValidate,结合FileUploadUseBusiness 表的 callbackService或 callbackAsyncService 回调服务地址,将数据回调业务,
 *  7.mongodb 表,提供相关冗余信息,方便业务方搜索和查询使用;
 *  8.FileDataDetailedToMG 表,记录回调业务异常逻辑记录信息,对应errorCode错误code和errorMessage 错误提示信息
 *  9.FileUploadActionWater 表,记录提供操作的行为记录信息;
 *  10.FileBusinessCallbackWater 文件平台回调各业务方,异常处理信息和处理结果
 *
 *  二.下载任务中心
 *  1.接入下载文件任务
 *  2.调用业务下载文件接口
 *  3.结合业务数据和生成文件格式,生成文件
 *  4.将上成文件上传到存储中心
 *  5.提供安全验证下载文件逻辑
 */
package com.suven.framework.upload.entity;











// /** 是否全部数据通过数据检查 **/
//    private long id;
//     private long idempotent
/** 回调状态.0.未执行, 1.已回调成功, 2.已回调成异常 **/
//    private int callbackStatus;
/** 解决数据信息,统一json 格式存储 **/
//    private String dataJson;
/** 是否通过数据检查,检查异常修改为 .1,默认为0 **/
//      private int checkStatus;
/** 检查数据错误提示编码 **/
//    private String errorCode;
/** 检查数据错误提示信息 **/
//    private String errorMessage;




//
//    /** 业务公司人员的部门id **/
//    private long  deptId;
//    /** 业务公司人员的部门名称 **/
//    private String deptName;
//    /** 上传人员的id**/
//    private long uploadUserId;
//    /** 上传人员的名称 **/
//    private String uploadUserName;






// /** 是否全部数据通过数据检查 **/
//    private long id;
//     private long idempotent
/** 回调状态.0.未执行, 1.已回调成功, 2.已回调成异常 **/
//    private int callbackStatus;
/** 解决数据信息,统一json 格式存储 **/
//    private String dataJson;
/** 是否通过数据检查,检查异常修改为 .1,默认为0 **/
//      private int checkStatus;
/** 检查数据错误提示编码 **/
//    private String errorCode;
/** 检查数据错误提示信息 **/
//    private String errorMessage;

