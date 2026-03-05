package com.suven.framework.file.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.suven.framework.file.vo.request.*;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.file.config.UpLoadConstant;
import com.suven.framework.file.config.FileConfigSetting;
import com.suven.framework.file.util.FileMsgEnum;
import com.suven.framework.file.util.URLFileCommand;
import com.suven.framework.file.vo.UploadFileErrorEnum;
import com.suven.framework.file.vo.response.FileDownBytesResponseVo;
import com.suven.framework.file.vo.response.FileHistoryResponseVo;
import com.suven.framework.file.vo.response.FileUploadResponseVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.crypt.Base64Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@ApiDoc(module = "文件存储FastDFS文件服务的相关api",group = "File-Group",groupDesc = "FastDFS文件上传下载文档")
@Validated
@Slf4j
@RestController
public class FileFastDFSController {

	private Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired(required = false)
	protected FastFileStorageClient fastFileStorageClient;

	@Autowired(required = false)
	private AppendFileStorageClient appendFileStorageClient;

	@Autowired(required = false)
	protected RedisClientServer redisClusterServer;

	@Autowired(required = false)
	private FileConfigSetting fileConfigSetting;


//
//	@Value("${server.domain}")
//	private String domain;


	/**
	 * 上传文件
	 * @param files
	 * @throws IOException
	 */
	@ApiDoc(value = "FastDFS单个文件上传功能",author = "suven", request = FileUploadRequestVo.class, response = FileUploadResponseVo.class)
    @RequestMapping(value = URLFileCommand.file_post_file, method = RequestMethod.POST )
	public FileUploadResponseVo uploadFile(FileUploadRequestVo uploadFileVo, @PathVariable("files") MultipartFile files) throws IOException {
		if(null == files ){
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_IS_NULL_FAIL);
		}
		if(files.getSize() != uploadFileVo.getFileSize()){
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_SIZE_FAIL);
		}

		long userId = ParameterMessage.getRequestMessage().getUserId();
		FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
		InputStream inputStream = null;
		try {
			FileUploadBytesRequestVo uploadFile = FileUploadBytesRequestVo.build();
			uploadFile.setFileSize(files.getSize());
			uploadFile.setFileMd5(files.getOriginalFilename());
			uploadFile.setFileType(uploadFile.getFileExtName(files.getOriginalFilename()));
			UploadFileErrorEnum errorEnum = checkParam(uploadFile);
			if(null !=  errorEnum){
				vo.setErrorEnum(errorEnum);
				vo.setPath(files.getOriginalFilename());
				return vo;
			}
			inputStream  = files.getInputStream();
			StorePath storePath = fastFileStorageClient.uploadFile(inputStream,
					uploadFile.getFileSize(),uploadFile.getFileType(),null);
			if(storePath == null){
				logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
				vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
				vo.setPath(files.getOriginalFilename());
				return vo;
			}
			vo.setPath(storePath.getFullPath());
			vo.setDomain(fileConfigSetting.getDomain());
			return vo;

		}catch (Exception e){
			logger.error(e.getMessage());
		}finally {
			if(null != inputStream){
				inputStream.close();
			}
		}
		return vo;

	}

	/**
	 * 批量上传文件
	 * @param files
	 * @throws IOException
	 */
	@ApiDoc(value = "FastDFS批量上传文件功能",author = "suven", request = FileBathUploadRequestVo.class, response = FileUploadResponseVo.class)
	@RequestMapping(value = URLFileCommand.file_post_m_file, method = RequestMethod.POST )
	public PageResult<FileUploadResponseVo> uploadMultipartFile(FileBathUploadRequestVo uploadFileVo, @PathVariable("files") MultipartFile[] files) throws IOException {

		if(null == files || files.length < 0){
			throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_IS_NULL_FAIL);
		}
		long userId = ParameterMessage.getRequestMessage().getUserId();

		InputStream inputStream = null;

		PageResult<FileUploadResponseVo> pageResult = new PageResult<>();

		for (MultipartFile file : files){
			FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
			pageResult.getList().add(vo);
			try {
				FileUploadBytesRequestVo uploadFile = FileUploadBytesRequestVo.build();
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
				uploadFile.setFileSize(file.getSize());
				uploadFile.setFileMd5(file.getOriginalFilename());
				uploadFile.setFileType(ext);

				UploadFileErrorEnum errorEnum = checkParam(uploadFile);
				if(null !=  errorEnum){
					vo.setErrorEnum(errorEnum);
					vo.setPath(file.getOriginalFilename());
					continue;
				}
				inputStream  = file.getInputStream();
				StorePath storePath = fastFileStorageClient.uploadFile(inputStream,
						uploadFile.getFileSize(),uploadFile.getFileType(),null);
				if(storePath == null){
					logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
					vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
					vo.setPath(file.getOriginalFilename());
					continue;
				}
				vo.setPath(storePath.getFullPath());

			}catch (Exception e){
				logger.error(e.getMessage());
			}finally {
				if(null != inputStream){
					inputStream.close();
				}
			}
		}
		return pageResult;

	}


	/**
	 * 按字节上传文件
	 * @throws Exception
	 */

	@ApiDoc(value = "FastDFS 单个文件按字节数组上传文件功能",author = "suven", request = FileBathUploadRequestVo.class, response = FileUploadResponseVo.class)
	@RequestMapping(value = URLFileCommand.file_post_file_byte, method = RequestMethod.POST)
	public FileUploadResponseVo uploadFileBytes(FileUploadBytesRequestVo uploadFile)  throws Exception{
		//登录验证
		long userId = ParameterMessage.getRequestMessage().getUserId();
		FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
		ByteArrayInputStream inputStream  = null;
		try {
			UploadFileErrorEnum errorEnum = checkParam(uploadFile);
			if(null !=  errorEnum){
				vo.setErrorEnum(errorEnum);
				return vo;
			}
			byte[] blockSize = uploadFile.getBlockSize();
			inputStream = new ByteArrayInputStream(blockSize);
			StorePath storePath = fastFileStorageClient.uploadFile(inputStream,
					uploadFile.getFileSize(),uploadFile.getFileType(),null);
			if(storePath == null){
				logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
				vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
				return vo;
			}
			vo.setPath(storePath.getFullPath());
			return vo;

		}catch (Exception e){
			logger.error(e.getMessage());
		}finally {
			if(null != inputStream){
				inputStream.close();
			}
		}
		return vo;
	}

	/**
	 * 按字节上传从文件
	 * @throws Exception
	 */
	@ApiDoc(value = "FastDFS 单个文件按字节数组上传扩张缩放类型文件功能",author = "suven", request = FileBathUploadRequestVo.class, response = FileUploadResponseVo.class)
    @RequestMapping(value = URLFileCommand.file_post_file_slave_byte, method = RequestMethod.POST)
    public FileUploadResponseVo uploadFileSlaveBytes(FileSlaveUploadRequestVo uploadFile)  throws Exception{
        //登录验证
        long userId = ParameterMessage.getRequestMessage().getUserId();
        FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");
        ByteArrayInputStream inputStream  = null;
        try {
            UploadFileErrorEnum errorEnum = checkParam(uploadFile);
            if(null !=  errorEnum){
                vo.setErrorEnum(errorEnum);
                return vo;
            }
            String ext = FilenameUtils.getExtension(uploadFile.getUrlPath());
//            StorePath slaveFile = storageClient.uploadSlaveFile(storePath.getGroup(),storePath.getPath(),
//                    file.getInputStream(), file.getSize(),prefixName, ext);
            byte[] blockSize = uploadFile.getBlockSize();
            inputStream = new ByteArrayInputStream(blockSize);
            StorePath store = StorePath.parseFromUrl(uploadFile.getUrlPath());
            String group = store.getGroup();
            String masterFilename = store.getPath();
            String prefixName = uploadFile.getPrefixName();
            StorePath slaveFile = fastFileStorageClient.uploadSlaveFile(group,masterFilename,inputStream,
                    uploadFile.getFileSize(),prefixName,uploadFile.getFileType());
            if(slaveFile == null){
                logger.error("failed post file to cloud, urlPath:{}, userId:{}", uploadFile.getUrlPath(), userId);
                vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
                return vo;
            }
            vo.setPath(slaveFile.getFullPath());
            return vo;

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            if(null != inputStream){
                inputStream.close();
            }
        }
		return vo;
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
//			return SFileUploadStatus.newBuilder(;.setStatus(0).setMsg("文件太大").build());
			errorEnum =  UploadFileErrorEnum.UPLOAD_FILE_ERROR_OVER_BLOCK_SIZE;
			return errorEnum;
		}
		return errorEnum;
	}

	/**
	 * 按字符串上传文件,兼容php端实现
	 * @throws Exception
	 */
	@ApiDoc(value = "FastDFS 单个文件按字节串上传文件功能",author = "suven", request = FileUploadStringRequestVo.class, response = FileUploadResponseVo.class)
	@RequestMapping(value = URLFileCommand.file_post_string_block, method = RequestMethod.POST)
	public FileUploadResponseVo uploadFileBlockByString(FileUploadStringRequestVo uploadFile)  throws Exception{
		byte[] data = Base64Util.decode(uploadFile.getBlockSize());
		FileUploadBytesRequestVo fileReqVo = FileUploadBytesRequestVo.build()
				.setFileMd5(uploadFile.getFileMd5())
				.setBlockSize(data);
		fileReqVo.setFileType(uploadFile.getFileType());
		fileReqVo.setFileSize(uploadFile.getFileSize());
		fileReqVo.setOffset(uploadFile.getOffset());
		return this.uploadFileBlock(fileReqVo);
	}

	/**
	 * 按字符串上传从文件,兼容php端实现
	 * @throws Exception
	 */
    @RequestMapping(value = URLFileCommand.file_post_slave_string_block, method = RequestMethod.POST)
	@ApiDoc(value = "FastDFS 单个文件按字节串上传文件功能",author = "suven", request = FileUploadStringRequestVo.class, response = FileUploadResponseVo.class)
    public FileUploadResponseVo uploadFileSlaveBlockByString(FileUploadStringRequestVo uploadFile)  throws Exception{
        byte[] data = Base64Util.decode(uploadFile.getBlockSize());
        FileSlaveUploadRequestVo fileReqVo = FileSlaveUploadRequestVo.build();

        fileReqVo.setFileMd5(uploadFile.getFileMd5()).setBlockSize(data);

        fileReqVo.setFileType(uploadFile.getFileType());
        fileReqVo.setFileSize(uploadFile.getFileSize());
        fileReqVo.setOffset(uploadFile.getOffset());
        fileReqVo.setUrlPath(uploadFile.getUrlPath());
        fileReqVo.setPrefixName(uploadFile.getPrefixName());

       return this.uploadFileSlaveBytes(fileReqVo);
    }


	@RequestMapping(value = URLFileCommand.file_post_file_block, method = RequestMethod.POST)
	public FileUploadResponseVo uploadFileBlock(FileUploadBytesRequestVo uploadFile, @PathVariable("files") MultipartFile files)  throws Exception{
		uploadFile.setFileInputStream(files.getInputStream());
		return this.uploadFileBlock(uploadFile);
	}


	@ApiDoc(value = "FastDFS 单个按字节分块上传文件功能",author = "suven", request = FileUploadBytesRequestVo.class, response = FileUploadResponseVo.class)
	@RequestMapping(value = URLFileCommand.file_post_byte_block, method = RequestMethod.POST)
	public FileUploadResponseVo uploadFileBlock(FileUploadBytesRequestVo uploadFile)  throws Exception{

		//登录验证
		long userId = ParameterMessage.getRequestMessage().getUserId();

		//MD5
		String fileMd5 = uploadFile.getFileMd5();

		FileUploadResponseVo vo = FileUploadResponseVo.build().setStatus(0).setErrorMsg("OK");

		StorePath storePath = null;

		UploadFileErrorEnum errorEnum = checkParam(uploadFile);
		if(null !=  errorEnum){
			vo.setErrorEnum(errorEnum);
			return vo;
		}

		// 默认当前进度为0
		long curPosition = 0;
		//存储在fastDfs不带组的路径

		String noGroupPath = null;

		// 查看当前进度
		String result = redisClusterServer.get(UpLoadConstant.UPLOAD_KEY+userId+fileMd5);
		FileHistoryResponseVo historyResponseVo = JSONObject.parseObject(result, FileHistoryResponseVo.class);
		if (null != historyResponseVo) {
			curPosition = historyResponseVo.getCurPosition();
			noGroupPath = historyResponseVo.getNoGroupPath();
		}

		// 已经上传过
		if(null != historyResponseVo && curPosition == uploadFile.getFileSize()) {
			if (!StringUtils.isBlank(historyResponseVo.getNoGroupPath())) {
				vo.setDomain(fileConfigSetting.getDomain());
				vo.setOffset(uploadFile.getOffset());
				vo.setPath(UpLoadConstant.DEFAULT_GROUP_PATH + historyResponseVo.getNoGroupPath());
				return vo;
			}
		}

		// 续传位置不对 返回当前缓存位置,且不是最后一块
		if (curPosition != uploadFile.getOffset() ) {
			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_OFFSET).setOffset(curPosition).setPath(uploadFile.getFileMd5());
			return vo;
		}

		//第一块
		if(curPosition == 0) {
			storePath = appendFileStorageClient.uploadAppenderFile(UpLoadConstant.DEFAULT_GROUP, uploadFile.getFileInputStream(),
					uploadFile.getChunkSize(), uploadFile.getFileType());

			//缓存保存
			curPosition = uploadFile.getChunkSize(); //设置新位置
			saveCache(userId,fileMd5,curPosition,storePath.getPath());
			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_NOT_FINISH).setOffset(uploadFile.getChunkSize()).setPath(uploadFile.getFileMd5());
			return vo;
		}


		//上传未完成 返回当前位置，件上传尚未完成,请继续上传
		if (curPosition < uploadFile.getFileSize()) {
			//追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
			appendFileStorageClient.modifyFile(UpLoadConstant.DEFAULT_GROUP, historyResponseVo.getNoGroupPath(), uploadFile.getFileInputStream(),
					uploadFile.getChunkSize(),curPosition);

			//缓存保存
			curPosition = curPosition + uploadFile.getChunkSize();
			saveCache(userId,fileMd5,curPosition,noGroupPath);
			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_NOT_FINISH).setOffset(curPosition).setPath(uploadFile.getFileMd5());
			return vo;
		}


		if(storePath == null){
			vo.setErrorEnum(UploadFileErrorEnum.UPLOAD_FILE_ERROR_TO_FST);
			return vo;
		}
		saveCache(userId,fileMd5,curPosition,storePath.getPath());
		vo.setPath(storePath.getFullPath());
		return vo;
	}


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


	/**
	 * 删除文件
	 * @param uploadFile 文件访问地址
	 * @return
	 */
	@ApiDoc(value = "FastDFS 根据url删除文件功能",author = "suven", request = FileDeleteRequestVo.class, response = boolean.class)
	@RequestMapping(value = URLFileCommand.file_post_file_delete, method = RequestMethod.POST)
	public boolean deleteFile(FileDeleteRequestVo uploadFile) throws FdfsUnsupportStorePathException {
		if (isEmpty(uploadFile.getFileUrl())) {

			return false;
		}
		StorePath storePath = StorePath.parseFromUrl(uploadFile.getFileUrl());
		fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
		return true;
	}

    /**
     * 下载文件
     * @param uploadFile 文件访问地址
     * @return
     */
	@ApiDoc(value = "FastDFS 根据url下载文件功能",author = "suven", request = FileDownloadRequestVo.class, response = Byte[].class)
    @RequestMapping(value = URLFileCommand.file_post_file_download, method = RequestMethod.POST)
    public byte[] downloadFile(FileDownloadRequestVo uploadFile) throws FdfsUnsupportStorePathException {
        if (StringUtils.isEmpty(uploadFile.getUrlPath())) {
            throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_PATH_NULL);
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(uploadFile.getUrlPath());
            byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(),
                    new FileDownloadCallback());

            return bytes;
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
		return null;
    }

    /**
     * 分块下载文件
     * @param downloadFile 文件访问地址
     * @return
     */
	@ApiDoc(value = "FastDFS 根据url分块下载文件",author = "suven", request = FileDownloadRequestVo.class, response = FileDownBytesResponseVo.class)
    @RequestMapping(value = URLFileCommand.file_post_file_download_block, method = RequestMethod.POST)
    public byte[] downloadFileBlock(FileDownloadRequestVo downloadFile) throws FdfsUnsupportStorePathException {
        if (StringUtils.isEmpty(downloadFile.getUrlPath())) {
            throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_PATH_NULL);
        }
        if(downloadFile.getFileSize() < 0){
            throw new SystemRuntimeException(FileMsgEnum.UPLOAD_FILE_SIZE_ZERO);
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(downloadFile.getUrlPath());
            byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(),downloadFile.getFileOffset(),
                    downloadFile.getFileSize(),new FileDownloadCallback());
            return bytes;
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
		return null;
    }

    private boolean isEmpty( String str){
    	return (null == str || "".equals(str));
	}

}
