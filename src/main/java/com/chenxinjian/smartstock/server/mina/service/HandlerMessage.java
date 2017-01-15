package com.chenxinjian.smartstock.server.mina.service;

import com.chenxinjian.smartstock.server.mina.mode.Module;
import org.apache.mina.core.session.IoSession;


public interface HandlerMessage {
	 void handlerMessage(IoSession session, Object module);

	/**
	 * 用户连接到服务器的处理
	 */
	 void ConnectHandler(IoSession session, Module module);
}
