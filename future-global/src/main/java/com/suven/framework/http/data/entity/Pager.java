package com.suven.framework.http.data.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * ClassName:   Page
 * @author       Joven.wang 
 * date         2014年7月28日
 * @version      V1.0.0 
 * Description: (作用描述) 
 *  页码初始大小1，页码数大小100条
 */
public class Pager<T> implements Serializable {

	private static final long serialVersionUID = 171123346864199339L;
	private int PAGE_MAX_SIZE = 100;//设置页码数大小;
	private final Integer PAGE_INIT = 1;  //设置页码初始大小1;
	private int indexId = 0;     //获取下标ID索引值;(初始时传0,pageType传1,获取最新数据; 1.刷新 传最大ID, 3.上一页也是传获取最大ID,2.下一页,传本页的最小ID)
	private int pageType = 0 ; 		//分页获取方向: 1.刷新(最新,做第一次请求用) 2.下一页(更多) 3.上一页(比当前页更新的)
	private int pageSize = 0 ;  // 获取请求条数;
	private int pageNo = 0;       //页码
	private boolean searchCount = false;//是否执行总条数查询;默认为false,不查询
	private long total; //数据总数
	private boolean isNextPage = false;    //是否需要验证还有下一次
	private int nextPage = 0;
	private T paramObject;

	/**
	 * 创建对象时,设置页码的大小值默认为100条;
	 */
	public Pager(){
		
	}
	/**
	 * 创建对象时,设置页码的是否需要下一次;
	 * @param isNextPage
	 */
	public Pager(boolean isNextPage){
		this.isNextPage = isNextPage;
	}

	/**
	 * 创建对象时,设置页码的是否需要下一次;
	 * @param isNextPage
	 */
	public Pager(boolean isNextPage, int pageMaxSize){
		this.isNextPage = isNextPage;
		this.PAGE_MAX_SIZE = pageMaxSize;
	}


	/**
     * 创建默认有下一页的分页对象
     * @return
     */
	public  static <T> Pager<T> build(){
		return new Pager<>(true);
	}

    /**
     * 创建默认有下一页的分页对象,且返回条数小于等于指定的pageMaxSize值;
     * @return
     */
    public  static <T> Pager<T> build(int pageMaxSize){
        return new Pager<>(true,pageMaxSize);
    }


	/**
	 * 创建分页对象时,设置页码的大小值默认为100条;是否有下一页,默认为false;
	 */
	public static <T> Pager<T> build(int pageNo, int pageSize){
		Pager<T> build = build();
		return build.toPageNo(pageNo).toPageSize(pageSize);
	}

	public static <T> Pager<T> build(T paramObject, int pageNo, int pageSize){
		Pager<T> build = build();
		return build.toPageNo(pageNo).toPageSize(pageSize).toParamObject(paramObject);
	}

	
	public int getIndexId() {
		return indexId;
	}
	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}
	public Integer getPageType() {
		return pageType;
	}
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

	public boolean isSearchCount() {
		return searchCount;
	}

	public Pager<T> setSearchCount(boolean searchCount) {
		this.searchCount = searchCount;
		return this;
	}

	public long getTotal() {
		return total;
	}

	public Pager<T> setTotal(long total) {
		this.total = total;
		return this;
	}

	/**
	 * 通过new 对象初始化是否需要下一页;返回大小是否加1的值;
	 * @return
	 */
	public int getPageSize() {
		int pageSize = this.getSize();
		if(isNextPage){//设置有下一页,页大小增加一;
			pageSize ++ ;
		}
		return pageSize;
	}

	/**
	 * 获取真实页面 不判断是否包含下一页
	 * @return
	 */
	public int getRealPageSize() {
		return pageSize;
	}
	
	/**
	 * 返回页大小数
	 * @return
	 */
	public int getSize(){
		if(pageSize <= 0 || pageSize > PAGE_MAX_SIZE) {
			pageSize = PAGE_MAX_SIZE;
		}
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Pager<T> toPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

    /**
     * 返回的结果为1,2,3,4
     * @return
     */
    public int getPageNo() {
		if (this.pageNo < PAGE_INIT){
			pageNo = PAGE_INIT;
		}
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Pager<T> toPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		return this;
	}
	
	/**
	 * 获取数据库查询的第几页的大小值;
     * 返回的结果为10,20,30 (1->0, 2->10,3->30)
	 * @return
	 */
	public int getStart(){
		int offset = (this.getPageNo() - PAGE_INIT) * this.getSize() ;//分页获取缓存信息;
		return offset;
	}
	
	/**
	 * 获取下标为1开始的页结束的大小
	 * @return
	 */
	public int getOneEndSize(){
		int endset = this.getPageNo() *  this.getSize();//获取第几页的下标值
		if(isNextPage){//设置有下一页,页大小增加一;
			endset ++ ;
		}
		return endset;
	}
	
	/**
	 * //获取下标为0开始的结束页大小
	 * 用于redis set 数据组排序列表
	 * @return
	 */
	public int getZeroEndSize(){
		int endset = this.getPageNo() *  this.getSize() - PAGE_INIT ;
		if(isNextPage){//设置有下一页,页大小增加一;
			endset ++ ;
		}
		return endset;
	}
	
	
	/**
	 * 验证查询结果集是否还有一下页实现方法
	 * @param size
	 * @return
	 */
	public boolean isNextPage(int size){
        return isNextPage && this.getSize() < size;
    }

	public  boolean isNextPage(List<T> list){
		if(null != list && list.size() > pageSize && list.size() > 1){
			list.remove(pageSize);
			nextPage = PAGE_INIT;
			return true;
		}return false;
	}

	public boolean isNextPage(){
		return this.nextPage == PAGE_INIT;
	}

		/**
	 * 设置分页器的参数对象
	 *
	 * @param paramObject 泛型参数对象，用于存储分页查询的参数信息
	 * @return 返回当前Pager对象，支持链式调用
	 */
	@SuppressWarnings("unchecked")
    public Pager<T> toParamObject(T paramObject) {
        this.paramObject = paramObject;
        return this;
    }

	public void setParamObject(T paramObject) {
		this.paramObject = paramObject;
	}

    public T getParamObject(){
	    return paramObject;
    }

	/**
	 * 获取分页的开始Id和页条数大小的集合;
	 * @return
	 */
	public List<Integer> getPageNoSizeList(){
		List<Integer> param = new ArrayList<>();
		param.add(getStart());
		param.add(getPageSize());
		return param;
	}

}