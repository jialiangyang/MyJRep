package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.demo.bean.User;

public interface UserMapper {

	@Insert("INSERT INTO `t_user`(username, password) VALUES(#{name},#{pwd})")
	void insertUser(User user);
	
	@Select("SELECT * FROM `t_user` WHERE username = #{name}")
	@Results({
		@Result(property="name", column="username"),
		@Result(property="pwd", column="password")
	})
	User selectUserByName(String name);
}
