package com.chenxinjian.smartstock.server.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

/**
 * 
 * @author root
 * 
 */
public class SplatCodeFactory implements ProtocolCodecFactory {

	private final TextLineEncoder encoder;
	private final TextLineDecoder decoder;

	public SplatCodeFactory() {
		this(Charset.forName("UTF-8"));
	}

	public SplatCodeFactory(Charset charset) {
		encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
		decoder = new TextLineDecoder(charset, LineDelimiter.AUTO);
	}

        public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		// System.out.println("333333333333333333 :");
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public int getEncoderMaxLineLength() {
		return encoder.getMaxLineLength();
	}

	public void setEncoderMaxLineLength(int maxLineLength) {
		encoder.setMaxLineLength(maxLineLength);
	}

	public int getDecoderMaxLineLength() {
		return decoder.getMaxLineLength();
	}

	public void setDecoderMaxLineLength(int maxLineLength) {
		decoder.setMaxLineLength(maxLineLength);
	}
}
