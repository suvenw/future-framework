
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.json.JacksonUtil;
import com.suven.framework.util.json.JsonFormatTool;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.random.RandomUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;


/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:  v1.0.0    修改人： suven  修改日期:  20160110   修改内容: 添加异常信息参数化 
 * </pre>
 */
public abstract class BaseHttpResponseHandlerConverter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());


   protected IResultCodeEnum errorCodeEnum;

    protected HttpServletResponse response;


	public HttpServletResponse getResponse(){
		return this.response;
	}

	/**
	 * 将返回结果数据,或错误信息,组合成统一规范的实现类,返回到前端的实现逻辑
	 * @param responseData
	 */
    protected void writeResponseData(IResponseResult responseResultVo, Object responseData, String... errorParam)  {
		if(responseResultVo == null){
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL);
		}
        //返回转换后的规范的错误码信息;
		if(responseData != null && (responseData instanceof IResultCodeEnum)){//如果是消息类型
			IResultCodeEnum codeEnum = this.formatCodeMsgByErrorCodeEnum(responseData,errorParam);
			responseResultVo.of(codeEnum.getCode(),codeEnum.getMsg());
		}else{//为数据,成功结果数据
			responseResultVo.of(responseData);
//			responseResultVo.setData(responseData);
		}
	}


	/**
	 * 解析出返回代码及返回信息。
	 * @param errorCodeEnum
	 */
    protected IResultCodeEnum formatCodeMsgByErrorCodeEnum(Object errorCodeEnum,String... errorParam) {
		try {
			if(null != errorCodeEnum && errorCodeEnum instanceof IResultCodeEnum){
				IResultCodeEnum codeEnum = (IResultCodeEnum)errorCodeEnum;
				if(errorParam == null || errorParam.length <=0){
					return codeEnum;
				}
				String msg = String.format(codeEnum.getMsg(), new Object[] { errorParam });
				return codeEnum.clone(msg);
			}
		} catch (Exception e) {
			logger.warn("type=Exception, IMsgEnumTypeException =[{}] ", e);
		}
		return SysResultCodeEnum.SYS_UNKOWNN_FAIL;
	}

    /**
     * 设置CDN缓存实现
     * @param response http响应对象
     * @return boolean true 表示设置成功,false 表示设置失败;
     */
	@SuppressWarnings("unchecked")
    protected boolean setCdnCache(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setHeader("Access-Control-Allow-Origin","*");
		int cdnTime =0 ;//Cdn.get(ParamMessage.getRequestRemote().getUrl());
		if (cdnTime == 0) {
			response.addHeader("Cache-Control", "no-cache");
		} else {
			response.setHeader("Cache-Control", "max-age=" + cdnTime);
			response.addDateHeader("Last-Modified", System.currentTimeMillis());
			response.addDateHeader("Date", System.currentTimeMillis());
			response.addDateHeader("Expires", System.currentTimeMillis() + (cdnTime*1000));
			return true;
		}
		return false;
	}

	/**
	 * 设置data数据结果使用AES加密,解密的密匙key,加密的结果sign 缓存到返回头;
	 * Access-Control-sign:sign sign 为由随机数据加密的结果值;
	 * Access-Control-sign:key  key为随机16位大小写+数字的值;
	 * @param response
	 * @return String 返回参与加密的字符串值;
	 */
	@SuppressWarnings("unchecked")
	protected String  initAesHeader(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		String key =  RandomUtils.verifyCode(16,-1);
		String value = RandomUtils.verifyCode(16,0);
		String sign = CryptUtil.aesPassEncrypt( value,key);
		response.setHeader("Access-Control-sign",sign);
		response.setHeader("Access-Control-key",key);
		logger.info("initAesHeader Parameter key:[{}], value:[{}],sign:[{}]",key,value,sign);
		return value;
	}

	/**
	 * 设置将返回data数据结果使用AES加密,再返回去;
	 * Access-Control-sign:sign sign 为由随机数据加密的结果值;
	 * Access-Control-sign:key  key为随机16位大小写+数字的值;
	 * Setter@Getter 为原始返回用户的结果数据,将参与aes加密后,返回给用户 data 值,
	 * aesEncryptKey 由initAesHeader方法实现的value值,
	 * @return String 返回参与加密的字符串值;
	 */
	protected String  converterAesDate(Object data, String aesEncryptKey) {
		if(data == null || aesEncryptKey == null){
			return null;
		}
		String jsonData = JsonUtils.toJson(data);
		String signDate = CryptUtil.aesPassEncrypt( jsonData,aesEncryptKey);
		return signDate;
	}

	/**
	 * 将用户返回ResponseResultVo对象中data属性对象进行加密入里,错误协议时不参与加密
	 * @param responseResultVo 响应结果对象
	 */
	protected void aesDateResultVo(IResponseResult responseResultVo,Object data){
		if(null == responseResultVo ){
			return;
		}
		if(responseResultVo.code() == SysResultCodeEnum.SYS_SUCCESS.getCode() && null != data){
			String aesEncryptKey = this.initAesHeader(response);
			String aesData = this.converterAesDate(data,aesEncryptKey);
			if(aesData == null){
				return;
			}
			responseResultVo.of(true, SysResultCodeEnum.SYS_SUCCESS.getCode(),
					SysResultCodeEnum.SYS_SUCCESS.getMsg(),aesData);
		}
	}

	/**
	 * 将用户返回ResponseResultVo对象进行缓存,错误协议时不参与缓存
	 * @param responseResultVo 响应结果对象
	 */
	protected void cacheDataResultVo(Object responseResultVo){
		/*** ----------将返回结果进行缓存到redis中---------- ***/
		if(null != responseResultVo){
//			if(Cdn.isCdn() && responseResultVo instanceof IResponseResult){
//				IResponseResult vo = (IResponseResult)responseResultVo;
//				ParamMessage.setRedisCacheResponseVo(vo);
//			}
		}
		/*** ----------将返回结果进行缓存到redis中---------- ***/
	}


	/**
     * 统一出口,写流和cdn信息
     */
    protected void writeStream(Object responseResultVo) {

        String smJson = "";
        try {
            ServletOutputStream output = response.getOutputStream();
            if(null == output){
                return ;
            }
            smJson = JacksonUtil.toJson(responseResultVo);
            this.setCdnCache(response);
            byte[] jsonBytes = smJson.getBytes();
            output.write(jsonBytes);
            output.flush();
            output.close();

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e1) {
            logger.error("type=Exception,  ResponseError=[{}]", e1);
        }finally {
//            if(!Env.isProd()){
                logger.warn("type=Success, ResponseMessage=[{}]", JsonFormatTool.formatJson(smJson));
//            }

        }

    }

	/**
	 * 统一出口,先判断是成功协议,将data进行aes加密,再写流和cdn信息
	 */
	protected void writeAesStream(IResponseResult responseResultVo,Object data) {
		/*** ----------将返回结果进行aes加密处理---------- ***/
		this.aesDateResultVo(responseResultVo,data);
		/*** ----------将返回结果进行aes加密处理---------- ***/
		this.writeStream(responseResultVo);

	}





//	/**
//	 * @return 返回 code。
//	 */
//    private int getCode() {
//		return code;
//	}
//
//
//	/**
//	 * @return 返回 msg。
//	 */
//	private String getMsg() {
//		return msg;
//	}


	protected void printErrorLogForRequestMessage(Logger logger,int code ,String msg) {
		HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
		String  json = ParameterMessage.getJsonParseString();
		long netTime = ParameterMessage.getRequestRemote().getNetTime();
		long exeTime = System.currentTimeMillis() - message.getTimes();
		logger.warn("type=Exception, request-url=[{}], code=[{}], msg=[{}],ip=[{}]; FROM-Request-BaseParam = [{}],Request-AttributeParam=[{}], responseEndTime =[{}],netEndTime =[{}]",
				message.getUri(), code, msg, message.getIp(),
				message.toString(),json,exeTime,netTime);
	}
	protected void printSuccessLog(Logger logger) {
		HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
		long netTime = ParameterMessage.getRequestRemote().getNetTime();
		long exeTime = System.currentTimeMillis() - message.getTimes();
		logger.warn("type=Success ,ip= [{}], RequestMessage= [{}],responseEndTime =[{}],netEndTime =[{}]",
				message.getIp(),
				message.toString(),exeTime,netTime);
	}



}
