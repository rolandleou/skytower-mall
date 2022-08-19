package com.rolandleou.skymall.dao;

import com.rolandleou.skymall.dto.UserRegisterRequest;
import com.rolandleou.skymall.model.User;

public interface UserDao {

	Integer createUser(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer userId);
}
