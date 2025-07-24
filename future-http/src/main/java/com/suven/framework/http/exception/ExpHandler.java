/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.exception;

/**
 * <pre>
 * 异常处理器接口。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface ExpHandler{
	/**
	 * 重试。
	 */
    void retry();
}
