package com.chenxinjian.smartstock.server.mina.service.impl;

import com.chenxinjian.smartstock.server.db.bean.UserActionBean;
import com.chenxinjian.smartstock.server.db.bean.UserActionRecordBean;
import com.chenxinjian.smartstock.server.db.dao.impl.UserActionDaoImpl;
import com.chenxinjian.smartstock.server.db.dao.impl.UserActionRecordDaoImpl;
import com.chenxinjian.smartstock.server.db.dao.impl.UserDaoImpl;
import com.chenxinjian.smartstock.server.mina.mode.Module;
import com.chenxinjian.smartstock.server.mina.service.HandlerMessage;
import com.chenxinjian.smartstock.server.utils.DateUtil;
import com.sinkinchan.transport.module.ActionResponse;
import com.sinkinchan.transport.module.FunctionBean;
import com.sinkinchan.transport.module.TransportType;
import com.sinkinchan.transport.module.UserBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.sinkinchan.transport.module.TransportType.useFunction;


@Component
public class HandlerMessageImpl implements HandlerMessage {
    private Logger logger = LoggerFactory.getLogger(HandlerMessageImpl.class);
    @Autowired
    UserDaoImpl userDao;
    @Autowired
    UserActionRecordDaoImpl userActionRecordDao;
    @Autowired
    UserActionDaoImpl userActionDao;

    public void handlerMessage(IoSession session, Object module) {
        // TODO Auto-generated method stub
        if (module != null) {
            UserBean dbUser = null;
            UserActionBean userAction = null;
            if (module instanceof UserBean) {
                UserBean userBean = (UserBean) module;
                TransportType type = userBean.getType();
                long sessionId = session.getId();
                int id = -1;
                if (type == null) {
                    logger.info("type==null");
                    return;
                }
                switch (type) {
                    case register:
                        logger.info("register name=" + userBean.getUserName());
                        userBean.setSessionId(sessionId);
                        id = userDao.login(userBean);
                        if (id > 0) {
                            //登录成功
                            logger.info("已注册,直接登录...");
                            dbUser = userDao.getUserByThirdPartyId(userBean);
                            if (dbUser != null) {
                                userAction = userActionDao.getUserActionByTransportType(TransportType.loginSuccess.name());
                                if (userAction != null) {
                                    dbUser.setType(userAction.getTransportType());
                                }
                                session.write(dbUser);
                            }
                            break;
                        }
                        dbUser = userDao.saveUser(userBean);
                        if (dbUser != null) {
                            //注册成功
                            userBean.setId(id);
                            dbUser = userDao.getUserByThirdPartyId(userBean);
                            if (dbUser != null) {
                                userAction = userActionDao.getUserActionByTransportType(TransportType.registerSuccess.name());
                                if (userAction != null) {
                                    dbUser.setType(userAction.getTransportType());
                                }
                                session.write(dbUser);
                            }
                            logger.info("register success=" + userBean.getUserName());
                        } else {
                            //注册失败
                            logger.info("register failed=" + userBean.getUserName());
                            userBean.setType(TransportType.registerFailed);
                            session.write(userBean);
                        }
                        break;
                    case login:
                        id = userDao.login(userBean);
                        if (id > 0) {
                            logger.info("登录成功");
                            userBean.setId(id);
                            dbUser = userDao.getUserByThirdPartyId(userBean);
                            if (dbUser != null) {
                                dbUser.setType(TransportType.loginSuccess);
                                session.write(dbUser);
                            }
                        } else {
                            //登录失败
                            logger.info("登录失败");
                        }

                        break;
                    case logout:
                        userDao.logout(userBean);
                        break;
                }

            } else if (module instanceof FunctionBean) {
                //使用了功能
                FunctionBean function = (FunctionBean) module;
                String deviceId = function.getId();
                String type = function.getFunctionName();
                switch (function.getType()) {
                    case askUseFunction:
                        //询问是否服务器是否有这条记录
                        logger.info("askUseFunction id==" + deviceId + " name=" + type);
                        if (!StringUtils.isBlank(deviceId) && !StringUtils.isBlank(type)) {
                            dbUser = userDao.getUserByThirdPartyId(deviceId);
                            if (dbUser != null) {
                                //检查该用户该功能是否已经扣除过积分
                                userAction = userActionDao.getUserActionByTransportType(type);
                                if (userAction != null) {
                                    boolean isExist = userActionRecordDao.isUserExistActionOutOfDate(dbUser, userAction, new Date());
                                    ActionResponse actionResponse = new ActionResponse();
                                    if (!isExist) {
                                        logger.info("该用户没有这条记录-- askUseFunction--"+type);
                                        actionResponse.setType(useFunction);
                                        actionResponse.setResponseType(ActionResponse.ResponseType.Ask);
                                        actionResponse.setName(userAction.getName());
                                        actionResponse.setFunctionName(type);
                                        actionResponse.setDesc("你需要扣除 " + userAction.getIntegral() +
                                                " 积分来开启 " + userAction.getName() + " 选股功能吗? 你现在拥有 " + dbUser.getIntegral() + " 积分.");
                                        session.write(actionResponse);
                                    }else{
                                        //用户已经购买过
                                        logger.info("用户已经购买过-- askUseFunction--"+type);
                                    }

                                }
                            }
                        }
                        break;
                    case useFunction:
                        //确认使用该方法
                        logger.info("useFunction id==" + deviceId + " name=" + type);
                        if (!StringUtils.isBlank(deviceId) && !StringUtils.isBlank(type)) {
                            dbUser = userDao.getUserByThirdPartyId(deviceId);
                            if (dbUser != null) {
                                //检查该用户该功能是否已经扣除过积分
                                userAction = userActionDao.getUserActionByTransportType(type);
                            }
                        }
                        break;
                }

            }
            //保存用户行为
            saveUserAction(dbUser, userAction);
        }


    }

    private void saveUserAction(UserBean dbUser, UserActionBean userActionBean) {
        if (userActionBean != null && dbUser != null) {
            logger.info("userActionBean=" + userActionBean.toString());
            int i = userDao.reward(dbUser, userActionBean);
            if (i > 0) {
                //暂时只有 useFunction 有过期时间这个功能
                Date outOfDate = null;
                switch (userActionBean.getTransportType()) {
                    case useFunction:
                        outOfDate = DateUtil.addDays(new Date(), 1);
                        break;
                }
                UserActionRecordBean userActionRecordBean = new UserActionRecordBean();
                userActionRecordBean.setActionId(userActionBean.getId())
                        .setUserId(dbUser.getId())
                        .setOutOfDate(outOfDate);
                userActionRecordDao.add(userActionRecordBean);
            }
        }


    }


    public void ConnectHandler(IoSession session, Module module) {
        // TODO Auto-generated method stub

    }

}
