package com.suven.framework.core.db.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Objects;

public class PagePlus<T> extends Page<T> implements IPagePlus<T> {
    public PagePlus() {
        super();
    }

    public PagePlus(long current, long size) {
        super(current, size);
    }

    public PagePlus(long current, long size, long total) {
        super(current, size, total);
    }

    public PagePlus(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public PagePlus(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }


    @Override
    public boolean hasPrevious() {
        return super.hasPrevious();
    }

    public boolean isSearchCount(){
        return searchCount;
    }


    /**
     * 重写 是否存在下一页
     * 如何搜索总条数时，按原来的张应斌，
     * 如何不搜索总条数时，按重 getSize +1 == getNextPageSize
     * @return true / false
     */
    @Override
    public boolean hasNext() {
        if(!isSearchCount()){
            if (Objects.isNull(super.getRecords())){
                return false;
            }
            long recordsSize =  super.getRecords().size();
            return recordsSize == getNextPageSize();
        }
        return super.hasNext();

    }

    @Override
    public List<T> getRecords() {
        if(!isSearchCount() && hasNextPage()){
           long index =  getSize();
            List<T> recordList =  super.getRecords();
            recordList.remove(index);
            return recordList;
        }
        return super.getRecords();

    }


}
