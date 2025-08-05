package com.suven.framework.sys.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseBeanClone;
import com.suven.framework.http.api.ApiDesc;


/**
 * @ClassName: SysUserShowResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:37
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 http业务接口交互数据返回参数实现类
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


public class SysUserShowResponseVo extends BaseBeanClone  implements Serializable {

   /** id 对应的业务表的主键id  */
    @ApiDesc(value = "对应的业务表的主键id ")
    private long id;

 		/** username 登录账号  */
 		@ApiDesc(value = "登录账号", required = 0)
 		private String username;
 		/** realname 真实姓名  */
 		@ApiDesc(value = "真实姓名", required = 0)
 		private String realname;
// 		/** password 密码  */
// 		@ApiDesc(value = "密码", required = 0)
// 		private String password;
// 		/** salt md5密码盐  */
// 		@ApiDesc(value = "md5密码盐", required = 0)
// 		private String salt;
// 		/** avatar 头像  */
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

    public static SysUserShowResponseVo build(){
        return new SysUserShowResponseVo();
    }
 		public void setUsername( String username){
 		 		this.username = username ; 
 		 		}

 		public String getUsername(){
 		 		return this.username;
 		}
 		public SysUserShowResponseVo toUsername( String username){
 		 		this.username = username ; 
 		 		 return this ;
 		}
 		public void setRealname( String realname){
 		 		this.realname = realname ; 
 		 		}

 		public String getRealname(){
 		 		return this.realname;
 		}
 		public SysUserShowResponseVo toRealname( String realname){
 		 		this.realname = realname ; 
 		 		 return this ;
 		}
// 		public void setPassword( String password){
// 		 		this.password = password ;
// 		 		}
//
// 		public String getPassword(){
// 		 		return this.password;
// 		}
// 		public SysUserShowResponseVo toPassword( String password){
// 		 		this.password = password ;
// 		 		 return this ;
// 		}
// 		public void setSalt( String salt){
// 		 		this.salt = salt ;
// 		 		}
//
// 		public String getSalt(){
// 		 		return this.salt;
// 		}
// 		public SysUserShowResponseVo toSalt( String salt){
// 		 		this.salt = salt ;
// 		 		 return this ;
// 		}
 		public void setAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		}

 		public String getAvatar(){
 		 		return this.avatar;
 		}
 		public SysUserShowResponseVo toAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		 return this ;
 		}
 		public void setBirthday( Date birthday){
 		 		this.birthday = birthday ; 
 		 		}

 		public Date getBirthday(){
 		 		return this.birthday;
 		}
 		public SysUserShowResponseVo toBirthday( Date birthday){
 		 		this.birthday = birthday ; 
 		 		 return this ;
 		}
 		public void setSex( int sex){
 		 		this.sex = sex ; 
 		 		}

 		public int getSex(){
 		 		return this.sex;
 		}
 		public SysUserShowResponseVo toSex( int sex){
 		 		this.sex = sex ; 
 		 		 return this ;
 		}
 		public void setEmail( String email){
 		 		this.email = email ; 
 		 		}

 		public String getEmail(){
 		 		return this.email;
 		}
 		public SysUserShowResponseVo toEmail( String email){
 		 		this.email = email ; 
 		 		 return this ;
 		}
 		public void setPhone( String phone){
 		 		this.phone = phone ; 
 		 		}

 		public String getPhone(){
 		 		return this.phone;
 		}
 		public SysUserShowResponseVo toPhone( String phone){
 		 		this.phone = phone ; 
 		 		 return this ;
 		}
 		public void setOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		}

 		public String getOrgCode(){
 		 		return this.orgCode;
 		}
 		public SysUserShowResponseVo toOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		 return this ;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public SysUserShowResponseVo toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public SysUserShowResponseVo toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}
 		public void setThirdId( String thirdId){
 		 		this.thirdId = thirdId ; 
 		 		}

 		public String getThirdId(){
 		 		return this.thirdId;
 		}
 		public SysUserShowResponseVo toThirdId( String thirdId){
 		 		this.thirdId = thirdId ; 
 		 		 return this ;
 		}
 		public void setThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		}

 		public String getThirdType(){
 		 		return this.thirdType;
 		}
 		public SysUserShowResponseVo toThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		 return this ;
 		}
 		public void setActivitiSync( int activitiSync){
 		 		this.activitiSync = activitiSync ; 
 		 		}

 		public int getActivitiSync(){
 		 		return this.activitiSync;
 		}
 		public SysUserShowResponseVo toActivitiSync( int activitiSync){
 		 		this.activitiSync = activitiSync ; 
 		 		 return this ;
 		}
 		public void setWorkNo( String workNo){
 		 		this.workNo = workNo ; 
 		 		}

 		public String getWorkNo(){
 		 		return this.workNo;
 		}
 		public SysUserShowResponseVo toWorkNo( String workNo){
 		 		this.workNo = workNo ; 
 		 		 return this ;
 		}
 		public void setPost( String post){
 		 		this.post = post ; 
 		 		}

 		public String getPost(){
 		 		return this.post;
 		}
 		public SysUserShowResponseVo toPost( String post){
 		 		this.post = post ; 
 		 		 return this ;
 		}
 		public void setTelephone( String telephone){
 		 		this.telephone = telephone ; 
 		 		}

 		public String getTelephone(){
 		 		return this.telephone;
 		}
 		public SysUserShowResponseVo toTelephone( String telephone){
 		 		this.telephone = telephone ; 
 		 		 return this ;
 		}
 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysUserShowResponseVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}
 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysUserShowResponseVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}
 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysUserShowResponseVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}
 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysUserShowResponseVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}
 		public void setUserIdentity( int userIdentity){
 		 		this.userIdentity = userIdentity ; 
 		 		}

 		public int getUserIdentity(){
 		 		return this.userIdentity;
 		}
 		public SysUserShowResponseVo toUserIdentity( int userIdentity){
 		 		this.userIdentity = userIdentity ; 
 		 		 return this ;
 		}
 		public void setDepartIds( String departIds){
 		 		this.departIds = departIds ; 
 		 		}

 		public String getDepartIds(){
 		 		return this.departIds;
 		}
 		public SysUserShowResponseVo toDepartIds( String departIds){
 		 		this.departIds = departIds ; 
 		 		 return this ;
 		}
 		public void setRelTenantIds( String relTenantIds){
 		 		this.relTenantIds = relTenantIds ; 
 		 		}

 		public String getRelTenantIds(){
 		 		return this.relTenantIds;
 		}
 		public SysUserShowResponseVo toRelTenantIds( String relTenantIds){
 		 		this.relTenantIds = relTenantIds ; 
 		 		 return this ;
 		}
 		public void setClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		}

 		public String getClientId(){
 		 		return this.clientId;
 		}
 		public SysUserShowResponseVo toClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





}
