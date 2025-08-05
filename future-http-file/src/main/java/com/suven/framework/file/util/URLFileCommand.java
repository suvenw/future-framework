package com.suven.framework.file.util;

import com.suven.framework.http.processor.url.IURLCommand;
import com.suven.framework.http.processor.url.annotations.CDN;
import com.suven.framework.http.processor.url.annotations.UrlRemote;

@CDN
@UrlRemote
public class URLFileCommand implements IURLCommand {

    /*  ================ resource 模块 start ============================ */

    /**
     * 按文件批量上传
     */
    public static final String file_post_m_file = "/upload/mFile";

    /**
     * 按文件上传
     */
    public static final String file_post_file = "/upload/file";



    /**
     * 按字节上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_file_byte = "/upload/fileByte";

    /**
     * 按字节上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_file_slave_byte = "/upload/fileSlaveByte";

    /**
     * 按文件分块上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_file_block = "/upload/fileBlock";


    /**
     * 按字节分块上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_byte_block = "/upload/byteBlock";

    /**
     * 按字符串上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_string_block = "/upload/stringBlock";

    /**
     * 按字符串上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_slave_string_block = "/upload/slaveStringBlock";

    /**
     * 删除视频或图片
     */
    public static final String file_post_file_delete = "/upload/delete";

    /**
     * 下载视频
     */
    public static final String file_post_file_download = "/upload/download";

    /**
     * 分块下载视频
     */
    public static final String file_post_file_download_block = "/upload/downBlock";

    /*  ================ resource 模块 end ============================ */

    /**
     * 按文件上传
     */
    public static final String oss_file_post_file = "/upload/ossFile";
    /**
     * 阿里OSS按文件批量上传
     */
    public static final String oss_file_post_m_file = "/upload/ossMFile";


    /**
     * 按字节上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String oss_file_post_file_byte = "/upload/ossByte";

    /**
     * 按字节分块上传
     */
    @UrlRemote(isWhite = false,excludeParam = "blockSize")
    public static final String file_post_byte_m3u8 = "/upload/m3u8" ;

    /**
     * 删除视频或图片
     */
    public static final String oss_file_post_file_delete = "/upload/ossDelete";

    /**
     * 删除视频或图片
     */
    public static final String oss_file_post_delete_list = "/upload/ossDeleteList";

    /**
     * 生成二维码上传到OSS
     */
    public static final String oss_file_post_qrCodeUploadOss = "/upload/qrCodeUploadOss";

    /**
     * 生成二维码带logo上传到OSS
     */
    public static final String oss_file_post_qrLogoUploadOss = "/upload/qrLogoUploadOss";
    /**
     * 生成二维码文件流
     */
    public static final String oss_file_post_qrLogoUploadImg = "/upload/qrLogoUploadImg";


}