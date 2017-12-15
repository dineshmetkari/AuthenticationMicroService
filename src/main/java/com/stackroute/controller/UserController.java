package com.stackroute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.UserRepository;
import com.stackroute.activitystream.model.User;




@RestController()
@RequestMapping("api/user")
@Component
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private User user;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers()
	{
		//List<User> users = userDAO.list();
		List<User> users = (List<User>) userRepository.findAll();
		if(users==null || users.isEmpty())
		{
			return  new ResponseEntity<>(users,HttpStatus.NO_CONTENT);
		}
		return  new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUserDetails(@PathVariable("id") String id)
	{
		//user = userDAO.get(id);
		user = userRepository.findOne(id);
		if(user==null )
		{
			return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<>(user,HttpStatus.FOUND);
	}
	

	
	
}
