package com.suven.framework.http.data.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IResponseResultPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**   
 * Title: RespFriendShipList.java
 * @author Joven.wang   
 * date   2017年1月19日
 * @version V1.0  
 * Description: 返回列表规范对象list
 */

public  class PageResult<T> implements IResponseResultPage<T> {
    @ApiDesc(value= "返回分页结果指定对象的聚合 ")
	private List<T> list;
    @ApiDesc(value= "是否有下一页,1.有下一页,0:没有下一页")
	private int isNextPage;
    @ApiDesc(value= "下一页的开始索引的表ID值,类型为long")
	private long pageIndex;
	/**
	 * 总数
	 */
	@ApiDesc(value= "数据总数")
    private long total;

	/**
	 * 每页显示条数，默认 20
	 */
	@ApiDesc(value= "每页显示条数，默认 20")
	protected long pageSize = 20;

	/**
	 * 当前页
	 */
	@ApiDesc(value= "当前页,默认 1")
	protected long current = 1;



	public PageResult( ) {
		list = new ArrayList<>();
	}

	public PageResult(int isNextPage) {
		super();
		this.isNextPage = isNextPage;
		list = new ArrayList<>();
	}
	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * resultList size ==  pageSize  查询条件的条数大小;
	 * @return 返回列表规范对象list
	 */
	public PageResult<T> of(List<T> resultList){
		return of(resultList,resultList.size(),0);
	}

	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * @param pageSize  查询条件的条数大小;
	 * @return 返回列表规范对象list
	 */
	public PageResult<T> of(List<T> resultList, int pageSize){
		return of(resultList,pageSize,0);
	}
	/**
	 * 创建分页返回列表规范对象list
	 * @param resultList 返回的列表集合;
	 * @param pageSize  查询条件的条数大小;
	 * @param total  查询条件的结果总条数;
	 * @return 返回列表规范对象list
	 */
	public PageResult<T> of(List<T> resultList, int pageSize, long total ){
		if(null == resultList){
			resultList = new ArrayList<>(0);
		}
		boolean isNext = isNextPage(resultList, pageSize);
		this.toIsNextPage(isNext).toList(resultList).toTotal(total);
		return this;
	}

	/**
	 * 创建分页返回列表规范对象list
	 * @param iPage IPage 返回的列表集合;
	 * @return 返回列表规范对象list
	 */
	public PageResult<T> of(IPage<T> iPage){
		if(null == iPage){
			return this;
		}
		boolean isNext = isNextPage(iPage.getRecords(), iPage.getSize());
		this.toIsNextPage(isNext).toList(iPage.getRecords()).toTotal(iPage.getTotal());
		this.setCurrent(iPage.getCurrent());
		this.setPageSize(iPage.getSize());
		return this;
	}

	public  boolean isNextPage(List<T> list, long pageSize){
		if(null != list && list.size() > pageSize && list.size() > 1){
			list.remove(pageSize);
			return true;
		}return false;
	}


	@Override
	public int getIsNextPage() {
		return isNextPage;
	}
	public PageResult<T> toIsNextPage(int isNextPage) {
		this.isNextPage = isNextPage;
		return this;
	}
	public void setIsNextPage(int isNextPage) {
		this.isNextPage = isNextPage;
	}

    public PageResult<T> toIsNextPage(boolean isNextPage) {
        this.isNextPage = isNextPage? 1 : 0;
        return this;
    }
	@Override
	public List<T> getList() {
		return new ArrayList<>(list);
	}
	public void setList(Collection<T> list) {
		this.list = new ArrayList<>(list);
	}


		/**
	 * 将给定的集合转换为当前对象的列表
	 *
	 * @param dataList 要转换的集合对象，可以为null
	 * @return 返回当前ResultPageVo对象，支持链式调用
	 */
	public PageResult<T> toList(Collection<T> dataList) {
		if (list != null) {
			this.list = new ArrayList<>(dataList);
		}else {
			this.list.addAll(dataList);
		}
		return this;
	}



	@Override
    public long getPageIndex() {
        return pageIndex;
    }

    public PageResult<T> toPageIndex(long pageIndex) {
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

	public PageResult<T> toTotal(long total) {
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
	 * @return ResultPageVo 转换结果
	 */
	public <V extends IBeanClone> PageResult<V> convertBuild(Class<V> targetClazz){
		List<V> resultList = IterableConvert.convertList(this.getList(), targetClazz);

		PageResult<V> result = new PageResult<>();
		result.toList(resultList)
				.toIsNextPage(this.getIsNextPage())
				.toPageIndex(this.getPageIndex())
				.toTotal(this.getTotal());
		return result;
	}

	/**
	 * 分页结果对象转换实现, 用于分页查询,将V为DTO对象,转换成V为VO对象的分页结果ResultPageVo
	 * 
	 * <p>重要：targetClazz 必须实现 IBeanClone 接口，否则会抛出异常</p>
	 * 
	 * @param <V> 源列表元素类型
	 * @param list 源列表数据
	 * @param targetClazz 需要转换成结果目录对象，必须实现 IBeanClone 接口
	 * @param pageSize 每页大小
	 * @param total 总记录数
	 * @return ResultPageVo 转换结果
	 * @throws IllegalArgumentException 如果 targetClazz 未实现 IBeanClone 接口
	 */
	public <V> PageResult<T> convertBuild(List<V> list, Class<T> targetClazz, long pageSize, long total) {
		// 检查 targetClazz 是否实现了 IBeanClone 接口
		// 使用 isAssignableFrom 检查类是否实现接口（instanceof 只能用于对象实例检查）
		if (!IBeanClone.class.isAssignableFrom(targetClazz)) {
			throw new IllegalArgumentException(
					String.format("目标类型 %s 必须实现 IBeanClone 接口。请确保类实现 IBeanClone 接口或继承 BaseBeanClone 基类。", 
							targetClazz.getName()));
		}
		// 转换为目标类型列表
		// 由于已经验证了 targetClazz 实现了 IBeanClone，可以进行安全的类型转换
		@SuppressWarnings({"unchecked", "rawtypes"})
		Class<? extends IBeanClone> beanCloneClass = (Class<? extends IBeanClone>) targetClazz;
		@SuppressWarnings("unchecked")
		List<T> resultList = (List<T>) IterableConvert.convertList(list, beanCloneClass);
		// 判断是否有下一页
		boolean nextPage = isNextPage(resultList, pageSize);
		// 构建分页结果
		this.toList(resultList).toIsNextPage(nextPage).toTotal(total);
		return this;
	}

	/**
	 * 转换结果, 用于分页查询
	 * <p>page为null不会判断下一页
	 * @param isNextPage        分页信息, 是否有下一页,
	 * @return ResultPageVo
	 */
	public PageResult<T> convertBuild(List<T> resultList, boolean isNextPage ) {
		this.toList(resultList).toIsNextPage(isNextPage);
		return this;
	}

	/**
	 * 转换结果, 用于分页查询
	 * <p>page为null不会判断下一页
	 * @param isNextPage        分页信息, 是否有下一页,
	 * @return ResultPageVo
	 */
	public PageResult<T> convertBuild(List<T> resultList, boolean isNextPage , long total) {
		this.toList(resultList).toIsNextPage(isNextPage).toTotal(total);
		return this;
	}

	public long getCurrent() {
		return current;
	}

	public void setCurrent(long current) {
		this.current = current;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
}
