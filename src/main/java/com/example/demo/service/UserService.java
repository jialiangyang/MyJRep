package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ex.PasswordNotMatchException;
import com.example.demo.service.ex.SmsCodeNotMatchException;
import com.example.demo.service.ex.SmsCodeOverdueException;
import com.example.demo.service.ex.UserNotFoundException;
import com.example.demo.service.ex.UsernameAlreadyExistException;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	private String salt = "19^J$aU*#";
	
	@Override
	public void addUser(User user, int code, HttpSession session) {
		
		codeVertify(code, session);
		User u =  userMapper.selectUserByName(user.getName());
		
		if(u == null) {
			String md5Pwd = DigestUtils.md5DigestAsHex((user.getPwd()+salt).getBytes());
			user.setPwd(md5Pwd);
			userMapper.insertUser(user);
		}
		else {
			throw new UsernameAlreadyExistException("用户名已存在");
		}
		
	}

	@Override
	public User login(String name, String pwd, int code, HttpSession session) {
		
		codeVertify(code, session);
		if(session.getAttribute("user") != null)
			throw new RuntimeException("已登录");
		User u = userMapper.selectUserByName(name);
		if(u == null) {
			throw new UserNotFoundException("找不到用户");
		}
		else {
			String md5Pwd = DigestUtils.md5DigestAsHex((pwd+salt).getBytes());
			if(u.getPwd().equals(md5Pwd)) {
				
				return u;
			}
			else {
				throw new PasswordNotMatchException("密码错误");
			}
		}
	}

	public void codeVertify(int code, HttpSession session) {
		
		if(session.getAttribute("smscode") == null)
			throw new SmsCodeOverdueException("验证码过期");
		
		if((Integer)session.getAttribute("smscode") != code)
			throw new SmsCodeNotMatchException("验证码错误");
	}
	

}
