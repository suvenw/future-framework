package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * ClassName: SysLogRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:19
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 http业务接口交互数据请求参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class SysLogRequestVo extends HttpRequestByIdPageVo{





 		/** log_type 日志类型（1登录日志，2操作日志）  */
 		@ApiDesc(value = "日志类型（1登录日志，2操作日志）", required = 0)
 		private int logType;

 		/** log_content 日志内容  */
 		@ApiDesc(value = "日志内容", required = 0)
 		private String logContent;

 		/** operate_type 操作类型  */
 		@ApiDesc(value = "操作类型", required = 0)
 		private int operateType;

 		/** user_id 操作用户账号  */
 		@ApiDesc(value = "操作用户账号", required = 0)
 		private String userId;

 		/** user_name 操作用户名称  */
 		@ApiDesc(value = "操作用户名称", required = 0)
 		private String userName;

 		/** ip IP  */
 		@ApiDesc(value = "IP", required = 0)
 		private String ip;

 		/** method 请求java方法  */
 		@ApiDesc(value = "请求java方法", required = 0)
 		private String method;

 		/** request_url 请求路径  */
 		@ApiDesc(value = "请求路径", required = 0)
 		private String requestUrl;

 		/** request_param 请求参数  */
 		@ApiDesc(value = "请求参数", required = 0)
 		private String requestParam;

 		/** request_type 请求类型  */
 		@ApiDesc(value = "请求类型", required = 0)
 		private String requestType;

 		/** cost_time 耗时  */
 		@ApiDesc(value = "耗时", required = 0)
 		private long costTime;









    public static SysLogRequestVo build(){
        return new SysLogRequestVo();
    }




 		public void setLogType( int logType){
 		 		this.logType = logType ; 
 		 		}
 		public SysLogRequestVo toLogType( int logType){
 		 		this.logType = logType ; 
 		 		 return this ;
 		}

 		public int getLogType(){
 		 		return this.logType;
 		}

 		public void setLogContent( String logContent){
 		 		this.logContent = logContent ; 
 		 		}
 		public SysLogRequestVo toLogContent( String logContent){
 		 		this.logContent = logContent ; 
 		 		 return this ;
 		}

 		public String getLogContent(){
 		 		return this.logContent;
 		}

 		public void setOperateType( int operateType){
 		 		this.operateType = operateType ; 
 		 		}
 		public SysLogRequestVo toOperateType( int operateType){
 		 		this.operateType = operateType ; 
 		 		 return this ;
 		}

 		public int getOperateType(){
 		 		return this.operateType;
 		}

 		public void setUserId( String userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysLogRequestVo toUserId( String userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public String getUserId(){
 		 		return this.userId;
 		}

 		public void setUserName( String userName){
 		 		this.userName = userName ; 
 		 		}
 		public SysLogRequestVo toUserName( String userName){
 		 		this.userName = userName ; 
 		 		 return this ;
 		}

 		public String getUserName(){
 		 		return this.userName;
 		}

 		public void setIp( String ip){
 		 		this.ip = ip ; 
 		 		}
 		public SysLogRequestVo toIp( String ip){
 		 		this.ip = ip ; 
 		 		 return this ;
 		}

 		public String getIp(){
 		 		return this.ip;
 		}

 		public void setMethod( String method){
 		 		this.method = method ; 
 		 		}
 		public SysLogRequestVo toMethod( String method){
 		 		this.method = method ; 
 		 		 return this ;
 		}

 		public String getMethod(){
 		 		return this.method;
 		}

 		public void setRequestUrl( String requestUrl){
 		 		this.requestUrl = requestUrl ; 
 		 		}
 		public SysLogRequestVo toRequestUrl( String requestUrl){
 		 		this.requestUrl = requestUrl ; 
 		 		 return this ;
 		}

 		public String getRequestUrl(){
 		 		return this.requestUrl;
 		}

 		public void setRequestParam( String requestParam){
 		 		this.requestParam = requestParam ; 
 		 		}
 		public SysLogRequestVo toRequestParam( String requestParam){
 		 		this.requestParam = requestParam ; 
 		 		 return this ;
 		}

 		public String getRequestParam(){
 		 		return this.requestParam;
 		}

 		public void setRequestType( String requestType){
 		 		this.requestType = requestType ; 
 		 		}
 		public SysLogRequestVo toRequestType( String requestType){
 		 		this.requestType = requestType ; 
 		 		 return this ;
 		}

 		public String getRequestType(){
 		 		return this.requestType;
 		}

 		public void setCostTime( long costTime){
 		 		this.costTime = costTime ; 
 		 		}
 		public SysLogRequestVo toCostTime( long costTime){
 		 		this.costTime = costTime ; 
 		 		 return this ;
 		}

 		public long getCostTime(){
 		 		return this.costTime;
 		}








}
