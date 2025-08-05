package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * ClassName: SysThirdAccountQueryRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:47
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 第三方登陆表 http业务接口交互数据请求参数实现类
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

public class SysThirdAccountQueryRequestVo extends HttpRequestByIdPageVo{




 		/** sys_user_id 第三方登录id  */
 		@ApiDesc(value = "第三方登录id", required = 0)
 		private String sysUserId;

 		/** avatar 头像  */
 		@ApiDesc(value = "头像", required = 0)
 		private String avatar;

 		/** status 状态（1启用，0不启用）  */
 		@ApiDesc(value = "状态（1启用，0不启用）", required = 0)
 		private int status;

 		/** del_flag 删除状态(0-正常,1-已删除)  */
 		@ApiDesc(value = "删除状态(0-正常,1-已删除)", required = 0)
 		private int delFlag;

 		/** realname 真实姓名  */
 		@ApiDesc(value = "真实姓名", required = 0)
 		private String realname;

 		/** third_user_uuid 第三方账号  */
 		@ApiDesc(value = "第三方账号", required = 0)
 		private String thirdUserUuid;

 		/** third_user_id 第三方app用户账号  */
 		@ApiDesc(value = "第三方app用户账号", required = 0)
 		private String thirdUserId;

 		/** third_type 登录来源  */
 		@ApiDesc(value = "登录来源", required = 0)
 		private String thirdType;




    public static SysThirdAccountQueryRequestVo build(){
        return new SysThirdAccountQueryRequestVo();
    }



 		public void setSysUserId( String sysUserId){
 		 		this.sysUserId = sysUserId ; 
 		 		}

 		public String getSysUserId(){
 		 		return this.sysUserId;
 		}
 		public SysThirdAccountQueryRequestVo toSysUserId( String sysUserId){
 		 		this.sysUserId = sysUserId ; 
 		 		 return this ;
 		}

 		public void setAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		}

 		public String getAvatar(){
 		 		return this.avatar;
 		}
 		public SysThirdAccountQueryRequestVo toAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		 return this ;
 		}

 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public SysThirdAccountQueryRequestVo toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public SysThirdAccountQueryRequestVo toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public void setRealname( String realname){
 		 		this.realname = realname ; 
 		 		}

 		public String getRealname(){
 		 		return this.realname;
 		}
 		public SysThirdAccountQueryRequestVo toRealname( String realname){
 		 		this.realname = realname ; 
 		 		 return this ;
 		}

 		public void setThirdUserUuid( String thirdUserUuid){
 		 		this.thirdUserUuid = thirdUserUuid ; 
 		 		}

 		public String getThirdUserUuid(){
 		 		return this.thirdUserUuid;
 		}
 		public SysThirdAccountQueryRequestVo toThirdUserUuid( String thirdUserUuid){
 		 		this.thirdUserUuid = thirdUserUuid ; 
 		 		 return this ;
 		}

 		public void setThirdUserId( String thirdUserId){
 		 		this.thirdUserId = thirdUserId ; 
 		 		}

 		public String getThirdUserId(){
 		 		return this.thirdUserId;
 		}
 		public SysThirdAccountQueryRequestVo toThirdUserId( String thirdUserId){
 		 		this.thirdUserId = thirdUserId ; 
 		 		 return this ;
 		}

 		public void setThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		}

 		public String getThirdType(){
 		 		return this.thirdType;
 		}
 		public SysThirdAccountQueryRequestVo toThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		 return this ;
 		}





}
