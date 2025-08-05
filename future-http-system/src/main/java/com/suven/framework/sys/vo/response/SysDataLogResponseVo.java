package com.suven.framework.sys.vo.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * ClassName: SysDataLogResponseVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description:  http业务接口交互数据返回参数实现类
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

public class SysDataLogResponseVo  extends BaseByTimeEntity implements Serializable {








 		/** data_table 表名  */
 		@ApiDesc(value = "表名", required = 0)
 		@ExcelProperty(value = "表名")
 		private String dataTable;

 		/** data_id 数据ID  */
 		@ApiDesc(value = "数据ID", required = 0)
 		@ExcelProperty(value = "数据ID")
 		private String dataId;

 		/** data_content 数据内容  */
 		@ApiDesc(value = "数据内容", required = 0)
 		@ExcelProperty(value = "数据内容")
 		private String dataContent;

 		/** data_version 版本号  */
 		@ApiDesc(value = "版本号", required = 0)
 		@ExcelProperty(value = "版本号")
 		private int dataVersion;


    public static SysDataLogResponseVo build(){
        return new SysDataLogResponseVo();
    }

    
    
    
    
    
     		public void setDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		}
 		public SysDataLogResponseVo toDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		 return this ;
 		}

 		public String getDataTable(){
 		 		return this.dataTable;
 		}
    
     		public void setDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		}
 		public SysDataLogResponseVo toDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		 return this ;
 		}

 		public String getDataId(){
 		 		return this.dataId;
 		}
    
     		public void setDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		}
 		public SysDataLogResponseVo toDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		 return this ;
 		}

 		public String getDataContent(){
 		 		return this.dataContent;
 		}
    
     		public void setDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		}
 		public SysDataLogResponseVo toDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		 return this ;
 		}

 		public int getDataVersion(){
 		 		return this.dataVersion;
 		}
    






}
