package edu.xidian.hgy.message;

import lombok.Data;
import lombok.ToString;

/**
 * @Description
 * @Author He
 * @Date 2021/4/16 15:18
 */
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends Message {
	/**
	 * 返回值
	 */
	private Object returnValue;
	/**
	 * 异常值
	 */
	private Exception exceptionValue;

	@Override
	public int getMessageType() {
		return RPC_MESSAGE_TYPE_RESPONSE;
	}
}
