package com.rolandleou.skymall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rolandleou.skymall.dao.UserDao;
import com.rolandleou.skymall.dto.UserRegisterRequest;
import com.rolandleou.skymall.model.User;
import com.rolandleou.skymall.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		return userDao.createUser(userRegisterRequest);
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

}
