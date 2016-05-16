package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void join(User user){
		userRepository.insert(user);
	}
	
	public User login(User user){
		User authUser = userRepository.get(user);
		return authUser;
	}
	
	public User getUser(String email){
		return userRepository.get(email);
	}
	
	public void modify(User user){
		userRepository.update(user);
	}
	
	public User getUser(Long no){
		User user = userRepository.get(no);
		return user;
	}

}
