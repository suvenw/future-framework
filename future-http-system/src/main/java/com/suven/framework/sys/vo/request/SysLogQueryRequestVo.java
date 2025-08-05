package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * @ClassName: SysLogQueryRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:19
 * @Version 版本: v1.0.0
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

public class SysLogQueryRequestVo extends HttpRequestByIdPageVo{




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

 		/** create_by 创建人  */
 		@ApiDesc(value = "创建人", required = 0)
 		private String createBy;

 		/** create_time 创建时间  */
 		@ApiDesc(value = "创建时间", required = 0)
 		private Date createTime;

 		/** update_by 更新人  */
 		@ApiDesc(value = "更新人", required = 0)
 		private String updateBy;

 		/** update_time 更新时间  */
 		@ApiDesc(value = "更新时间", required = 0)
 		private Date updateTime;




    public static SysLogQueryRequestVo build(){
        return new SysLogQueryRequestVo();
    }



 		public void setLogType( int logType){
 		 		this.logType = logType ; 
 		 		}

 		public int getLogType(){
 		 		return this.logType;
 		}
 		public SysLogQueryRequestVo toLogType( int logType){
 		 		this.logType = logType ; 
 		 		 return this ;
 		}

 		public void setLogContent( String logContent){
 		 		this.logContent = logContent ; 
 		 		}

 		public String getLogContent(){
 		 		return this.logContent;
 		}
 		public SysLogQueryRequestVo toLogContent( String logContent){
 		 		this.logContent = logContent ; 
 		 		 return this ;
 		}

 		public void setOperateType( int operateType){
 		 		this.operateType = operateType ; 
 		 		}

 		public int getOperateType(){
 		 		return this.operateType;
 		}
 		public SysLogQueryRequestVo toOperateType( int operateType){
 		 		this.operateType = operateType ; 
 		 		 return this ;
 		}

 		public void setUserId( String userId){
 		 		this.userId = userId ; 
 		 		}

 		public String getUserId(){
 		 		return this.userId;
 		}
 		public SysLogQueryRequestVo toUserId( String userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public void setUserName( String userName){
 		 		this.userName = userName ; 
 		 		}

 		public String getUserName(){
 		 		return this.userName;
 		}
 		public SysLogQueryRequestVo toUserName( String userName){
 		 		this.userName = userName ; 
 		 		 return this ;
 		}

 		public void setIp( String ip){
 		 		this.ip = ip ; 
 		 		}

 		public String getIp(){
 		 		return this.ip;
 		}
 		public SysLogQueryRequestVo toIp( String ip){
 		 		this.ip = ip ; 
 		 		 return this ;
 		}

 		public void setMethod( String method){
 		 		this.method = method ; 
 		 		}

 		public String getMethod(){
 		 		return this.method;
 		}
 		public SysLogQueryRequestVo toMethod( String method){
 		 		this.method = method ; 
 		 		 return this ;
 		}

 		public void setRequestUrl( String requestUrl){
 		 		this.requestUrl = requestUrl ; 
 		 		}

 		public String getRequestUrl(){
 		 		return this.requestUrl;
 		}
 		public SysLogQueryRequestVo toRequestUrl( String requestUrl){
 		 		this.requestUrl = requestUrl ; 
 		 		 return this ;
 		}

 		public void setRequestParam( String requestParam){
 		 		this.requestParam = requestParam ; 
 		 		}

 		public String getRequestParam(){
 		 		return this.requestParam;
 		}
 		public SysLogQueryRequestVo toRequestParam( String requestParam){
 		 		this.requestParam = requestParam ; 
 		 		 return this ;
 		}

 		public void setRequestType( String requestType){
 		 		this.requestType = requestType ; 
 		 		}

 		public String getRequestType(){
 		 		return this.requestType;
 		}
 		public SysLogQueryRequestVo toRequestType( String requestType){
 		 		this.requestType = requestType ; 
 		 		 return this ;
 		}

 		public void setCostTime( long costTime){
 		 		this.costTime = costTime ; 
 		 		}

 		public long getCostTime(){
 		 		return this.costTime;
 		}
 		public SysLogQueryRequestVo toCostTime( long costTime){
 		 		this.costTime = costTime ; 
 		 		 return this ;
 		}

 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysLogQueryRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysLogQueryRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysLogQueryRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysLogQueryRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}





}
