package com.chenxinjian.smartstock.server.db.bean;

import java.util.List;

/**
 *  对查询结果进行封装
 *
 */
public class QueryResult<T> {
	
	// 结果集合
	private List<T> resultList;
	
	// 总计录数
	private long totalRecord;
	
	public QueryResult(){}
	
	public QueryResult(List<T> resultList, long totalRecord){
		this.resultList = resultList;
		this.totalRecord = totalRecord;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
}
