package com.chenxinjian.smartstock.server.mina;

import org.apache.mina.filter.executor.ExecutorFilter;

/**
 * 
 * 
 * @author root
 * 
 */
public class SplatexecutorFilter extends ExecutorFilter {
	private int DEFAULT_MAX_POOL_SIZE;
	private int BASE_THREAD_NUMBER;
	private long DEFAULT_KEEPALIVE_TIME;
	private boolean MANAGEABLE_EXECUTOR;
	private boolean NOT_MANAGEABLE_EXECUTOR;

	SplatexecutorFilter() {
		DEFAULT_MAX_POOL_SIZE = 16;
		BASE_THREAD_NUMBER = 0;
		DEFAULT_KEEPALIVE_TIME = 30L;
		MANAGEABLE_EXECUTOR = true;
		NOT_MANAGEABLE_EXECUTOR = false;
	}

	public int getDEFAULT_MAX_POOL_SIZE() {
		return DEFAULT_MAX_POOL_SIZE;
	}

	public void setDEFAULT_MAX_POOL_SIZE(int dEFAULT_MAX_POOL_SIZE) {
		DEFAULT_MAX_POOL_SIZE = dEFAULT_MAX_POOL_SIZE;
	}

	public int getBASE_THREAD_NUMBER() {
		return BASE_THREAD_NUMBER;
	}

	public void setBASE_THREAD_NUMBER(int bASE_THREAD_NUMBER) {
		BASE_THREAD_NUMBER = bASE_THREAD_NUMBER;
	}

	public long getDEFAULT_KEEPALIVE_TIME() {
		return DEFAULT_KEEPALIVE_TIME;
	}

	public void setDEFAULT_KEEPALIVE_TIME(long dEFAULT_KEEPALIVE_TIME) {
		DEFAULT_KEEPALIVE_TIME = dEFAULT_KEEPALIVE_TIME;
	}

	public boolean isMANAGEABLE_EXECUTOR() {
		return MANAGEABLE_EXECUTOR;
	}

	public void setMANAGEABLE_EXECUTOR(boolean mANAGEABLE_EXECUTOR) {
		MANAGEABLE_EXECUTOR = mANAGEABLE_EXECUTOR;
	}

	public boolean isNOT_MANAGEABLE_EXECUTOR() {
		return NOT_MANAGEABLE_EXECUTOR;
	}

    public void setNOT_MANAGEABLE_EXECUTOR(boolean nOT_MANAGEABLE_EXECUTOR) {
		NOT_MANAGEABLE_EXECUTOR = nOT_MANAGEABLE_EXECUTOR;
	}

}
