package edu.xidian.hgy.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description
 * @Author He
 * @Date 2021/4/16 15:24
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {

	public ProcotolFrameDecoder() {
		this(1024, 12, 4, 0, 0);
	}

	public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
	}
}

