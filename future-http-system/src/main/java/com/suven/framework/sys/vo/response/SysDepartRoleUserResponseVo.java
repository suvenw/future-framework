package com.suven.framework.sys.vo.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @ClassName: SysDepartRoleUserResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:53:58
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 http业务接口交互数据返回参数实现类
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

public class SysDepartRoleUserResponseVo  extends BaseTimeEntity implements Serializable {




 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		@ExcelProperty(value = "用户id")
 		private long userId;

 		/** depart_role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		@ExcelProperty(value = "角色id")
 		private long departRoleId;

 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		@ExcelProperty(value = "部门id")
 		private long departId;




    public static SysDepartRoleUserResponseVo build(){
        return new SysDepartRoleUserResponseVo();
    }

    
     		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysDepartRoleUserResponseVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
    
     		public void setDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		}
 		public SysDepartRoleUserResponseVo toDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		 return this ;
 		}

 		public long getDepartRoleId(){
 		 		return this.departRoleId;
 		}
    
     		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartRoleUserResponseVo toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
    
    
    






}
