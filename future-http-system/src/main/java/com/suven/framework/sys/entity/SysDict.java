package com.suven.framework.sys.entity;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.core.db.ext.DS;

/**
  * @ClassName: SysDict.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:10:09
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description: 后台字典类型表 数据库表对应的实现类
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

@DS(DataSourceModuleName.module_name_sys)
public class SysDict extends BaseByTimeEntity{

private static final long serialVersionUID = 1L;




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





 		/** type 字典类型0为string,1为number  */
 		@ApiDesc(value = "字典类型0为string,1为number", required = 0)
 		private int type;


    public static SysDict build(){
        return new SysDict();
    }

 		public void setDictName( String dictName){
 		 		this.dictName = dictName ; 
 		 		}
 		public SysDict toDictName( String dictName){
 		 		this.dictName = dictName ; 
 		 		 return this ;
 		}

 		public String getDictName(){
 		 		return this.dictName;
 		}
 		public void setDictCode( String dictCode){
 		 		this.dictCode = dictCode ; 
 		 		}
 		public SysDict toDictCode( String dictCode){
 		 		this.dictCode = dictCode ; 
 		 		 return this ;
 		}

 		public String getDictCode(){
 		 		return this.dictCode;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysDict toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}
 		public SysDict toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public void setType( int type){
 		 		this.type = type ; 
 		 		}
 		public SysDict toType( int type){
 		 		this.type = type ; 
 		 		 return this ;
 		}

 		public int getType(){
 		 		return this.type;
 		}
}