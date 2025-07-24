package com.suven.framework.http.message;

/**
 * @Title: IRequestRemote.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http get/post 接口对应参数解释,统一采摘方便后面展开业务实现;
 */

public interface IRequestRemote {
	/**
	 * 
	 * @return
	 */
    String getUrl() ;

	 String getClientIp();
}
