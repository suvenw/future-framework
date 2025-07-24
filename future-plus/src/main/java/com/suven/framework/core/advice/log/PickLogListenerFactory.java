package com.suven.framework.core.advice.log;

import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.common.cat.PickLog;
import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.util.json.JsonUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


@Service("pickLogListenerFactory")
public  class PickLogListenerFactory implements PickLogListener {

   private Logger logger = LoggerFactory.getLogger(PickLogListenerFactory.class);

    List<PickLogService> list = new ArrayList<>();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 业务功能实现调用的方法
     * @param pickLog
     * @param even
     * @param requestVo
     * @return
     */
    public boolean picking(PickLog pickLog, PickLogEven even, IRequestVo requestVo) {
        logger.info("---------- PickLogServiceHandle ------------------");
        if(null == list || list.isEmpty()){
            return false;
        }
        list.forEach(pickLogService ->{
            pickLogService.picking(pickLog,even,requestVo);
        });

        return true;
    }

    /***
     * 工作管理线程调用的方法
     * @param pickLog
     * @param pickLogEven
     * @param requestVo
     * @return
     */

    public boolean pickFuture(  PickLog pickLog,  PickLogEven pickLogEven , IRequestVo requestVo){
        logger.debug("===================== PickLogListener ===================== "+"\n"
                + JsonUtils.toJson(pickLogEven));
        logger.debug("\n"+ "===================== PickLogListener ===================== ");
        return this.picking(pickLog,pickLogEven,requestVo);
    }


    @PostConstruct
    public void initPickLogService() {
        Set<Class<? extends PickLogService>> classList = ReflectionsScan.reflections.getSubTypesOf(PickLogService.class);
        if(null == classList || classList.isEmpty()){
            return ;
        }
        TreeMap<Integer,PickLogService > handlerTreeMap = new TreeMap();
        //缺省时,按类名称升序排序
        TreeMap<String,PickLogService > sortTreeMap = new TreeMap();

        for (Class<?> clazz : classList){
            try {
                if(PickLogService.class.isAssignableFrom(clazz) ){

                    PickLogService pickLogService = (PickLogService)applicationContext.getBean(clazz);
                    Order interceptor = AnnotationUtils.findAnnotation(clazz, Order.class);

                    //没有排序编号;随机排序;
                    if(null == interceptor){
                        sortTreeMap.put(pickLogService.getClass().getName(),pickLogService);
                        continue;
                    }
                    //按order升序排序
                    handlerTreeMap.put(interceptor.value(),pickLogService);

                }

            }catch (Exception e){
                logger.error("===================== initPickLogServiceError ===================== ",e);
            }
        }
        list.addAll(0,handlerTreeMap.values());
        list.addAll(sortTreeMap.values());
    }
}
