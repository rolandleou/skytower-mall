package com.rolandleou.skymall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.rolandleou.skymall.dao.UserDao;
import com.rolandleou.skymall.dto.UserLoginRequest;
import com.rolandleou.skymall.dto.UserRegisterRequest;
import com.rolandleou.skymall.model.User;
import com.rolandleou.skymall.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class); 
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		// Check register email
		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
		
		if (user != null) {
			log.warn("email {} alreday registered!!", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		// Use MD5 hash values for new register password
		String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
		userRegisterRequest.setPassword(hashedPassword);
		
		// create register account
		return userDao.createUser(userRegisterRequest);
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User login(UserLoginRequest userLoginRequest) {
		
		User user = userDao.getUserByEmail(userLoginRequest.getEmail());
		
		// check if user exist
		if (user == null) {
			log.warn("email {} not registered before!!", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);			
		}
		

		// Use MD5 hash values for new register password
		String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
		
		if (user.getPassword().equals(hashedPassword)) {
			return user;
		} else {
			log.warn("email {} incorrect password!!", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);				
		}

	}

}
