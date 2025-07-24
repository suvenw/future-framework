package com.suven.framework.core.advice.log;

import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.common.cat.PickLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件监听器
 */
public interface PickLogListener {

 static Logger logger = LoggerFactory.getLogger(PickLogListener.class);




 boolean pickFuture(PickLog pickLog, PickLogEven pickLogEven , IRequestVo requestVo);
}