package com.chenxinjian.smartstock.server.db.dao.impl;

import com.chenxinjian.smartstock.server.db.base.DaoSupport;
import com.chenxinjian.smartstock.server.db.bean.UserActionBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by apple on 2016/10/26.
 */
@Component()
public class UserActionDaoImpl extends DaoSupport<UserActionBean> {

    protected Class<UserActionBean> getClazz() {
        return UserActionBean.class;
    }

    @Override
    protected String getTableName() {
        return "tb_user_action";
    }


    List<UserActionBean> userActionBeanList = null;

    @Override
    public List<UserActionBean> getAll() {
        if (userActionBeanList == null) {
            return super.getAll();
        }
        return userActionBeanList;
    }

    /**
     * 更新列表
     *
     * @return
     */
    public List<UserActionBean> updateUserActionList() {
        userActionBeanList = null;
        return getAll();
    }



    public UserActionBean getUserActionByTransportType(String userAction) {
        UserActionBean userActionBean =new UserActionBean(userAction);
        List<UserActionBean> userActionBeanList = getAll();
        if (userActionBeanList != null) {
            if (userActionBeanList.contains(userActionBean)) {
                int index = userActionBeanList.indexOf(userActionBean);
                if (index >= 0) {
                    userActionBean = userActionBeanList.get(index);
                }
            }
        }
        return userActionBean;
    }
}
