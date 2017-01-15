package com.chenxinjian.smartstock.server.mina;

import com.chenxinjian.smartstock.server.db.dao.impl.UserDaoImpl;
import com.chenxinjian.smartstock.server.mina.service.HandlerMessage;
import com.sinkinchan.transport.module.UserBean;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 业务分发入口
 *
 * @author root
 */
public class ReceivedMessage extends IoHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger("mina-ReceivedMessage");
    private String TAG = "MINA--";
    @Autowired
    private HandlerMessage handlerMessage;
    @Autowired
    UserDaoImpl userDao;

    /**
     * 新连接建立的时候触发
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        logger.info(TAG + Thread.currentThread().getName() + "-有用户进来 " + session.getId());

    }

    /**
     * 新连接打开的时候触发，在sessionCreated之后被调用
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    /**
     * 连接被关闭的时候触发
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        long id = session.getId();
        logger.info(TAG + Thread.currentThread().getName() + "用户离开 " + id);
        userDao.logout(id);
        super.sessionClosed(session);
    }

    /**
     * 有消息接收的时候触发
     */
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        handlerMessage.handlerMessage(session, message);
    }


    /**
     * 连接被闲置的时候触发
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        super.sessionIdle(session, status);
    }

    /**
     * 有异常抛出但是没有被catch的时候触发
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        super.exceptionCaught(session, cause);
    }

    /**
     * 消息发送的时候触发
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }
}
