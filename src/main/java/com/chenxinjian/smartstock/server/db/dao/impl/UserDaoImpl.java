package com.chenxinjian.smartstock.server.db.dao.impl;

import com.chenxinjian.smartstock.server.db.base.DaoSupport;
import com.chenxinjian.smartstock.server.db.bean.UserActionBean;
import com.sinkinchan.transport.module.UserBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component()
public class UserDaoImpl extends DaoSupport<UserBean> {
    /*@Autowired
    UserCacheDaoImpl userCacheDao;*/

    @Override
    protected Class<UserBean> getClazz() {
        // TODO Auto-generated method stub
        return UserBean.class;
    }

    @Override
    protected String getTableName() {
        // TODO Auto-generated method stub
        return "tb_user";
    }

    /**
     * 保存一个用户实体
     *
     * @param entity
     * @return
     */
    public UserBean saveUser(UserBean entity) {
        String sql = "insert into " + getTableName() + "(userName,icon,gender,third_party_id,platform,addTime,isOnline,loginTime,sessionId) " +
                "values(:userName,:icon,:gender,:third_party_id,:platform,:addTime,:isOnline,:loginTime,:sessionId)";
        logger.info("saveUser=" + sql);
        int id = super.save(sql, entity);
        if (id <= 0) {
            //注册失败
            return null;
        }
        entity.setId(id);
//        userCacheDao.save(entity);
        return entity;
    }

    /**
     * 根据id获取实体
     *
     * @param userBean
     * @return
     */
    public UserBean getUserByThirdPartyId(UserBean userBean) {
        /*UserBean cacheUserBean = userCacheDao.get(userBean);
        if (cacheUserBean != null) {
            logger.info("get user by cache");
            return cacheUserBean;
        }*/
        String id = userBean.getThird_party_id();
        if (StringUtils.isBlank(id)) {
            return null;
        }
        logger.info("get user by db");
        String sql = "select * from " + getTableName() + " where third_party_id=?";
        return getByFirst(sql, id);
    }

    /**
     * 根据id获取实体
     *
     * @param third_party_id
     * @return
     */
    public UserBean getUserByThirdPartyId(String third_party_id) {
        /*UserBean cacheUserBean = userCacheDao.get(userBean);
        if (cacheUserBean != null) {
            logger.info("get user by cache");
            return cacheUserBean;
        }*/
        if (StringUtils.isBlank(third_party_id)) {
            return null;
        }
        logger.info("get user by db");
        String sql = "select * from " + getTableName() + " where third_party_id=?";
        return getByFirst(sql, third_party_id);
    }


    /**
     * 登录
     *
     * @param userBean
     */
    public int login(UserBean userBean) {
        if (userBean == null) {
            return 0;
        }
        String id = userBean.getThird_party_id();
        if (StringUtils.isBlank(id)) {
            id = userBean.getId() + "";
        }
        if (StringUtils.isBlank(id)) {
            return 0;
        }
        userBean.setLoginTime(new Date());
        userBean.setAddTime(new Date());
        userBean.setIsOnline(UserBean.ON_LINE);
        logger.info("login id=" + id);
        userBean.setLoginTime(new Date());
        String sql = "update " + getTableName() + " set isOnline = ?,sessionId = ?, loginTime = ? where id=? or third_party_id = ?";
        int index = update(sql, UserBean.ON_LINE, userBean.getSessionId(), userBean.getLoginTime(), userBean.getId(), userBean.getThird_party_id());
        //更新缓存
        /*if (index > 0) {
            userCacheDao.save(userBean);
        }*/
        return index;
    }

    /**
     * 退出登录
     *
     * @param userBean
     */
    public void logout(UserBean userBean) {
        if (userBean == null) {
            return;
        }
        String id = userBean.getThird_party_id();
        if (StringUtils.isBlank(id)) {
            id = userBean.getId() + "";
        }
        if (StringUtils.isBlank(id)) {
            return;
        }
        logger.info("logout id=" + id);
        String sql = "update " + getTableName() + " set isOnline = 0,sessionId=0, logoutTime = ? where id=? or third_party_id = ?";
        userBean.setIsOnline(0).setSessionId(0).setLogOutTime(new Date());
        int index = update(sql, userBean.getLogOutTime(), userBean.getId(), userBean.getThird_party_id());
        //更新缓存
        /*if (index > 0) {
            userCacheDao.save(userBean);
        }*/
        logger.info("sql==" + sql);
    }

    /**
     * 退出登录
     *
     * @param sessionId
     */
    public void logout(long sessionId) {
        UserBean userBean = getUserBySessionId(sessionId);
        if (userBean != null) {
            logout(userBean);
        }
    }

    /**
     * 根据会话Id获取用户实体
     *
     * @param sessionId
     * @return
     */
    public UserBean getUserBySessionId(long sessionId) {
        String sql = "select * from " + getTableName() + " where sessionId = ?";
        logger.info("getUserBySessionId sql=" + sql);
        return getByFirst(sql, sessionId);
//        return get(new String[]{"third_party_id"}, "sessionId = ?", new Object[]{sessionId}, null);
    }

    public int reward(UserBean user, UserActionBean userActionBean) {
        if (user == null) {
            logger.info("reward,but user is null.");
            return 0;
        }
        if (userActionBean == null) {
            logger.info("reward,but userActionBean is null.");
            return 0;
        }
        int integral = userActionBean.getIntegral();
        String reg = "+";
        if (userActionBean.isReward()) {
            //奖励
            logger.info("reward,true.");
        } else {
            //惩罚
            reg = "-";
            logger.info("reward,false.");
        }
        String sql = "update " + getTableName() + " set integral=integral" + reg + "? where third_party_id=?";
        logger.info("sql=" + sql);
        return update(sql, integral, user.getThird_party_id());
    }
}
