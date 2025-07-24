package com.suven.framework.core.advice.log;

import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.common.cat.PickLog;

/**
 * 日志逻辑实现接口,日志采摘标答注解 @PickLog 具体业务实现逻辑实现例规划与标准;
 * 实现业务类必须写上具体的实现名称,和排序号;
 *   1.必须有@Order(1)唯一排序号,保证日志输出的先后顺序;
 *   2.实现类@Service("pickLogPrintService"),必须有唯一的名称,保证spring注入管理
 * @Order(1)
 * @Service("pickLogPrintService")
 * public  class PickLogPrintService implements PickLogService {}
 */
public interface PickLogService {

 /** 采摘日志 **/
  IBeanClone picking(PickLog pickLog, PickLogEven pickLogEven , IRequestVo requestVo);


}