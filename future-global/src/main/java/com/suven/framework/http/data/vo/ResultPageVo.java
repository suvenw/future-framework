package com.suven.framework.http.data.vo;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IResponseResultPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**   
 * @Title: RespFriendShipList.java 
 * @author Joven.wang   
 * @date   2017年1月19日
 * @version V1.0  
 * Description: 返回列表规范对象list
 */

public  class ResultPageVo<T> implements IResponseResultPage {
    @ApiDesc(value= "返回分页结果指定对象的聚合 ")
	private List<T> list;
    @ApiDesc(value= "是否有下一页,1.有下一页,0:没有下一页")
	private int isNextPage;
    @ApiDesc(value= "下一页的开始索引的表ID值,类型为long")
	private long pageIndex;

	@ApiDesc(value= "数据总数")
    private long total;

	/**
	 * 创建分页返回列表规范对象list
	 * @return 返回列表规范对象list
	 */
	public static ResultPageVo build( ){
		return new ResultPageVo();
	}

	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * @param pageSize  查询条件的条数大小;
	 * @return返回列表规范对象list
	 */
	public ResultPageVo of(List<?> resultList, int pageSize){
		return of(resultList,pageSize,0);
	}
	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * @param pageSize  查询条件的条数大小;
	 * @param total  查询条件的结果总条数;
	 * @return返回列表规范对象list
	 */
	public ResultPageVo of(List<?> resultList, int pageSize, int total ){
		if(null == resultList){
			resultList = new ArrayList(0);
		}
		boolean isNext = resultList.size() >= pageSize;
		this.toIsNextPage(isNext).toList(resultList).toTotal(total);
		return this;
	}

	public ResultPageVo( ) {
		list = new ArrayList<>();
	}
	
	public ResultPageVo(int isNextPage) {
		super();
		this.isNextPage = isNextPage;
		list = new ArrayList<>();
	}

	@Override
	public int getIsNextPage() {
		return isNextPage;
	}
	public ResultPageVo toIsNextPage(int isNextPage) {
		this.isNextPage = isNextPage;
		return this;
	}
	public void setIsNextPage(int isNextPage) {
		this.isNextPage = isNextPage;
	}

    public ResultPageVo toIsNextPage(boolean isNextPage) {
        int nextPage = isNextPage == true ? 1 : 0;
        this.isNextPage = nextPage;
        return this;
    }
	@Override
	public List<T> getList() {
		return new ArrayList<>(list);
	}
	public void setList(Collection<T> list) {
		this.list = new ArrayList<>(list);
	}


	public ResultPageVo toList(Collection<T> list) {
		if(list != null){
			this.list = new ArrayList<>(list);
		}
		return this;
    }

	@Override
    public long getPageIndex() {
        return pageIndex;
    }

    public ResultPageVo toPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }
	@Override
	public long getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ResultPageVo toTotal(long total) {
		this.total = total;
		return this;
	}

	/** 判断类对象的 list 聚合是否为空的实现方法 **/
	public boolean isNotList(){
		return null == list || list.isEmpty();
	}

	/**
	 * 分页结果对象转换实现, 用于分页查询,将V为DTO对象,转换成V为VO对象的分页结果ResultPageVo
	 * @param targetClazz 需要转换成结果目录对象
	 * @return ResultPageVo
	 */
	public <V extends IBeanClone> ResultPageVo<V> convertBuild(Class<V> targetClazz){
		List<V> resultList = IterableConvert.convertList(this.getList(), targetClazz);

		ResultPageVo<V> result = new ResultPageVo<>();
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
	 * @return ResultPageVo
	 */
	public ResultPageVo<T> convertBuild(List<T> resultList, boolean isNextPage ) {
		this.toList(resultList).toIsNextPage(isNextPage);
		return this;
	}

	/**
	 * 转换结果, 用于分页查询
	 * <p>page为null不会判断下一页
	 * @param isNextPage        分页信息, 是否有下一页,
	 * @return ResultPageVo
	 */
	public ResultPageVo<T> convertBuild(List<T> resultList, boolean isNextPage ,long total) {
		this.toList(resultList).toIsNextPage(isNextPage).toTotal(total);
		return this;
	}

}
