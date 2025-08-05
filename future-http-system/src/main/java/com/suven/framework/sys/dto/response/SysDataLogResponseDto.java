package com.suven.framework.sys.dto.response;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;

/**
 * ClassName: SysDataLogResponseDto.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description:  RPC业务接口交互数据返回实现类
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

public class SysDataLogResponseDto  extends BaseByTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysDataLogResponseDto.class);






 		/** data_table 表名  */
 		@ApiDesc(value = "表名", required = 0)
 		private String dataTable;

 		/** data_id 数据ID  */
 		@ApiDesc(value = "数据ID", required = 0)
 		private String dataId;

 		/** data_content 数据内容  */
 		@ApiDesc(value = "数据内容", required = 0)
 		private String dataContent;

 		/** data_version 版本号  */
 		@ApiDesc(value = "版本号", required = 0)
 		private int dataVersion;


        public static SysDataLogResponseDto build(){
                return new SysDataLogResponseDto();
        }

 		public void setDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		}
 		public SysDataLogResponseDto toDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		 return this ;
 		}

 		public String getDataTable(){
 		 		return this.dataTable;
 		}
 		public void setDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		}
 		public SysDataLogResponseDto toDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		 return this ;
 		}

 		public String getDataId(){
 		 		return this.dataId;
 		}
 		public void setDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		}
 		public SysDataLogResponseDto toDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		 return this ;
 		}

 		public String getDataContent(){
 		 		return this.dataContent;
 		}
 		public void setDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		}
 		public SysDataLogResponseDto toDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		 return this ;
 		}

 		public int getDataVersion(){
 		 		return this.dataVersion;
 		}







}
