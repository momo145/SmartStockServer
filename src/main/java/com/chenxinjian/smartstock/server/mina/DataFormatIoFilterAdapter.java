package com.chenxinjian.smartstock.server.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

/**
 * 
 * @author root
 * 
 */
public class DataFormatIoFilterAdapter extends BaseIoFilterAdapter {

    @Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {

		super.messageReceived(nextFilter, session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {

		super.messageSent(nextFilter, session, writeRequest);
	}
}