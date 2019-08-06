package com.wondersgroup.framwork.dao.bo;

import java.util.List;

public class Page<T>{
    private int pageIndex;//第几页
    private int pageSize;//每页显示行数
    private long total;//总记录数
    private long totalPage;//总页数
    private long startNum;//开始行号
    private long endNum;//截止行号

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getStartNum() {
        return startNum;
    }

    public void setStartNum(long startNum) {
        this.startNum = startNum;
    }

    public long getEndNum() {
        return endNum;
    }

    public void setEndNum(long endNum) {
        this.endNum = endNum;
    }
    public void calculate(){
        this.pageIndex++;
        this.startNum=(this.pageIndex-1)*this.pageSize;
        this.startNum=this.startNum<=0?0:this.startNum;
        this.endNum=this.startNum+pageSize;
    }
    private List<T> data;

    public Page(){

    }
    public Page(long total){
        this.total=total;
    }
    public Page(int pageIndex,int pageSize){
        this.pageIndex=pageIndex;
        this.pageSize=pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
