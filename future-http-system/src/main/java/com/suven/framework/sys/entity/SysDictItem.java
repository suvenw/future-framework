package com.suven.framework.sys.entity;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.core.db.ext.DS;

/**
  * @ClassName: SysDictItem.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:10:15
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description: 数据字典明细表 数据库表对应的实现类
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
public class SysDictItem extends BaseByTimeEntity{

private static final long serialVersionUID = 1L;




 		/** dict_id 字典id  */
 		@ApiDesc(value = "字典id", required = 0)
 		private String dictId;

 		/** item_text 字典项文本  */
 		@ApiDesc(value = "字典项文本", required = 0)
 		private String itemText;

 		/** item_value 字典项值  */
 		@ApiDesc(value = "字典项值", required = 0)
 		private String itemValue;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** sort_order 排序  */
 		@ApiDesc(value = "排序", required = 0)
 		private int sortOrder;

 		/** status 状态（1启用 0不启用）  */
 		@ApiDesc(value = "状态（1启用 0不启用）", required = 0)
 		private int status;






    public static SysDictItem build(){
        return new SysDictItem();
    }

 		public void setDictId( String dictId){
 		 		this.dictId = dictId ; 
 		 		}
 		public SysDictItem toDictId( String dictId){
 		 		this.dictId = dictId ; 
 		 		 return this ;
 		}

 		public String getDictId(){
 		 		return this.dictId;
 		}
 		public void setItemText( String itemText){
 		 		this.itemText = itemText ; 
 		 		}
 		public SysDictItem toItemText( String itemText){
 		 		this.itemText = itemText ; 
 		 		 return this ;
 		}

 		public String getItemText(){
 		 		return this.itemText;
 		}
 		public void setItemValue( String itemValue){
 		 		this.itemValue = itemValue ; 
 		 		}
 		public SysDictItem toItemValue( String itemValue){
 		 		this.itemValue = itemValue ; 
 		 		 return this ;
 		}

 		public String getItemValue(){
 		 		return this.itemValue;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysDictItem toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public void setSortOrder( int sortOrder){
 		 		this.sortOrder = sortOrder ; 
 		 		}
 		public SysDictItem toSortOrder( int sortOrder){
 		 		this.sortOrder = sortOrder ; 
 		 		 return this ;
 		}

 		public int getSortOrder(){
 		 		return this.sortOrder;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}
 		public SysDictItem toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public int getStatus(){
 		 		return this.status;
 		}
}