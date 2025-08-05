package com.suven.framework.sys.vo.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @ClassName: SysLogResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:19
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 http业务接口交互数据返回参数实现类
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

public class SysLogResponseVo  extends BaseByTimeEntity implements Serializable {




 		/** log_type 日志类型（1登录日志，2操作日志）  */
 		@ApiDesc(value = "日志类型（1登录日志，2操作日志）", required = 0)
 		@ExcelProperty(value = "日志类型（1登录日志，2操作日志）")
 		private int logType;

 		/** log_content 日志内容  */
 		@ApiDesc(value = "日志内容", required = 0)
 		@ExcelProperty(value = "日志内容")
 		private String logContent;

 		/** operate_type 操作类型  */
 		@ApiDesc(value = "操作类型", required = 0)
 		@ExcelProperty(value = "操作类型")
 		private int operateType;

 		/** user_id 操作用户账号  */
 		@ApiDesc(value = "操作用户账号", required = 0)
 		@ExcelProperty(value = "操作用户账号")
 		private String userId;

 		/** user_name 操作用户名称  */
 		@ApiDesc(value = "操作用户名称", required = 0)
 		@ExcelProperty(value = "操作用户名称")
 		private String userName;

 		/** ip IP  */
 		@ApiDesc(value = "IP", required = 0)
 		@ExcelProperty(value = "IP")
 		private String ip;

 		/** method 请求java方法  */
 		@ApiDesc(value = "请求java方法", required = 0)
 		@ExcelProperty(value = "请求java方法")
 		private String method;

 		/** request_url 请求路径  */
 		@ApiDesc(value = "请求路径", required = 0)
 		@ExcelProperty(value = "请求路径")
 		private String requestUrl;

 		/** request_param 请求参数  */
 		@ApiDesc(value = "请求参数", required = 0)
 		@ExcelProperty(value = "请求参数")
 		private String requestParam;

 		/** request_type 请求类型  */
 		@ApiDesc(value = "请求类型", required = 0)
 		@ExcelProperty(value = "请求类型")
 		private String requestType;

 		/** cost_time 耗时  */
 		@ApiDesc(value = "耗时", required = 0)
 		@ExcelProperty(value = "耗时")
 		private long costTime;






    public static SysLogResponseVo build(){
        return new SysLogResponseVo();
    }

    
     		public void setLogType( int logType){
 		 		this.logType = logType ; 
 		 		}
 		public SysLogResponseVo toLogType( int logType){
 		 		this.logType = logType ; 
 		 		 return this ;
 		}

 		public int getLogType(){
 		 		return this.logType;
 		}
    
     		public void setLogContent( String logContent){
 		 		this.logContent = logContent ; 
 		 		}
 		public SysLogResponseVo toLogContent( String logContent){
 		 		this.logContent = logContent ; 
 		 		 return this ;
 		}

 		public String getLogContent(){
 		 		return this.logContent;
 		}
    
     		public void setOperateType( int operateType){
 		 		this.operateType = operateType ; 
 		 		}
 		public SysLogResponseVo toOperateType( int operateType){
 		 		this.operateType = operateType ; 
 		 		 return this ;
 		}

 		public int getOperateType(){
 		 		return this.operateType;
 		}
    
     		public void setUserId( String userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysLogResponseVo toUserId( String userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public String getUserId(){
 		 		return this.userId;
 		}
    
     		public void setUserName( String userName){
 		 		this.userName = userName ; 
 		 		}
 		public SysLogResponseVo toUserName( String userName){
 		 		this.userName = userName ; 
 		 		 return this ;
 		}

 		public String getUserName(){
 		 		return this.userName;
 		}
    
     		public void setIp( String ip){
 		 		this.ip = ip ; 
 		 		}
 		public SysLogResponseVo toIp( String ip){
 		 		this.ip = ip ; 
 		 		 return this ;
 		}

 		public String getIp(){
 		 		return this.ip;
 		}
    
     		public void setMethod( String method){
 		 		this.method = method ; 
 		 		}
 		public SysLogResponseVo toMethod( String method){
 		 		this.method = method ; 
 		 		 return this ;
 		}

 		public String getMethod(){
 		 		return this.method;
 		}
    
     		public void setRequestUrl( String requestUrl){
 		 		this.requestUrl = requestUrl ; 
 		 		}
 		public SysLogResponseVo toRequestUrl( String requestUrl){
 		 		this.requestUrl = requestUrl ; 
 		 		 return this ;
 		}

 		public String getRequestUrl(){
 		 		return this.requestUrl;
 		}
    
     		public void setRequestParam( String requestParam){
 		 		this.requestParam = requestParam ; 
 		 		}
 		public SysLogResponseVo toRequestParam( String requestParam){
 		 		this.requestParam = requestParam ; 
 		 		 return this ;
 		}

 		public String getRequestParam(){
 		 		return this.requestParam;
 		}
    
     		public void setRequestType( String requestType){
 		 		this.requestType = requestType ; 
 		 		}
 		public SysLogResponseVo toRequestType( String requestType){
 		 		this.requestType = requestType ; 
 		 		 return this ;
 		}

 		public String getRequestType(){
 		 		return this.requestType;
 		}
    
     		public void setCostTime( long costTime){
 		 		this.costTime = costTime ; 
 		 		}
 		public SysLogResponseVo toCostTime( long costTime){
 		 		this.costTime = costTime ; 
 		 		 return this ;
 		}

 		public long getCostTime(){
 		 		return this.costTime;
 		}
    
    
    
    
    






}
