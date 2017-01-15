package com.chenxinjian.smartstock.server.db.base;

import com.chenxinjian.smartstock.server.db.bean.QueryResult;
import com.chenxinjian.smartstock.server.utils.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * abstarct dao
 *
 * @author
 */
@SuppressWarnings("unchecked")
public abstract class DaoSupport<T> extends SimpleJdbcDaoSupport implements
        BaseDao<T> {

    Class clazz = GenericUtils.getSuperClassGenericType(this.getClass());

    protected List<String> fileds = new ArrayList<String>();
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "jdbcTemplate")
    /**
     * 注入JdbcTemplate
     */
    public void setJb(JdbcTemplate jb) {
        super.setJdbcTemplate(jb);
    }

    /**
     * 添加新的数据
     */
    public void save(String sql, Object... args) {
        this.getSimpleJdbcTemplate().update(sql, args);
    }

    /**
     * 添加新的数据
     *
     * @return 返回當前記錄的id
     */
    public int save(String sql, T entity) {
        // 将实体bean将参数进行赋值
        SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
        // 这是存放主键的，当我们想把刚插入的字段的主键得到，使用它就可拿到
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getSimpleJdbcTemplate().getNamedParameterJdbcOperations()
                .update(sql, param, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 根据ID查找记录
     */
    public T getById(String sql, Serializable id) {
        List<T> results = getAll(sql, id);
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * 根据ID查找记录 子类必须重写 getTableName 传入正确的表名
     */
    public T getById(Serializable id) {
        String sql = "select * from " + getTableName() + " where id=?";
        // System.out.println(sql);
        List<T> results = getAll(sql, id);
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * 根據ID刪除記錄
     */
    public int deleteById(String sql, Serializable id) {

        return this.getSimpleJdbcTemplate().update(sql, id);
    }

    /**
     * 根據ID刪除記錄
     */
    public int deleteById(Serializable id) {
        String sql = "delete from " + getTableName() + " where id=?";
        // System.out.println(sql);
        return this.getSimpleJdbcTemplate().update(sql, id);
    }

    /**
     * 修改記錄
     */
    public int update(String sql, Object... args) {
        return this.getSimpleJdbcTemplate().update(sql, args);
    }

    /**
     * 查找所有實體
     */
    public List<T> getAll(String sql, Object... args) {
        return this.getSimpleJdbcTemplate().query(sql,
                new ParameterizedRowMapper<T>() {
                    public T mapRow(ResultSet rs, int index)
                            throws SQLException {
                        return rowMapper(rs, index);
                    }
                }, args);
    }

    /**
     * 获取所有实体
     *
     * @return
     */
    public List<T> getAll() {
        if (getTableName() == null) {
            logger.info("table name can not be null");
            return null;
        }
        String sql = "select * from " + getTableName();
        return getAll(sql);
    }

    /**
     * 查找所有实体
     */
    public List<Map<String, Object>> getAllToMap(String sql, Object... args) {
        List<Map<String, Object>> objs = this.getJdbcTemplate().queryForList(
                sql, args);
        Iterator<Map<String, Object>> it = objs.iterator();
        Map<String, Object> rs = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (it.hasNext()) {
            rs = (Map) it.next();
            list.add(rs);
        }
        return list;
    }

    /**
     * 查找單條記錄,用於排序
     */
    public T get(String[] fields, String where, Object[] queryParams,
                 LinkedHashMap<String, String> orderBy) {
        int firstIndex = 0;
        int maxResult = 1;
        Object[] params = getQueryParams(firstIndex, maxResult, queryParams);
        List<T> resultList = this.getSimpleJdbcTemplate().query(
                buildQuerySql(firstIndex, maxResult, fields, where, orderBy),
                new ParameterizedRowMapper<T>() {
                    public T mapRow(ResultSet rs, int index)
                            throws SQLException {
                        return rowMapper(rs, index);
                    }
                }, params);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 分頁查找記錄
     */
    public QueryResult<T> getScrollData() {
        return getScrollData(-1, -1, null, null, null, null);
    }

    public QueryResult<T> getScrollData(int firstIndex, int maxResult) {
        return getScrollData(firstIndex, maxResult, null, null, null, null);
    }

    /**
     * @param firstIndex  從第幾條數據開始
     * @param maxResult   每頁顯示記錄數
     * @param orderBy     排序方式
     * @param fields      查詢字段,如果fields為null,則查找所有
     * @param where       查詢條件
     * @param queryParams 查詢條件參數
     */
    public QueryResult<T> getScrollData(int firstIndex, int maxResult,
                                        String[] fields, String where, Object[] queryParams,
                                        LinkedHashMap<String, String> orderBy) {

        Object[] params = getQueryParams(firstIndex, maxResult, queryParams);
        QueryResult<T> qr = new QueryResult<T>();
        List<T> resultList = this.getSimpleJdbcTemplate().query(
                buildQuerySql(firstIndex, maxResult, fields, where, orderBy),
                new ParameterizedRowMapper<T>() {
                    public T mapRow(ResultSet rs, int index)
                            throws SQLException {
                        return rowMapper(rs, index);
                    }
                }, params);

        params = getQueryParams(-1, -1, queryParams);
        int totalCount = this.getSimpleJdbcTemplate().queryForInt(
                buildTotalCountSql(where), params);

        qr.setResultList(resultList);
        qr.setTotalRecord(totalCount);
        return qr;
    }

    public QueryResult<T> getScrollData(int firstIndex, int maxResult,
                                        String where, Object[] queryParams,
                                        LinkedHashMap<String, String> orderBy) {
        return getScrollData(firstIndex, maxResult, null, where, queryParams,
                orderBy);
    }

	/*
     * public QueryResult<T> getScrollData(int firstIndex, int maxResult,
	 * LinkedHashMap<String, String> orderBy, String[] fields,
	 * LinkedHashMap<String, Object> where){ Object[] params =
	 * getQueryParams(firstIndex, maxResult, where); QueryResult<T> qr = new
	 * QueryResult<T>(); List<T> resultList =
	 * this.getSimpleJdbcTemplate().query(buildQuerySql(firstIndex, maxResult,
	 * fields, where, orderBy), new ParameterizedRowMapper<T>(){ public T
	 * mapRow(ResultSet rs, int index) throws SQLException { return
	 * rowMapper(rs, index); } }, params);
	 * 
	 * params = getQueryParams(-1, -1, where); int totalCount =
	 * this.getSimpleJdbcTemplate().queryForInt(buildTotalCountSql(where),
	 * params);
	 * 
	 * qr.setResultList(resultList); qr.setTotalRecord(totalCount); return qr; }
	 */

    /**
     * 返回查詢參數
     *
     * @return
     */
    protected Object[] getQueryParams(int firstIndex, int maxResult,
                                      Object[] queryParams) {
        Object[] params = null;
        Object[] temp = null;
        if (queryParams != null && queryParams.length > 0) {
            temp = new Object[queryParams.length];
            int index = 0;
            for (Object value : queryParams) {
                temp[index] = value;
                index++;
            }
            params = temp;
        }

        if (firstIndex != -1 && maxResult != -1) {
            if (params != null) {
                int size = queryParams.length;
                temp = new Object[size + 2];
                int index = 0;
                for (Object o : params) {
                    temp[index] = o;
                    index++;
                }
                temp[size] = firstIndex;
                temp[size + 1] = maxResult;
            } else {
                temp = new Object[2];
                temp[0] = firstIndex;
                temp[1] = maxResult;
            }
            params = temp;
            temp = null;
        }

        return params;
    }

    /**
     * 構建查詢sql
     */
    protected String buildQuerySql(int firstIndex, int maxResult,
                                   String[] propertyeNames, String where,
                                   LinkedHashMap<String, String> orderBy) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");

        builderQueryProperties(propertyeNames, sql);

        sql.append(" from ").append(getTableName());

        if (where != null) {

            sql.append(" where " + where);
        }

        // 排序
        buildOrderBy(sql, orderBy);

        // 分頁
        if (firstIndex != -1 && maxResult != -1) {
            sql.append(" limit ?,?");
        }
        // System.out.println(sql.toString());
        return sql.toString();
    }

    /**
     * 構建查詢總記錄數sql
     */
    protected String buildTotalCountSql(String where) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) from ").append(getTableName())
                .append(" as o");
        if (where != null) {
            sql.append(" where " + where);
        }
        return sql.toString();
    }

    /**
     * 查找屬性
     */
    protected void builderQueryProperties(String[] fields, StringBuilder sb) {
        if (fields == null) {
            sb.append("*");
        } else {
            // 按字段查找
            for (String property : fields) {
                sb.append(property).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 構建排序
     */
    protected String buildOrderBy(StringBuilder sql,
                                  LinkedHashMap<String, String> orderBy) {
        if (orderBy != null && orderBy.size() > 0) {
            sql.append(" order by ");
            for (String key : orderBy.keySet()) {
                sql.append(key).append(" ").append(orderBy.get(key))
                        .append(",");
            }
            return sql.deleteCharAt(sql.length() - 1).toString();
        }
        return sql.toString();
    }

    /**
     * 行映射 利用反射注入值
     * 2013-11-19
     * sinkin.chan
     *
     * @param rs
     * @param index
     * @return
     */
    protected T rowMapper(ResultSet rs, int index) {
        clazz = getClazz();//获得实体class
        Field[] fields = clazz.getDeclaredFields();//获取实体的所有属性
        Object obj = null;
        try {
            obj = clazz.newInstance();//实例化实体

            if (fields == null) {
                return null;
            }

            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();//获得实体的属性名
                Class fieldClass = fields[i].getType();//获取实体的类型
                //判断是否是final修饰，final修饰不能使用修改里面的值，所以需要排除final型的
                int type = fields[i].getModifiers();//获取实体的修饰符 注意：需要排除final修饰的，因为final不能修改
                if (type != Modifier.FINAL && type != (Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL) && type != (Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL)
                        && type != (Modifier.PROTECTED + Modifier.STATIC + Modifier.FINAL) && type != (Modifier.STATIC + Modifier.FINAL)
                        ) {
                    //获取set方法，所以实体必须提供属性的set方法
                    String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = clazz.getMethod(methodName, new Class[]{fieldClass});
                    //写值注入
                    Object o = rs.getObject(fieldName.toLowerCase());
                    if (o != null) {
                        method.invoke(obj, o);
                    }

                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            logger.info(e);
        }

        return (T) obj;
    }

    /**
     * 獲取表名
     */
    protected abstract String getTableName();

    protected T getByFirst(String sql, Object... args) {
        List<T> list = getAll(sql, args);
        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取实体的class 用于反射 子类传进来
     *
     * @return
     */
    protected abstract Class<T> getClazz();
}
