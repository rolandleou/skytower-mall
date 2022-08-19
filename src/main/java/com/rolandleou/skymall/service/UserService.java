package com.rolandleou.skymall.service;

import com.rolandleou.skymall.dto.UserRegisterRequest;
import com.rolandleou.skymall.model.User;

public interface UserService {
	
	Integer register(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer userId);

}
