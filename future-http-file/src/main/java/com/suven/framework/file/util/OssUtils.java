package com.suven.framework.file.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Description: 阿里云OSS工具类
 * @author 黄聪
 * date 2015年10月29日 下午3:59:12
 */
public class OssUtils {

	private static String accessKeyId = "iXpQFs4mbuUvIUWa";
	private static String accessKeySecret = "EsrWZid27X2KJHWFXkwrrebH2YqXvl";
	private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
	private static String bucketName = "qidianimg";

	/**
	 * 上传文件
	 *
	 *  key 上传到OSS起的名
	 * @param fileName 本地文件名
	 *
	 * @throws OSSException
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */
	public static String uploadFile(String fileName) throws FileNotFoundException {
		OSSClient client = createOssClient();
		String key = "supplyerp/" + System.currentTimeMillis();
		setBucketPublicReadable();
		File file = new File(fileName);
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(file.length());
		// 判断上传类型，多的可根据自己需求来判定
		if (fileName.endsWith("xml")) {
			objectMeta.setContentType("text/xml");
		} else if (fileName.endsWith("jpg")) {
			objectMeta.setContentType("image/jpeg");
		} else if (fileName.endsWith("png")) {
			objectMeta.setContentType("image/png");
		}

		InputStream input = new FileInputStream(file);
		client.putObject(bucketName, key, input, objectMeta);
		return key;
	}

	/**
	 * 根据文件流上传文件
	 *
	 *  key 上传到OSS起的名
	 * @param input 文件流
	 *
	 * @throws OSSException
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */
	public static String uploadFile(InputStream input, String fileTypeName) {
		OSSClient client = createOssClient();
		String key = "supplyerp/" + System.currentTimeMillis() + "." + fileTypeName;
		setBucketPublicReadable();
		ObjectMetadata objectMeta = new ObjectMetadata();
		// 判断上传类型，多的可根据自己需求来判定
		if (key.endsWith("xml")) {
			objectMeta.setContentType("text/xml");
		} else if (key.endsWith("jpg")) {
			objectMeta.setContentType("image/jpeg");
		} else if (key.endsWith("png")) {
			objectMeta.setContentType("image/png");
		}
		client.putObject(bucketName, key, input, objectMeta);
		return  key;
	}

	/**
	 * 列出所有Object
	 *
	 *  bucketName
	 */
	public static void listObjects() {
		// 初始化OSSClient
		OSSClient client = createOssClient();
		// 获取指定bucket下的所有Object信息
		ObjectListing listing = client.listObjects(bucketName);
		// 遍历所有Object
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			System.out.println(objectSummary.getKey());
		}
	}

	/**
	 * 下载文件
	 *
	 *  client OSSClient对象
	 *  bucketName Bucket名
	 * Objectkey 上传到OSS起的名
	 *  filename 文件下载到本地保存的路径
	 *
	 * @throws OSSException
	 * @throws ClientException
	 */
	public static void getObject(String key, String fileName) {
		OSSClient client = createOssClient();
		// 获取Object，返回结果为OSSObject对象
		client.getObject(new GetObjectRequest(bucketName, key), new File(fileName));
	}

	/**
	 * 新建bucket
	 *
	 *  bucketName
	 */
	public static void createBucket() {
		OSSClient client = createOssClient();
		// 新建一个Bucket
		client.createBucket(bucketName);

	}
	private static OSSClient createOssClient(){
		DefaultCredentialProvider provider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
		OSSClient client = new OSSClient(endpoint,provider,null);
		return client;
	}

	/**
	 * 把Bucket设置成所有人可读
	 *
	 *  client OSSClient对象
	 *  bucketName Bucket名
	 *
	 * @throws OSSException
	 * @throws ClientException
	 */
	private static void setBucketPublicReadable() throws OSSException, ClientException {
		OSSClient client = createOssClient();
		// 创建bucket
		client.createBucket(bucketName);
		// 设置bucket的访问权限， public-read-write权限
		client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
	}

	public static void main(String[] args) throws FileNotFoundException {
		// String url = uploadFile( "D:/EOS 70D/20160212/IMG_4871.JPG");
		InputStream in = new FileInputStream("C:/X.png");
		String path = uploadFile(in, "png");
		System.out.println(path);
	}
}