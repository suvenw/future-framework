package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * @ClassName: SysDictQueryRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:09
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 后台字典类型表 http业务接口交互数据请求参数实现类
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

public class SysDictQueryRequestVo extends HttpRequestByIdPageVo{




 		/** dict_name 字典名称  */
 		@ApiDesc(value = "字典名称", required = 0)
 		private String dictName;

 		/** dict_code 字典编码  */
 		@ApiDesc(value = "字典编码", required = 0)
 		private String dictCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** del_flag 删除状态(1-正常,0-已删除)  */
 		@ApiDesc(value = "删除状态(1-正常,0-已删除)", required = 0)
 		private int delFlag;

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

 		/** type 字典类型0为string,1为number  */
 		@ApiDesc(value = "字典类型0为string,1为number", required = 0)
 		private int type;




    public static SysDictQueryRequestVo build(){
        return new SysDictQueryRequestVo();
    }



 		public void setDictName( String dictName){
 		 		this.dictName = dictName ; 
 		 		}

 		public String getDictName(){
 		 		return this.dictName;
 		}
 		public SysDictQueryRequestVo toDictName( String dictName){
 		 		this.dictName = dictName ; 
 		 		 return this ;
 		}

 		public void setDictCode( String dictCode){
 		 		this.dictCode = dictCode ; 
 		 		}

 		public String getDictCode(){
 		 		return this.dictCode;
 		}
 		public SysDictQueryRequestVo toDictCode( String dictCode){
 		 		this.dictCode = dictCode ; 
 		 		 return this ;
 		}

 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public SysDictQueryRequestVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public SysDictQueryRequestVo toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysDictQueryRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDictQueryRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysDictQueryRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDictQueryRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}

 		public void setType( int type){
 		 		this.type = type ; 
 		 		}

 		public int getType(){
 		 		return this.type;
 		}
 		public SysDictQueryRequestVo toType( int type){
 		 		this.type = type ; 
 		 		 return this ;
 		}





}
