package com.example.demo.controller;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.ResponseResult;
import com.example.demo.bean.User;
import com.example.demo.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/reg")
	public ResponseResult<Void> register(String name, String pwd, int code, HttpSession session) {
		ResponseResult<Void> rr = null;
		User u = new User();
		u.setName(name);
		u.setPwd(pwd);
		
		try {
			userService.addUser(u,code,session);
			rr = new ResponseResult<Void>(1,"注册成功");
		} catch (RuntimeException e) {
			rr = new ResponseResult<Void>(0,e.getMessage());
		}
		return rr;
	}
	
	@RequestMapping("/login")
	public ResponseResult<Void> login(String name, String pwd, int code, HttpSession session) {
		ResponseResult<Void> rr = null;
		try {
			User u = userService.login(name, pwd, code, session);
			rr = new ResponseResult<Void>(1,"登录成功");
			session.setAttribute("user", u);
		} catch (RuntimeException e) {
			rr = new ResponseResult<Void>(0,e.getMessage());
		}
		return rr;
	}
	
	@RequestMapping("/exit")
	public void exit(HttpSession session) {
		session.removeAttribute("user");
	}
	
	@RequestMapping("/sendsmscode")
	public void sendSmsCode(String phone, HttpSession session) {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 6; i++) {
			int r = rand.nextInt(10);
			sb.append(r);
		}
		session.setAttribute("smscode", Integer.parseInt(sb.toString()));
		System.out.println(sb.toString());
		//往手机发送短信验证码
		//2分钟后删除绑定在session的验证码
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				session.removeAttribute("smscode");
				timer.cancel();
			}
			
		}, 15*1000);
	}
	
	
}
