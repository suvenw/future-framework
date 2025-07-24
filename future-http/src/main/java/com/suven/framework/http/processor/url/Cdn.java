
package com.suven.framework.http.processor.url;


import com.suven.framework.http.processor.url.annotations.AnnotationListener;
import com.suven.framework.http.processor.url.annotations.CDN;
import com.suven.framework.http.processor.url.annotations.Observer;
import com.suven.framework.http.message.ParameterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alex on 2014/8/20
 */
@Component
public class Cdn implements AnnotationListener {
	
	private static Logger logger = LoggerFactory
			.getLogger(Cdn.class);

    private static Map<String, Integer> map = new ConcurrentHashMap<>();


    /**
     * 只要实现AnnotationListener(一个空接口来的),
     * 这个方法就会被回调, url和cdn示例就会传进来啦
     * @param url url字符串
     * @param cdn url字符串上面的CDN注解实例
     */
    @Observer
    private void onVisit(String url, CDN cdn) {
        if(url == null){
            return ;
        }
        int num = cdn.value();
        int cdnTime = (int) cdn.unit().toSeconds(num);
        logger.info("init onVisit key value to Cdn Map<url,cdnTime> key=:" + url +"value =:" + cdn.value());
        map.put(url ,cdnTime);
    }
    
//    private String getUrlPrefix(String url){
//    	String rs = "errorUrlPrefix";
//    	
//    	if("mfx".equals(PropertiesHelper.getProp("project")))
//    		rs=  "/mfx";
//    	else if("show".equals(PropertiesHelper.getProp("project")))
//    		rs= "";
//    	else if("mpsbuss".equals(PropertiesHelper.getProp("project")))
//    		rs= "";
//    	else
//    		throw new SystemLogicException();
//    	
//    	logger.info("Start to add prefix for url[{}], prefix[{}]", url, rs);
//    	return rs;
//    }

    public static boolean isCdn(){
       String url = ParameterMessage.getRequestRemote().getUrl();
        return isCdn(url);
    }

    public static boolean isCdn(String url){
        if(url == null){
            return false;
        }
        Integer cdnTime = map.get(url);
        return (null != cdnTime && cdnTime > 0);
    }
    /**
     * 得到此url上面对应的时间 秒为单位
     * @param url url字符串
     * @return 得到此url上面对应的时间
     */
    public static int get(String url) {
        if(url == null){
            return 0;
        }
        Integer cdnTime = map.get(url);
        if (cdnTime == null) {
            return 0;
        }
        return cdnTime;
    }

}
