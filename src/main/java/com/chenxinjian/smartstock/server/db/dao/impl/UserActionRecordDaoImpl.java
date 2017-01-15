package com.chenxinjian.smartstock.server.db.dao.impl;

import com.chenxinjian.smartstock.server.db.base.DaoSupport;
import com.chenxinjian.smartstock.server.db.bean.UserActionBean;
import com.chenxinjian.smartstock.server.db.bean.UserActionRecordBean;
import com.sinkinchan.transport.module.UserBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by apple on 2016/10/26.
 */
@Component()
public class UserActionRecordDaoImpl extends DaoSupport<UserActionRecordBean> {
    protected Class<UserActionRecordBean> getClazz() {
        return UserActionRecordBean.class;
    }

    @Override
    protected String getTableName() {
        return "tb_user_action_record";
    }

    /**
     * 添加一个实体
     *
     * @param actionRecordBean
     * @return
     */
    public int add(UserActionRecordBean actionRecordBean) {
        String sql = "insert into " + getTableName() + " (userId,actionId,addTime,outOfDate) values(:userId,:actionId,now(),:outOfDate)";
        logger.info("sql=" + sql);
        return save(sql, actionRecordBean);
    }

    /**
     * 检查用户是否拥有该数据,并且是否已经过期
     *
     * @param user
     * @param action
     * @param date
     * @return
     */
    public boolean isUserExistActionOutOfDate(UserBean user, UserActionBean action, Date date) {
        String sql = "select * from " + getTableName() + " where userId=? and actionId=? and outOfDate>?";
        UserActionRecordBean actionRecordBean = getByFirst(sql, user.getId(), action.getId(), date);
        logger.info("isUserExistActionOutOfDate sql=" + sql);
        boolean flag = false;
        if (actionRecordBean != null) {
            flag = true;
        }
        return flag;
    }
}
