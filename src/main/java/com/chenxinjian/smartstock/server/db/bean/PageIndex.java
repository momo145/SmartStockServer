package com.chenxinjian.smartstock.server.db.bean;

public class PageIndex {
	private long startIndex;
	private long endIndex;
	
	public PageIndex(long startIndex, long endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	public long getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	public long getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	 
	public static PageIndex getPageIndex(long viewPageCount, int currentPage, long totalPage){
			long startPage = currentPage-(viewPageCount%2==0? viewPageCount/2-1 : viewPageCount/2);
			long endPage = currentPage+viewPageCount/2;
			if(startPage<1){
				startPage = 1;
				if(totalPage>=viewPageCount){
					endPage = viewPageCount;
				}else{
					endPage = totalPage;
				}
			}
			if(endPage>totalPage){
				endPage = totalPage;
				if((endPage-viewPageCount)>0){
					startPage = endPage-viewPageCount+1;
				}else{
					startPage = 1;
				}
			}
			return new PageIndex(startPage, endPage);		
	}
}
