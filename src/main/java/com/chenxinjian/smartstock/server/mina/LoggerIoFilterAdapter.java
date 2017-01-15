package com.chenxinjian.smartstock.server.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author root
 * 
 */
public class LoggerIoFilterAdapter extends BaseIoFilterAdapter {

    private static final Logger log = LoggerFactory
			.getLogger(LoggerIoFilterAdapter.class);

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
		// System.out.println("1111111111111111111== :" + message);
		super.messageReceived(nextFilter, session, message);
		// log.info(message.toString());
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		super.messageSent(nextFilter, session, writeRequest);
		log.info(writeRequest.getMessage().toString());
	}
}