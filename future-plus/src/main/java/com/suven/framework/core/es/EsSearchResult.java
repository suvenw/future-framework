package com.suven.framework.core.es;

import java.util.List;

public class EsSearchResult<T> {

    private long total;
    private int currentPage;
    private int pageSize;
    private int recordCount;
    private T result;
    private List<T> resultList;
    private float maxScore;


    public static EsSearchResult build(){
        return new EsSearchResult();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public EsSearchResult<T> toCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public EsSearchResult<T> toPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
    public EsSearchResult<T> toRecordCount(int recordCount) {
        this.recordCount = recordCount;
        return this;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
    public EsSearchResult<T> toResult(T result) {
        this.result = result;
        return this;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
    public EsSearchResult<T> toResultList(List<T> resultList) {
        this.resultList = resultList;
        return this;
    }

    public float getMaxScore() {
        return maxScore;
    }
    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    public EsSearchResult<T> toMaxScore(float maxScore) {
        this.maxScore = maxScore;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    public EsSearchResult<T> toTotal(long total) {
        this.total = total;
        return this;
    }
}
