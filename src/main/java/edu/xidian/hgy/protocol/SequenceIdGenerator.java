package edu.xidian.hgy.protocol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author He
 * @Date 2021/4/16 15:34
 */
public abstract class SequenceIdGenerator {
	private static final AtomicInteger id = new AtomicInteger();

	public static int nextId() {
		return id.incrementAndGet();
	}
}

