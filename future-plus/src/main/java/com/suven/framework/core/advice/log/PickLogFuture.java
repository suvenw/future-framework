package com.suven.framework.core.advice.log;

import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.common.cat.PickLog;

import java.util.concurrent.Callable;

/**
 * 日志线程很处理实现线程类
 */
public  class PickLogFuture implements Callable<IBeanClone> {
    final PickLogListener pickLogListener;
    final PickLogEven pickLogEven;
    final IRequestVo requestVo;
    final PickLog pickLog;

    public PickLogFuture(PickLogListener pickLogListener,PickLog pickLog, PickLogEven pickLogEven) {
        this.pickLogListener = pickLogListener;
        this.pickLog = pickLog;
        this.pickLogEven = pickLogEven;
        this.requestVo = null;
    }
    public PickLogFuture(PickLogListener pickLogListener,PickLog pickLog, PickLogEven pickLogEven,IRequestVo requestVo ) {
        this.pickLogListener = pickLogListener;
        this.pickLog = pickLog;
        this.pickLogEven = pickLogEven;
        this.requestVo = requestVo;
    }

    @Override
    public IBeanClone call() throws Exception {
        pickLogListener.pickFuture(pickLog,pickLogEven,requestVo);
        return null;
    }
}

