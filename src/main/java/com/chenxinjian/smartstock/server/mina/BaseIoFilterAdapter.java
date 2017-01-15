package com.chenxinjian.smartstock.server.mina;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

/**
 * 
 * @author root
 * 
 */
public class BaseIoFilterAdapter extends IoFilterAdapter {

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void destroy() throws Exception {
		super.destroy();
	}

	@Override
	public void onPreAdd(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		super.onPostAdd(parent, name, nextFilter);
	}

	@Override
	public void onPostAdd(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		super.onPostAdd(parent, name, nextFilter);
	}

	@Override
	public void onPreRemove(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		super.onPreRemove(parent, name, nextFilter);
	}

	@Override
	public void onPostRemove(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		super.onPostRemove(parent, name, nextFilter);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session)
			throws Exception {
		super.sessionCreated(nextFilter, session);
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session)
			throws Exception {
		super.sessionOpened(nextFilter, session);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {
		super.sessionClosed(nextFilter, session);
	}

	@Override
	public void sessionIdle(NextFilter nextFilter, IoSession session,
			IdleStatus status) throws Exception {
		super.sessionIdle(nextFilter, session, status);
	}

	@Override
	public void exceptionCaught(NextFilter nextFilter, IoSession session,
			Throwable cause) throws Exception {
		super.exceptionCaught(nextFilter, session, cause);
	}

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

	@Override
	public void filterWrite(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		super.filterWrite(nextFilter, session, writeRequest);
	}

	@Override
	public void filterClose(NextFilter nextFilter, IoSession session)
			throws Exception {
		super.filterClose(nextFilter, session);
	}

}
