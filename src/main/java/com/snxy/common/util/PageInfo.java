package com.snxy.common.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 7188932838664762641L;

    private int pageNum;            // 第几页
    private int pageSize;           // 每页记录数
    private int currentPageSize;    // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性
    private long total;             // 总记录数
    private int totalPages;         // 总页数
    private List<T> data;           // 结果集
    private boolean hasPrePage;
    private boolean hasNextPage;

    /**
     * 用于创建空分页对象
     */
    public PageInfo() {
        this.data = new ArrayList<>(0);
    }

 /*   public PageInfo(List<T> data) {
        if (data instanceof Page) {
            Page page = (Page) data;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.currentPageSize = page.size();
            this.totalPages = page.getPages();
            this.total = page.getTotal();
            this.data = page;
            this.hasPrePage = this.pageNum > 1;
            this.hasNextPage = this.pageNum < this.totalPages;
        } else {
            throw new BizException("不支持的数据类型" + data.getClass().getSimpleName());
        }
    }
*/
    public PageInfo(int pageNum, int pageSize, long total, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
        this.currentPageSize = data != null ? data.size() : 0;
        this.totalPages = (int) (total % pageSize > 0 ? (total / pageSize) + 1 : (total / pageSize));
        this.hasPrePage = pageNum > 1;
        this.hasNextPage = pageNum < totalPages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", currentPageSize=" + currentPageSize +
                ", total=" + total +
                ", totalPages=" + totalPages +
                ", data=" + data +
                ", hasPrePage=" + hasPrePage +
                ", hasNextPage=" + hasNextPage +
                '}';
    }
}
