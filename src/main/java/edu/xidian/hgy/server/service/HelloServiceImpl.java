package edu.xidian.hgy.server.service;

import edu.xidian.hgy.server.service.HelloService;

/**
 * @Description
 * @Author He
 * @Date 2021/4/16 15:32
 */
public class HelloServiceImpl implements HelloService {
	@Override
	public String sayHello(String msg) {
//		int i = 1 / 0;
		return "你好, " + msg;
	}
}
