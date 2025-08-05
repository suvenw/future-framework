package com.suven.framework.sys.dto.response;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTimeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @ClassName: SysUserResponseDto.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:37
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 RPC业务接口交互数据返回实现类
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

public class SysUserTokenResponseDto extends BaseTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysUserTokenResponseDto.class);



	/** username 登录账号  */
	@ApiDesc(value = "登录账号", required = 0)
	private String username;

	/** realname 真实姓名  */
	@ApiDesc(value = "真实姓名", required = 0)
	private String realname;



	/** avatar 头像  */
	@ApiDesc(value = "头像", required = 0)
	private String avatar;

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







	public static SysUserTokenResponseDto build() {
		return new SysUserTokenResponseDto();
	}

	public SysUserTokenResponseDto toUsername(String username) {
		this.username = username;
		return this;
	}

	public SysUserTokenResponseDto toRealname(String realname) {
		this.realname = realname;
		return this;
	}



	public SysUserTokenResponseDto toAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}



	public SysUserTokenResponseDto toThirdId(String thirdId) {
		this.thirdId = thirdId;
		return this;
	}

	public SysUserTokenResponseDto toThirdType(String thirdType) {
		this.thirdType = thirdType;
		return this;
	}

	public SysUserTokenResponseDto toActivitiSync(int activitiSync) {
		this.activitiSync = activitiSync;
		return this;
	}

	public SysUserTokenResponseDto toWorkNo(String workNo) {
		this.workNo = workNo;
		return this;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}



	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public int getActivitiSync() {
		return activitiSync;
	}

	public void setActivitiSync(int activitiSync) {
		this.activitiSync = activitiSync;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}


}
