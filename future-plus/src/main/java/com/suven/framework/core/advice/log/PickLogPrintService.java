package com.suven.framework.core.advice.log;

import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.common.cat.PickLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


/**
 * 日志采摘标答注解 @PickLog 具体业务实现逻辑实现例规划与标准;
 * 1.必须有@Order(1)唯一排序号,保证日志输出的先后顺序;
 * 2.实现类@Service("pickLogPrintService"),必须有唯一的名称,保证spring注入管理
 */

@Order(1)
@Service("pickLogPrintService")
public  class PickLogPrintService implements PickLogService {

    private Logger logger = LoggerFactory.getLogger(PickLogPrintService.class);

    @Override
    public IBeanClone picking(PickLog pickLog, PickLogEven even, IRequestVo requestVo) {
        logger.debug("---------- PickLogPrintServiceImp class  The specific implementation name and sort number must be written ------------------");
        return null;
    }
}
