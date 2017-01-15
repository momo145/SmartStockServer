package com.chenxinjian.smartstock.server.cache.base;

import com.sinkinchan.transport.module.BaseTransportBean;
import com.sinkinchan.transport.module.UserBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 2016/10/23.
 * 缓存基础类
 */
public abstract class BaseCacheDao<T extends BaseTransportBean> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取一个缓存实体
     *
     * @return
     */
    public T get(T cache) {
        if (cache == null) {
            return null;
        }
        String key = cache.getCacheKey();
        logger.info("从缓存中获取一个实体=" + key);
        T cacheBean = (T) redisTemplate.opsForValue().get(key);
        return cacheBean;
    }

    /**
     * 保存一个实体到缓存
     *
     * @param cache
     */
    public void save(T cache) {
        if (cache == null) {
            return;
        }
        String key = cache.getCacheKey();
        logger.info("缓存实体=" + key);
        //缓存8个小时
        redisTemplate.opsForValue().set(key, cache, 8, TimeUnit.HOURS);
    }



}
