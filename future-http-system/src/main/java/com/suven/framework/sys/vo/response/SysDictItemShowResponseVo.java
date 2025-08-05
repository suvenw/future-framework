package com.suven.framework.sys.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseBeanClone;
import com.suven.framework.http.api.ApiDesc;


/**
 * @ClassName: SysDictItemShowResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:15
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 数据字典明细表 http业务接口交互数据返回参数实现类
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


public class SysDictItemShowResponseVo extends BaseBeanClone  implements Serializable {

   /** id 对应的业务表的主键id  */
    @ApiDesc(value = "对应的业务表的主键id ")
    private long id;

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
 		/** create_by   */
 		@ApiDesc(value = "", required = 0)
 		private String createBy;
 		/** create_time   */
 		@ApiDesc(value = "", required = 0)
 		private Date createTime;
 		/** update_by   */
 		@ApiDesc(value = "", required = 0)
 		private String updateBy;
 		/** update_time   */
 		@ApiDesc(value = "", required = 0)
 		private Date updateTime;

    public static SysDictItemShowResponseVo build(){
        return new SysDictItemShowResponseVo();
    }
 		public void setDictId( String dictId){
 		 		this.dictId = dictId ; 
 		 		}

 		public String getDictId(){
 		 		return this.dictId;
 		}
 		public SysDictItemShowResponseVo toDictId( String dictId){
 		 		this.dictId = dictId ; 
 		 		 return this ;
 		}
 		public void setItemText( String itemText){
 		 		this.itemText = itemText ; 
 		 		}

 		public String getItemText(){
 		 		return this.itemText;
 		}
 		public SysDictItemShowResponseVo toItemText( String itemText){
 		 		this.itemText = itemText ; 
 		 		 return this ;
 		}
 		public void setItemValue( String itemValue){
 		 		this.itemValue = itemValue ; 
 		 		}

 		public String getItemValue(){
 		 		return this.itemValue;
 		}
 		public SysDictItemShowResponseVo toItemValue( String itemValue){
 		 		this.itemValue = itemValue ; 
 		 		 return this ;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public SysDictItemShowResponseVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}
 		public void setSortOrder( int sortOrder){
 		 		this.sortOrder = sortOrder ; 
 		 		}

 		public int getSortOrder(){
 		 		return this.sortOrder;
 		}
 		public SysDictItemShowResponseVo toSortOrder( int sortOrder){
 		 		this.sortOrder = sortOrder ; 
 		 		 return this ;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public SysDictItemShowResponseVo toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}
 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysDictItemShowResponseVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}
 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDictItemShowResponseVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}
 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysDictItemShowResponseVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}
 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDictItemShowResponseVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





}
