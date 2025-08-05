package com.suven.framework.sys.dto.request;


import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;

/**
 * ClassName: SysUserRequestDto.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 RPC业务接口交互数据请求参数实现类
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


public class SysUserRequestDto extends BaseByTimeEntity implements Serializable{


        private Logger logger = LoggerFactory.getLogger(SysUserRequestDto.class);


 		/** username 登录账号  */
 		@ApiDesc(value = "登录账号", required = 0)
 		private String username;

 		/** realname 真实姓名  */
 		@ApiDesc(value = "真实姓名", required = 0)
 		private String realname;

 		/** password 密码  */
 		@ApiDesc(value = "密码", required = 0)
 		private String password;

 		/** salt md5密码盐  */
 		@ApiDesc(value = "md5密码盐", required = 0)
 		private String salt;

 		/** avatar 头像  */
 		@ApiDesc(value = "头像", required = 0)
 		private String avatar;

 		/** birthday 生日  */
 		@ApiDesc(value = "生日", required = 0)
 		private Date birthday;

 		/** sex 性别(0-默认未知,1-男,2-女)  */
 		@ApiDesc(value = "性别(0-默认未知,1-男,2-女)", required = 0)
 		private int sex;

 		/** email 电子邮件  */
 		@ApiDesc(value = "电子邮件", required = 0)
 		private String email;

 		/** phone 电话  */
 		@ApiDesc(value = "电话", required = 0)
 		private String phone;

 		/** org_code 机构编码  */
 		@ApiDesc(value = "机构编码", required = 0)
 		private String orgCode;

 		/** status 状态（1启用，0不启用）  */
 		@ApiDesc(value = "状态（1启用，0不启用）", required = 0)
 		private int status;

 		/** del_flag 删除状态(0-正常,1-已删除)  */
 		@ApiDesc(value = "删除状态(0-正常,1-已删除)", required = 0)
 		private int delFlag;

 		/** third_id 第三方登录的唯一标识  */
 		@ApiDesc(value = "第三方登录的唯一标识", required = 0)
 		private String thirdId;

 		/** third_type 第三方类型  */
 		@ApiDesc(value = "第三方类型", required = 0)
 		private String thirdType;

 		/** activiti_sync 同步工作流引擎(1-同步,0-不同步)  */
 		@ApiDesc(value = "同步工作流引擎(1-同步,0-不同步)", required = 0)
 		private int activitiSync;

 		/** work_no 工号，唯一键  */
 		@ApiDesc(value = "工号，唯一键", required = 0)
 		private String workNo;

 		/** post 职务，关联职务表  */
 		@ApiDesc(value = "职务，关联职务表", required = 0)
 		private String post;

 		/** telephone 座机号  */
 		@ApiDesc(value = "座机号", required = 0)
 		private String telephone;





 		/** user_identity 身份（1普通成员 2上级）  */
 		@ApiDesc(value = "身份（1普通成员 2上级）", required = 0)
 		private int userIdentity;

 		/** depart_ids 负责部门  */
 		@ApiDesc(value = "负责部门", required = 0)
 		private String departIds;

 		/** rel_tenant_ids 多租户标识  */
 		@ApiDesc(value = "多租户标识", required = 0)
 		private String relTenantIds;

 		/** client_id 设备ID  */
 		@ApiDesc(value = "设备ID", required = 0)
 		private String clientId;


        public static SysUserRequestDto build(){
            return new SysUserRequestDto();
        }

 		public void setUsername( String username){
 		 		this.username = username ; 
 		 		}
 		public SysUserRequestDto toUsername( String username){
 		 		this.username = username ; 
 		 		 return this ;
 		}

 		public String getUsername(){
 		 		return this.username;
 		}
 		public void setRealname( String realname){
 		 		this.realname = realname ; 
 		 		}
 		public SysUserRequestDto toRealname( String realname){
 		 		this.realname = realname ; 
 		 		 return this ;
 		}

 		public String getRealname(){
 		 		return this.realname;
 		}
 		public void setPassword( String password){
 		 		this.password = password ; 
 		 		}
 		public SysUserRequestDto toPassword( String password){
 		 		this.password = password ; 
 		 		 return this ;
 		}

 		public String getPassword(){
 		 		return this.password;
 		}
 		public void setSalt( String salt){
 		 		this.salt = salt ; 
 		 		}
 		public SysUserRequestDto toSalt( String salt){
 		 		this.salt = salt ; 
 		 		 return this ;
 		}

 		public String getSalt(){
 		 		return this.salt;
 		}
 		public void setAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		}
 		public SysUserRequestDto toAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		 return this ;
 		}

 		public String getAvatar(){
 		 		return this.avatar;
 		}
 		public void setBirthday( Date birthday){
 		 		this.birthday = birthday ; 
 		 		}
 		public SysUserRequestDto toBirthday( Date birthday){
 		 		this.birthday = birthday ; 
 		 		 return this ;
 		}

 		public Date getBirthday(){
 		 		return this.birthday;
 		}
 		public void setSex( int sex){
 		 		this.sex = sex ; 
 		 		}
 		public SysUserRequestDto toSex( int sex){
 		 		this.sex = sex ; 
 		 		 return this ;
 		}

 		public int getSex(){
 		 		return this.sex;
 		}
 		public void setEmail( String email){
 		 		this.email = email ; 
 		 		}
 		public SysUserRequestDto toEmail( String email){
 		 		this.email = email ; 
 		 		 return this ;
 		}

 		public String getEmail(){
 		 		return this.email;
 		}
 		public void setPhone( String phone){
 		 		this.phone = phone ; 
 		 		}
 		public SysUserRequestDto toPhone( String phone){
 		 		this.phone = phone ; 
 		 		 return this ;
 		}

 		public String getPhone(){
 		 		return this.phone;
 		}
 		public void setOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		}
 		public SysUserRequestDto toOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		 return this ;
 		}

 		public String getOrgCode(){
 		 		return this.orgCode;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}
 		public SysUserRequestDto toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}
 		public SysUserRequestDto toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public void setThirdId( String thirdId){
 		 		this.thirdId = thirdId ; 
 		 		}
 		public SysUserRequestDto toThirdId( String thirdId){
 		 		this.thirdId = thirdId ; 
 		 		 return this ;
 		}

 		public String getThirdId(){
 		 		return this.thirdId;
 		}
 		public void setThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		}
 		public SysUserRequestDto toThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		 return this ;
 		}

 		public String getThirdType(){
 		 		return this.thirdType;
 		}
 		public void setActivitiSync( int activitiSync){
 		 		this.activitiSync = activitiSync ; 
 		 		}
 		public SysUserRequestDto toActivitiSync( int activitiSync){
 		 		this.activitiSync = activitiSync ; 
 		 		 return this ;
 		}

 		public int getActivitiSync(){
 		 		return this.activitiSync;
 		}
 		public void setWorkNo( String workNo){
 		 		this.workNo = workNo ; 
 		 		}
 		public SysUserRequestDto toWorkNo( String workNo){
 		 		this.workNo = workNo ; 
 		 		 return this ;
 		}

 		public String getWorkNo(){
 		 		return this.workNo;
 		}
 		public void setPost( String post){
 		 		this.post = post ; 
 		 		}
 		public SysUserRequestDto toPost( String post){
 		 		this.post = post ; 
 		 		 return this ;
 		}

 		public String getPost(){
 		 		return this.post;
 		}
 		public void setTelephone( String telephone){
 		 		this.telephone = telephone ; 
 		 		}
 		public SysUserRequestDto toTelephone( String telephone){
 		 		this.telephone = telephone ; 
 		 		 return this ;
 		}

 		public String getTelephone(){
 		 		return this.telephone;
 		}
 		public void setUserIdentity( int userIdentity){
 		 		this.userIdentity = userIdentity ; 
 		 		}
 		public SysUserRequestDto toUserIdentity( int userIdentity){
 		 		this.userIdentity = userIdentity ; 
 		 		 return this ;
 		}

 		public int getUserIdentity(){
 		 		return this.userIdentity;
 		}
 		public void setDepartIds( String departIds){
 		 		this.departIds = departIds ; 
 		 		}
 		public SysUserRequestDto toDepartIds( String departIds){
 		 		this.departIds = departIds ; 
 		 		 return this ;
 		}

 		public String getDepartIds(){
 		 		return this.departIds;
 		}
 		public void setRelTenantIds( String relTenantIds){
 		 		this.relTenantIds = relTenantIds ; 
 		 		}
 		public SysUserRequestDto toRelTenantIds( String relTenantIds){
 		 		this.relTenantIds = relTenantIds ; 
 		 		 return this ;
 		}

 		public String getRelTenantIds(){
 		 		return this.relTenantIds;
 		}
 		public void setClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		}
 		public SysUserRequestDto toClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}

 		public String getClientId(){
 		 		return this.clientId;
 		}




	


}
