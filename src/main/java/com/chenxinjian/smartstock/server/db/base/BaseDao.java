package com.chenxinjian.smartstock.server.db.base;

import com.chenxinjian.smartstock.server.db.bean.QueryResult;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * dao接口,泛型参数T为实体bean
 */
public interface BaseDao<T> {

	/**
	 * 添加新的數據
	 */
	public void save(String sql, Object... args);

	/**
	 * 添加新的數據
	 */
	public int save(String sql, T entity);

	/**
	 * 根據ID查找記錄
	 */
	public T getById(String sql, Serializable id);

	/**
	 * 根據ID刪除記錄
	 */
	public int deleteById(String sql, Serializable id);

	/**
	 * 修改記錄
	 */
	public int update(String sql, Object... args);

	/**
	 * 查找所有實體
	 */
	public List<T> getAll(String sql, Object... args);

	/**
	 * 查找所有实体
	 */
	public List<Map<String, Object>> getAllToMap(String sql, Object... args);

	/**
	 * 查找單條記錄,用於排序
	 */
	public T get(String[] fields, String where, Object[] queryParams,
				 LinkedHashMap<String, String> orderBy);

	/**
	 * 分頁查找記錄
	 */
	public QueryResult<T> getScrollData();

	public QueryResult<T> getScrollData(int firstIndex, int maxResult);

	/**
	 * @param firstIndex
	 *            從第幾條數據開始
	 * @param maxResult
	 *            每頁顯示記錄數
	 * @param orderBy
	 *            排序方式
	 * @param fields
	 *            查詢字段
	 * @param where
	 *            查詢條件
	 * @param queryParams
	 *            查詢參數
	 */
	public QueryResult<T> getScrollData(int firstIndex, int maxResult,
										String[] fields, String where, Object[] queryParams,
										LinkedHashMap<String, String> orderBy);

	/**
	 * 
	 * @param firstIndex
	 *            從第幾條數據開始
	 * @param maxResult
	 *            每頁顯示記錄數
	 * @param where
	 *            查詢條件
	 * @param queryParams
	 *            查詢參數
	 * @param orderBy
	 *            排序方式
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int maxResult,
										String where, Object[] queryParams,
										LinkedHashMap<String, String> orderBy);
}
