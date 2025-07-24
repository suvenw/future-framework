package com.suven.framework.core.mybatis;


import com.baomidou.mybatisplus.core.metadata.IPage;

public interface IPager<T>  extends IPage<T>  {
    boolean nextPage = true;
    /**
     * **/
    default public  boolean isNextPage(){
        if (nextPage){
            return true;
        }
        return nextPage;
    }
    default public IPage setNextPage(boolean isNextPage){
        return this;
    }

    default public long getNextPageSize(){
        return getSize() + 1;
    }
}
