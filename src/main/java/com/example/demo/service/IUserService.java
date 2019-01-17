package com.example.demo.service;

import javax.servlet.http.HttpSession;

import com.example.demo.bean.User;

public interface IUserService {

	void addUser(User user, int code, HttpSession session);
	
	User login(String name, String pwd, int code,HttpSession session);
}
