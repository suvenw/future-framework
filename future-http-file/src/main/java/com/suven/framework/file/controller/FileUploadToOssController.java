package com.suven.framework.file.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.file.client.FileClient;
import com.suven.framework.file.config.UpLoadConstant;
import com.suven.framework.file.config.FileConfigSetting;
import com.suven.framework.file.util.FileMsgEnum;
import com.suven.framework.file.util.OSSUploadUtil;
import com.suven.framework.file.util.QRCodeUtil;
import com.suven.framework.file.util.URLFileCommand;
import com.suven.framework.file.vo.UploadFileErrorEnum;
import com.suven.framework.file.vo.request.*;
import com.suven.framework.file.vo.response.FileHistoryResponseVo;
import com.suven.framework.file.vo.response.FileUploadResponseVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.date.DateUtil;
import com.suven.framework.util.http.OkHttpClients;
import com.suven.framework.util.random.RandomUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.suven.framework.file.util.FileMsgEnum.DELETE_FILE_PATH_IS_NULL;


@ApiDoc(module = "文件存储阿里云OSS文件服务的相关api",group = "File-group",groupDesc = "阿里云oss存储文件件上传下载文档")
@Controller
public class FileUploadToOssController {

	private Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired(required = false)
	private OSS ossClient;


	@Autowired
	private FileConfigSetting fileConfigSetting;


	@Autowired
	private IOssFileBuildName ossFileBuildName;


	@Autowired(required = false)
	protected RedisClientServer redisClusterServer;


	@Autowired
	private FileClient fileClient;



	/**
	 * 上传文件
	 * @param output
	 * @param uploadFileVo
	 * @param files
	 * @throws IOException
	 */
	@ApiDoc(value = "阿里云oss存储--单个文件上传功能",author = "suven",
			request = FileUploadRequestVo.class,
			response = FileUploadResponseVo.class)
    @RequestMapping(value = URLFileCommand.oss_file_post_file, method = RequestMethod.POST )
	public void uploadFileToOss(OutputResponse output, FileUploadRequestVo uploadFileVo, @PathVariable("files") MultipartFile files) throws IOException {
		if(null == files ){
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_IS_NULL_FAIL);
		}
        long userId = ParameterMessage.getRequestMessage().getUserId();
        try {
            FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");

            String ext = FilenameUtils.getExtension(files.getOriginalFilename());

            FileUploadBytesRequestVo uploadFile = FileUploadBytesRequestVo.build();
            uploadFile.setFileSize(files.getSize());
            uploadFile.setFileMd5(files.getOriginalFilename());
            uploadFile.setFileType(ext);

            UploadFileErrorEnum errorEnum = checkParam(uploadFile);
            if (null != errorEnum) {
                vo.setErrorEnum(errorEnum);
                vo.setPath(files.getOriginalFilename());
                output.write(vo);
                return;
            }
            //文件名称
			String ossFileName = this.uploadFileToOssFileService(uploadFileVo.getOssPath(),uploadFile.getPrefixPath(),ext);
			//生成阿里文件访问url
			String storePath = getOssFilePath(files.getInputStream(),ext,uploadFileVo.getOssKey(),ossFileName);
            if(null == storePath){
                logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
                vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_OSS);
                vo.setPath(files.getOriginalFilename());
                output.write(vo);
                return;
            }
			vo.setOssUrl(storePath);
//            vo.setPath(ossFileName);
			vo.setPath(fileConfigSetting.getOss().getDomain()+"/"+ossFileName);
            vo.setDomain(fileConfigSetting.getOss().getDomain());
            output.write(vo);
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.error("Error Message: " + oe.getErrorMessage());
            logger.error("Error Code:       " + oe.getErrorCode());
            logger.error("Request ID:      " + oe.getRequestId());
            logger.error("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.error("Error Message: " + ce.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }



	}

	private String uploadFileToOssFileService(String ossPath, String prefixPath, String fileType){
		if(ossPath == null || "".equals(ossPath )){
			String ossFileName = this.uploadFileToOssFileService(prefixPath,fileType);
			return ossFileName;
		}
		return ossPath;
	}

	private String uploadFileToOssFileService(String prefixPath, String fileType){
		if(prefixPath == null || "".equals(prefixPath)){
			String path = DateUtil.date2Str(new Date(), DateUtil.PATTERN_YYYYMM);
			prefixPath = "im/"+path;
		}
		String ossFileName = ossFileBuildName.getOssFileName(prefixPath) + "."+ fileType;
		return ossFileName;
	}

	/** 文件上传到阿里oss 返回组装的阿里云oss url**/
	private String getOssFilePath(InputStream inputStream,String fileType,String ossKey ,String ossFileName) throws Exception{
//		String ossFileName = ossFileBuildName.getOssFileName(prefixPath) + "."+ fileType;
		String path = fileClient.upload(inputStream,ossFileName,fileType);
//		String bucketName = fileConfigSetting.getOss().getBucketName(ossKey);
//		boolean storePath = OSSUploadUtil.uploadFileInputStreamToOSS(ossClient,inputStream, fileType,ossFileName,bucketName);
//		if(storePath){
//			String url = fileConfigSetting.getOssPath(bucketName,ossFileName);
//			return url;
//		}
		return path;

	}
	/**
     * 批量上传文件
	 * @param output
     * @param uploadFileVo
     * @param files
     * @throws IOException
	 */
	@ApiDoc(value = "阿里云oss存储--批量上传文件功能",author = "suven", request = FileBathUploadRequestVo.class, response = FileUploadResponseVo.class)
	@RequestMapping(value = URLFileCommand.oss_file_post_m_file, method = RequestMethod.POST )
	public void uploadMultipartFileToOss(OutputResponse output, FileBathUploadRequestVo uploadFileVo, @PathVariable("files") MultipartFile[] files) throws IOException {

		if (null == files || files.length < 0) {
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_IS_NULL_FAIL);
		}
		long userId = ParameterMessage.getRequestMessage().getUserId();

		InputStream inputStream = null;
		List<FileUploadResponseVo> list = new ArrayList<>();

		for (MultipartFile file : files) {
			FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
			list.add(vo);
			try {
				FileUploadBytesRequestVo uploadFile = FileUploadBytesRequestVo.build();
				String ext = FilenameUtils.getExtension(file.getOriginalFilename());
				uploadFile.setFileSize(file.getSize());
				uploadFile.setFileMd5(file.getOriginalFilename());
				uploadFile.setFileType(ext);

				UploadFileErrorEnum errorEnum = checkParam(uploadFile);
				if (null != errorEnum) {
					vo.setErrorEnum(errorEnum);
					vo.setPath(file.getOriginalFilename());
					continue;
				}
				inputStream = file.getInputStream();
				//文件名称
				String ossFileName = this.uploadFileToOssFileService(uploadFile.getPrefixPath(),ext);
				//生成阿里文件访问url

				String storePath = getOssFilePath(file.getInputStream(),ext,uploadFileVo.getOssKey(),ossFileName);
				if (storePath == null) {
					logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
					vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_OSS);
					vo.setPath(file.getOriginalFilename());
					output.write(vo);
					return;
				}
				vo.setOssUrl(storePath);
//              vo.setPath(ossFileName);
				vo.setPath(fileConfigSetting.getOss().getDomain()+"/"+ossFileName);
				vo.setDomain(fileConfigSetting.getOss().getDomain());
				output.write(vo);
				if (storePath == null) {
					logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
					vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_OSS);
					vo.setPath(file.getOriginalFilename());
					continue;
				}
				output.write(vo);
			} catch (OSSException oe) {
				logger.error("Caught an OSSException, which means your request made it to OSS, "
						+ "but was rejected with an error response for some reason.");
				logger.error("Error Message: " + oe.getErrorMessage());
				logger.error("Error Code:       " + oe.getErrorCode());
				logger.error("Request ID:      " + oe.getRequestId());
				logger.error("Host ID:           " + oe.getHostId());
			} catch (ClientException ce) {
				logger.error("Caught an ClientException, which means the client encountered "
						+ "a serious internal problem while trying to communicate with OSS, "
						+ "such as not being able to access the network.");
				logger.error("Error Message: " + ce.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if (null != inputStream) {
					inputStream.close();
				}
			}

		}

	}

	/**
     * 按字节上传文件
	 * @param output
     * @param uploadFileVo
     * @throws Exception
	 */

	@ApiDoc(value = "阿里云oss存储-- 单个文件按字节数组上传文件功能",author = "suven", request = FileUploadBytesRequestVo.class, response = FileUploadResponseVo.class)
	@RequestMapping(value = URLFileCommand.oss_file_post_file_byte, method = RequestMethod.POST)
	public void uploadFileBytes(OutputResponse output, FileUploadBytesRequestVo uploadFileVo)  throws Exception{
		//登录验证
		long userId = ParameterMessage.getRequestMessage().getUserId();
		FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
		ByteArrayInputStream inputStream  = null;
		try {
			UploadFileErrorEnum errorEnum = checkParam(uploadFileVo);
			if(null !=  errorEnum){
				vo.setErrorEnum(errorEnum);
				output.write( vo);
				return;
			}
			byte[] blockSize = uploadFileVo.getBlockSize();
			inputStream = new ByteArrayInputStream(blockSize);

			//文件名称
			String ossFileName = this.uploadFileToOssFileService(uploadFileVo.getOssPath(),uploadFileVo.getPrefixPath(),uploadFileVo.getFileType());
			//生成阿里文件访问url

			String storePath = getOssFilePath(inputStream,uploadFileVo.getFileType(),uploadFileVo.getOssKey(),ossFileName);
			if(storePath == null){
				logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFileVo.getUrlPath(), userId);
				vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
				output.write(vo);
				return;
			}
			vo.setOssUrl(storePath);
//            vo.setPath(ossFileName);
			vo.setPath(fileConfigSetting.getOss().getDomain()+"/"+ossFileName);
			vo.setDomain(fileConfigSetting.getOss().getDomain());
			output.write(vo);
		} catch (OSSException oe) {
			logger.error("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			logger.error("Error Message: " + oe.getErrorMessage());
			logger.error("Error Code:       " + oe.getErrorCode());
			logger.error("Request ID:      " + oe.getRequestId());
			logger.error("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			logger.error("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: " + ce.getMessage());

		}catch (Exception e){
			logger.error(e.getMessage());
		}finally {
			if(null != inputStream){
				inputStream.close();
			}
		}
	}



	private UploadFileErrorEnum checkParam(FileUploadBytesRequestVo uploadFile) throws Exception{
		//过滤非法类型,仅支持这类型文件上传
		//登录验证
		UploadFileErrorEnum errorEnum = null;
		if(!fileConfigSetting.isCheckParam()){
            return null;
        }
		long userId = ParameterMessage.getRequestMessage().getUserId();
        if (!uploadFile.checkFileSize()) {
            // 超出大小
            logger.warn("append filePart.length beyond fileSize:{}, userId:{}", uploadFile.getFileSize(), userId);
            errorEnum =  UploadFileErrorEnum.UPLOAD_FILE_ERROR_OVER_SIZE;
            return errorEnum;
        }
		String fileSuffix = uploadFile.getFileType();
		if(!fileConfigSetting.validatorFileType(fileSuffix)){
            logger.warn("not allowed file type:{}, userId:{}", fileSuffix, userId);
            errorEnum = UploadFileErrorEnum.UPLOAD_FILE_ERROR_EXT_NAME;
            return errorEnum;
        }
		if (!fileConfigSetting.validatorFileSize(uploadFile.getFileSize())) {
			// 文件太大
			logger.warn("file too big fileSize:{},  XXXId:{}", uploadFile.getFileSize(), userId);
//			message.write(SFileUploadStatus.newBuilder().setStatus(0).setMsg("文件太大").build());
			errorEnum =  UploadFileErrorEnum.UPLOAD_FILE_ERROR_OVER_BLOCK_SIZE;
			return errorEnum;
		}
		return errorEnum;
	}



	/**
	 * 删除文件
	 * @param uploadFile 文件访问地址
	 * @return
	 */
	@ApiDoc(value = "阿里云oss存储-- 根据url删除文件功能",author = "suven", request = FileDeleteRequestVo.class, response = boolean.class)
	@RequestMapping(value = URLFileCommand.oss_file_post_file_delete, method = RequestMethod.POST)
	public void deleteFile(OutputResponse output, FileDeleteRequestVo uploadFile) throws FdfsUnsupportStorePathException {
		if (isEmpty(uploadFile.getFileUrl())) {
			output.write(DELETE_FILE_PATH_IS_NULL);
			return;
		}
		OSSUploadUtil.deleteFile(ossClient,uploadFile.getFileUrl());
		output.writeSuccess();
	}
	/**
	 * 删除文件
	 * @param uploadFile 文件访问地址
	 * @return
	 */
	@ApiDoc(value = "阿里云oss存储-- 根据url删除文件功能",author = "suven", request = FileDeleteRequestVo.class, response = boolean.class)
	@RequestMapping(value = URLFileCommand.oss_file_post_delete_list, method = RequestMethod.POST)
	public void batchDeleteFiles(OutputResponse output, FileDeleteRequestVo uploadFile) throws FdfsUnsupportStorePathException {
		if (isEmpty(uploadFile.getFileUrl())) {
			output.write(DELETE_FILE_PATH_IS_NULL);
			return;
		}
		OSSUploadUtil.deleteFiles(ossClient,uploadFile.getFileUrls());
		output.writeSuccess();
	}



    private boolean isEmpty( String str){
    	return (null == str || "".equals(str));
	}



//	@ApiDoc(value = "阿里云oss存储-- 视频分块转m3u8文件功能",author = "suven", request = FileUploadBytesRequestVo.class, response = FileUploadResponseVo.class)
//	@RequestMapping(value = URLFileCommand.file_post_byte_m3u8, method = RequestMethod.POST)
//	public void uploadFileM3u8(OutputResponse output, FileUploadBytesRequestVo uploadFile)  throws Exception{
//
//		//登录验证
//		long userId = ParamMessage.getRequestMessage().getUserId();
//
//		//MD5
//		String fileMd5 = uploadFile.getFileMd5();
//
//		FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
//
//		StorePath storePath = null;
//
//		UploadFileErrorEnum errorEnum = checkParam(uploadFile);
//		if(null !=  errorEnum){
//			vo.setErrorEnum(errorEnum);
//			output.write( vo);
//			return;
//		}
//
//		// 默认当前进度为0
//		long curPosition = 0;
//		//存储在fastDfs不带组的路径
//
//		String noGroupPath = null;
//
//		// 查看当前进度
//		String result = redisClusterServer.get(UpLoadConstant.UPLOAD_KEY+userId+fileMd5);
//		FileHistoryResponseVo historyResponseVo = JSONObject.parseObject(result, FileHistoryResponseVo.class);
//		if (null != historyResponseVo) {
//			curPosition = historyResponseVo.getCurPosition();
//			noGroupPath = historyResponseVo.getNoGroupPath();
//		}
//
//		// 已经上传过
//		if(null != historyResponseVo && curPosition == uploadFile.getFileSize()) {
//			if (!StringUtils.isBlank(historyResponseVo.getNoGroupPath())) {
//				vo.setDomain(fileConfigSetting.getDomain());
//				vo.setOffset(uploadFile.getOffset());
//				vo.setPath(UpLoadConstant.DEFAULT_GROUP_PATH + historyResponseVo.getNoGroupPath());
//				output.write(vo);
//				return;
//			}
//		}
//
//		// 续传位置不对 返回当前缓存位置,且不是最后一块
//		if (curPosition != uploadFile.getOffset() ) {
//			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_OFFSET).setOffset(curPosition).setPath(uploadFile.getFileMd5());
//			output.write(vo);
//			return;
//		}
//
//		//第一块
//		if(curPosition == 0) {
//			//文件名称
//			String ossFileName = this.getOssFileName(uploadFile.getPrefixPath(),uploadFile.getFileType());
//			//生成阿里文件访问url
//
//			storePath = getOssFilePath(inputStream,uploadFile.getFileType(),uploadFile.getOssKey(),ossFileName);
////			storePath = appendFileStorageClient.uploadAppenderFile(UpLoadConstant.DEFAULT_GROUP, uploadFile.getFileInputStream(),
////					uploadFile.getChunkSize(), uploadFile.getFileType());
//
//			//缓存保存
//			curPosition = uploadFile.getChunkSize(); //设置新位置
//			saveCache(userId,fileMd5,curPosition,storePath.getPath());
//			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_NOT_FINISH).setOffset(uploadFile.getChunkSize()).setPath(uploadFile.getFileMd5());
//			output.write(vo);
//			return;
//		}
//
//
//		//上传未完成 返回当前位置，件上传尚未完成,请继续上传
//		if (curPosition < uploadFile.getFileSize()) {
//			//追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
//			appendFileStorageClient.modifyFile(UpLoadConstant.DEFAULT_GROUP, historyResponseVo.getNoGroupPath(), uploadFile.getFileInputStream(),
//					uploadFile.getChunkSize(),curPosition);
//
//			//缓存保存
//			curPosition = curPosition + uploadFile.getChunkSize();
//			saveCache(userId,fileMd5,curPosition,noGroupPath);
//			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_NOT_FINISH).setOffset(curPosition).setPath(uploadFile.getFileMd5());
//			output.write(vo);
//			return;
//		}
//
//
//		if(storePath == null){
//			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
//			output.write(vo);
//			return;
//		}
//		saveCache(userId,fileMd5,curPosition,storePath.getPath());
//		vo.setPath(storePath.getFullPath());
//		output.write(vo);
//	}


	/**
	 * 缓存保存
	 * @param userId
	 * @param fileMd5
	 * @param curPosition
	 * @param noGroupPath
	 */
	private void saveCache(long userId, String fileMd5, long curPosition, String noGroupPath){
		FileHistoryResponseVo responseVo = FileHistoryResponseVo.build()
				.setCurPosition(curPosition)
				.setNoGroupPath(noGroupPath);
		redisClusterServer.setEx(UpLoadConstant.UPLOAD_KEY+userId+fileMd5, JSON.toJSONString(responseVo));
	}

//	public static void upload(String md5,String index) throws IOException {//md5是m3u8文件的唯一名字，index是需要上传的m3u8文件的原地址。
//		String endpoint = "oss-cn-beijing.aliyuncs.com";//oss域名
//		String accessKeyId = "LTAI4FcZuuuqsXoQkBCHx6Ct";//ossId
//		String accessKeySecret = "dj8NFoCXkIWLajOvjvubxad9P2NdVv";//ossKey
//		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//		URLConnection url = new URL(index).openConnection(); ////index为m3u8地址
//		InputStream inputStream = url.getInputStream();
//		ossClient.putObject("yirujun", "video/"+md5+"/"+md5+".m3u8", inputStream);//第二个参数为oss文件存放目录，第一个md5是m3u8文件地址,第二个为文件名（这样做是为了让m3u8文件与.ts文件在同一文件夹下)
//		inputStream.close();
//
//		URLConnection url1 = new URL(index).openConnection();
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url1.getInputStream()));
//		String  text = "";
//		String tsUrl = index.split("index")[0];//获取m3u8链接的前缀部分
//		while((text =bufferedReader.readLine())!= null){//读出m3u8文件,逐个上传 .ts文件
//			System.out.println("上传ts文件："+text);
//			if(text.indexOf(".ts") != -1){//只需要.ts文件
//				try {
//					URLConnection url2 = new URL(tsUrl + text).openConnection();
//					InputStream inputStream2 = url2.getInputStream();
//					ossClient.putObject("yirujun", "video/" + md5 + "/" + text, inputStream2);//第二个参数为oss文件存放目录**
//					inputStream2.close();
//				}catch (Exception E){
//					continue;
//				}
//			}
//		}
//		ossClient.shutdown();
//	}


	@ApiDoc(
			value = "生成二维码上传到OSS",
			request = QRCodeUserInfoRequestVo.class,
			response = FileUploadResponseVo.class
	)
	@RequestMapping(value = URLFileCommand.oss_file_post_qrCodeUploadOss, method = RequestMethod.POST)
	public void qrCodeUploadOss(OutputResponse output, QRCodeUserInfoRequestVo vo) {
		try {
			logger.info("开始生成二维码上传到OSS");
			//生成二维码的编码
			int code = RandomUtils.num(100000, 999999);
			//生成二维码
			BufferedImage verifyImg = QRCodeUtil.drawLogoQRCode(vo.getQrUrl(), code);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String fileType = StringUtils.isEmpty(vo.getFileType()) ? "jpg" : vo.getFileType();
			ImageIO.write(verifyImg, fileType, os);//输出图片流
			ByteArrayInputStream inputStream = new ByteArrayInputStream(os.toByteArray());
			//文件名称
			String ossFileName = this.uploadFileToOssFileService(vo.getOssPath(), vo.getFolderName(), fileType);
			//上传文件到阿里云
			String storePath = getOssFilePath(inputStream, fileType, vo.getOssKey(), ossFileName);
			FileUploadResponseVo responseVo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
			if (null == storePath) {
				logger.error("failed post file to cloud, urlPath:{}", ossFileName);
				responseVo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_OSS);
				responseVo.setPath(ossFileName);
				output.write(vo);
				return;
			}
			os.flush();
			os.close();//关闭流
			responseVo.setOssUrl(storePath);
			responseVo.setPath(fileConfigSetting.getOss().getDomain() + "/" + ossFileName);
			responseVo.setDomain(fileConfigSetting.getOss().getDomain());
			output.write(responseVo);
		} catch (Exception e) {
			logger.error("qrCodeUploadOss Exception: ",e);
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_EXCEPTION_FAIL);
		}
	}


	@ApiDoc(
			value = "生成二维码带logo上传到OSS",
			request = QRCodeRequestVo.class,
			response = FileUploadResponseVo.class
	)
	@RequestMapping(value = URLFileCommand.oss_file_post_qrLogoUploadOss, method = RequestMethod.POST)
	public void qrCodeLogo(OutputResponse output, QRCodeRequestVo vo) {
		try {
			logger.info("开始生成二维码上传到OSS");
			//生成二维码的编码
			int code = RandomUtils.num(100000, 999999);

			InputStream logoCodeStream  = OkHttpClients.getHttpInputStream(vo.getLogoCodeUrl(),null);
			//生成二维码
			BufferedImage verifyImg = QRCodeUtil.drawLogoQRCode(vo.getQrUrl(),code, logoCodeStream,vo.getWidth(),vo.getHeight());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String fileType = StringUtils.isEmpty(vo.getFileType()) ? "png" : vo.getFileType();
			ImageIO.write(verifyImg, fileType, os);//输出图片流
			ByteArrayInputStream inputStream = new ByteArrayInputStream(os.toByteArray());
			//文件名称
			String ossFileName = this.uploadFileToOssFileService(vo.getOssPath(), null, fileType);
			//上传文件到阿里云
			String storePath = getOssFilePath(inputStream, fileType, vo.getOssKey(), ossFileName);
			FileUploadResponseVo responseVo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
			if (null == storePath) {
				logger.error("failed post file to cloud, urlPath:{}", ossFileName);
				responseVo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_OSS);
				responseVo.setPath(ossFileName);
				output.write(vo);
				return;
			}
			os.flush();
			os.close();//关闭流
			responseVo.setOssUrl(storePath);
			responseVo.setPath(fileConfigSetting.getOss().getDomain() + "/" + ossFileName);
			responseVo.setDomain(fileConfigSetting.getOss().getDomain());
			output.write(responseVo);
		} catch (Exception e) {
			logger.error("qrCodeLogo Exception: ",e);
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_EXCEPTION_FAIL);
		}
	}

	@ApiDoc(
			value = "生成二维码带logo上传到OSS",
			request = QRCodeRequestVo.class,
			response = String.class
	)
	@RequestMapping(value = URLFileCommand.oss_file_post_qrLogoUploadImg, method = RequestMethod.POST)
	public void qrCodeBaseImg(HttpServletResponse response, QRCodeRequestVo vo) {
		try {
			logger.info("开始生成二维码上传到OSS");
			//生成二维码的编码
			int code = RandomUtils.num(100000, 999999);
			String fileType = StringUtils.isEmpty(vo.getFileType()) ? "png" : vo.getFileType();
			response.setContentType("image/"+fileType);//必须设置响应内容类型为图片，否则前台不识别

			InputStream logoCodeStream  = OkHttpClients.getHttpInputStream(vo.getLogoCodeUrl(),null);
			//生成二维码
			BufferedImage verifyImg = QRCodeUtil.drawLogoQRCode(vo.getQrUrl(),code, logoCodeStream,vo.getWidth(),vo.getHeight());
			OutputStream os = response.getOutputStream(); //获取文件输出流
			ImageIO.write(verifyImg, fileType, os);//输出图片流
			os.flush();
			os.close();//关闭流
		} catch (Exception e) {
			logger.error("qrCodeBaseImg Exception: ",e);
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_EXCEPTION_FAIL);
		}
	}





}
