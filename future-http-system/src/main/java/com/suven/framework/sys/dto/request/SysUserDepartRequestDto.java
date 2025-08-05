package com.suven.framework.sys.dto.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTimeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
* @ClassName: SysUserDepartRequestDto.java
* Description: 用户部门表的数据交互处理类
* @author xxx.xxx
* @date   2019-11-27 17:49:58
* @version V1.0.0
* <p>
    * ----------------------------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    * ----------------------------------------------------------------------------
    * </p>
*/
public class SysUserDepartRequestDto extends BaseTimeEntity implements Serializable{


        private Logger logger = LoggerFactory.getLogger(SysUserDepartRequestDto.class);


 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depId 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;

		/** 用户id集合  */
		@ApiDesc(value = "用户id集合", required = 0)
		private List<Long> userIdList;




        public static SysUserDepartRequestDto build(){
            return new SysUserDepartRequestDto();
        }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserDepartRequestDto toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepartRequestDto toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}

		public List<Long> getUserIdList() {
			return userIdList;
		}
	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
		public SysUserDepartRequestDto toUserIdList(List<Long> userIdList) {
			this.userIdList = userIdList;
			return this;
		}
}
