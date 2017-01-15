package com.chenxinjian.smartstock.server.db.bean;

import java.util.List;

public class PageView<T> {
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageIndex;
	/** 总页数 **/
	private long totalPage = 1;
	/** 每页显示记录数 **/
	private int maxResult = 20;
	/** 当前页 **/
	private int currentPage = 1;
	/** 总记录数 **/
	private long totalRecord;
	/** 页码数量 **/
	private int pageCode = 6;
	/**是否存在上一頁*/
	@SuppressWarnings("unused")
	private boolean hasPrev;
	/**是否存在下一頁*/
	@SuppressWarnings("unused")
	private boolean hasNext;
	/** 要获取记录的开始索引 **/
	private String lang;
	private String state;
	public int getFirstResult() {
		if(this.currentPage == 0) this.currentPage = 1;
		return (this.currentPage-1)*this.maxResult;
	}
	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		this.currentPage = currentPage;
	}
	
	public void setQueryResult(QueryResult<T> qr){
		setTotalRecord(qr.getTotalRecord());
		setRecords(qr.getResultList());
	}
	
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord%this.maxResult==0? this.totalRecord/this.maxResult : this.totalRecord/this.maxResult+1);
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public PageIndex getPageIndex() {
		return pageIndex;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
		this.pageIndex = PageIndex.getPageIndex(pageCode, currentPage, totalPage);
	}
	public int getMaxResult() {
		return maxResult;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public boolean isHasPrev() {
		return currentPage > 1;
	}
	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}
	public boolean isHasNext() {
		return currentPage < totalPage;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
