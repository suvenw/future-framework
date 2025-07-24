package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.core.IterableConvert;

import java.util.ArrayList;
import java.util.List;


/**   
 * @Title: RespFriendShipList.java 
 * @author Joven.wang   
 * @date   2017年1月19日
 * @version V1.0  
 * Description: 返回列表规范对象list
 */

public class ResponseResultList<T> implements IResponseResultList{
    @ApiDesc(value= "返回分页结果指定对象的聚合 ")
	private List<T> list;
    @ApiDesc(value= "是否有下一页,1.有下一页,0:没有下一页")
	private int isNextPage;
    @ApiDesc(value= "下一页的开始索引的表ID值,类型为long")
	private long pageIndex;

	@ApiDesc(value= "数据总数")
    private int total;

	/**
	 * 创建分页返回列表规范对象list
	 * @return 返回列表规范对象list
	 */
	public static ResponseResultList build( ){
		return new ResponseResultList();
	}

	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * @param pageSize  查询条件的条数大小;
	 * @return返回列表规范对象list
	 */
	public static ResponseResultList build(List<?> resultList,int pageSize){
		return build(resultList,pageSize,0);
	}
	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * @param pageSize  查询条件的条数大小;
	 * @param total  查询条件的结果总条数;
	 * @return返回列表规范对象list
	 */
	public static ResponseResultList build(List<?> resultList,int pageSize,int total ){
		ResponseResultList result = build();
		result.setResult(resultList,pageSize,total);
		return result;
	}

	public ResponseResultList setResult(List<?> resultList,int pageSize,int total ){
		if(null == resultList){
			resultList = new ArrayList(0);
		}
		boolean isNext = resultList.size() >= pageSize;
		this.toIsNextPage(isNext).toList(resultList).toTotal(total);
		return this;
	}

	public ResponseResultList( ) {
		list = new ArrayList<>();
	}
	
	public ResponseResultList(int isNextPage) {
		super();
		this.isNextPage = isNextPage;
		list = new ArrayList<>();
	}

	@Override
	public int getIsNextPage() {
		return isNextPage;
	}
	public ResponseResultList toIsNextPage(int isNextPage) {
		this.isNextPage = isNextPage;
		return this;
	}
	public void setIsNextPage(int isNextPage) {
		this.isNextPage = isNextPage;
	}

    public ResponseResultList toIsNextPage(boolean isNextPage) {
        int nextPage = isNextPage == true ? 1 : 0;
        this.isNextPage = nextPage;
        return this;
    }
	@Override
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}


	public ResponseResultList toList(List<T> list) {
		if(list != null){
			this.list = list;
		}
		return this;
    }

	@Override
    public long getPageIndex() {
        return pageIndex;
    }

    public ResponseResultList toPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }
	@Override
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ResponseResultList toTotal(int total) {
		this.total = total;
		return this;
	}

	/** 判断类对象的 list 聚合是否为空的实现方法 **/
	public boolean isNotList(){
		return null == list || list.isEmpty();
	}

	/**
	 * 分页结果对象转换实现, 用于分页查询,将V为DTO对象,转换成V为VO对象的分页结果ResponseResultList
	 * @param targetClazz 需要转换成结果目录对象
	 * @return ResponseResultList
	 */
	public <V extends IBeanClone> ResponseResultList<V> coverBuild( Class<V> targetClazz){
		List<V> resultList = IterableConvert.convertList(this.getList(), targetClazz);

		ResponseResultList<V> result = new ResponseResultList<>();
		result.toList(resultList)
				.toIsNextPage(this.getIsNextPage())
				.toPageIndex(this.getPageIndex())
				.toTotal(this.getTotal());
		return result;
	}

	/**
	 * 转换结果, 用于分页查询
	 * <p>page为null不会判断下一页
	 * @param isNextPage        分页信息, 是否有下一页,
	 * @return ResponseResultList
	 */
	public ResponseResultList<T> coverBuild(List<T> resultList, boolean isNextPage ) {
		this.toList(resultList).toIsNextPage(isNextPage);
		return this;
	}


}
