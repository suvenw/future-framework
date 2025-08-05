package com.suven.framework.http.interceptor;


import com.suven.framework.http.message.HttpRequestPostMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Title: HttpRequestGetMessage.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http post 接口公共基础参数实现类;
 */
// loginUser = new LoginUser();
//		 loginUser1 = SecurityUtil.getUsername(request);
//		 String projectCode = request.getHeader(PublicConstant.PROJECT_CODE);
//		 loginUser.setProjectCode(projectCode);
//		 String channel = request.getHeader(PublicConstant.CHANNEL);
//		 loginUser.setPlatform(SalaryConstant.CHANNEL_PLATFORM.equals(channel));
//		 loginUser.setUserCode(loginUser1.getOpenId());
//		 loginUser.setUsername(loginUser1.getUserName());
//		 loginUser.setAccessToken(loginUser1.getAccessToken());
//		 loginUser.setHeaderToken(loginUser1.getHeaderToken());
//		 loginUser.setPhone(loginUser1.getPhoneNumber());
//		 loginUser.setUserOpenId(loginUser1.getOpenId());
//		 loginUser.setTenantId(loginUser1.getTenantId());
//		 loginUser.setClientId(loginUser1.getClientId());
//		 if (StringUtils.isBlank(loginUser.getTenantId()) && url.contains("getDataSource")){



public class HttpRequestHeaders extends HttpRequestPostMessage{

	public HttpRequestHeaders(){}
	
    public static HttpRequestHeaders build(){
		return new HttpRequestHeaders();
	}

	public HttpRequestHeaders of( Map<String, String> map)  throws Exception{
		List<Field> fields = FieldUtils.getAllFieldsList(getClass());
		for (Field field : fields) {
			String fieldName = field.getName();
			String value = map.get(fieldName.toLowerCase());
			if (Objects.isNull(value)){
				continue;
			}
			field.setAccessible(true);
			field.set(this, value);
		}
		return this;
	}


}
