
package com.suven.framework.http.processor.url;


import com.suven.framework.http.processor.url.annotations.AnnotationListener;
import com.suven.framework.http.processor.url.annotations.Observer;
import com.suven.framework.http.processor.url.annotations.UrlRemote;
import com.suven.framework.http.message.ParameterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Title: IRequestRemote.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http get/post 接口 url 类型解释类,包括白名单,强制登陆等;
 */

@Component
public class UrlExplain implements AnnotationListener {
	
	private static Logger logger = LoggerFactory.getLogger(UrlExplain.class);

    private static Map<String, UrlRemote> map = new ConcurrentHashMap<>();

    private static final  int VERSION_OLD = 2500;



    /**
     * 只要实现AnnotationListener(一个空接口来的),
     * 这个方法就会被回调, url和cdn示例就会传进来啦
     * @param url url字符串
     * @param urlRemote url字符串上面的CDN注解实例
     */
    @Observer
    public void onVisit(String url, UrlRemote urlRemote) {
    	logger.info("init onVisit key value to UrlExplain Map<url,UrlRemote> key[" + url +"] value = [" + urlRemote +"]");
        map.put( url,urlRemote);
    }
    
   

    /**
     * 验证url 是否白名单中；UrlRemote 默认为白名称
     * @param url url字符串
     */
    public static boolean isWhite(String url) {
        UrlRemote urlex = map.get(url);
        if (urlex == null) {
            return false;
        }
        return urlex.isWhite();
    }

    public static boolean isWhiteVersion(Integer version){
        return null == version || version < VERSION_OLD;
    }

    /**
     * 验证url 是否白名单中；UrlRemote 默认为白名称
     * @param url url字符串
     */
    public static boolean isParamSign(String url) {
        UrlRemote urlex = map.get(url);
        if (urlex == null) {
            return true;
        }
        return urlex.isParamSign();
    }
    /**
     * url 必须登陆，强制验证
     * @param url
     * @return
     */
    public static boolean mustCheck(String url) {
        UrlRemote urlex = map.get(url);
        if (urlex == null) {
            return false;
        }
        return urlex.mustCheck();
    }
    


    private static List<String> excludeParam(String requestRemoteUrl) {
        String url = requestRemoteUrl != null  ? requestRemoteUrl : ParameterMessage.getRequestRemote().getUrl();
        if(url == null){
            throw new RuntimeException("please init requestRemoteUrl value, requestRemoteUrl is null");
        }

        UrlRemote urlEx = map.get(url);
        if (urlEx == null) {
            return null;
        }
        String param = urlEx.excludeParam();
        if(null  == param || "".equals(param)){
            return null;
        }
        return new ArrayList(Arrays.asList(param.split(",")));
    }

    public static void excludeParam(String requestRemoteUrl ,Map paramMap){
        //过滤验证参数;
        List<String> excludeList =  UrlExplain.excludeParam(requestRemoteUrl);
        if(null  != excludeList && !excludeList.isEmpty()){
            for (String exclude : excludeList ) {
                paramMap.remove(exclude);
            }
        }
    }



}
