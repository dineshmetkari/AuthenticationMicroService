package com.stackroute.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import  com.stackroute.activitystream.model.User;
import com.stackroute.activitystream.dao.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@EnableWebMvc
public class UserAuthenticateController {
	
	Map<String, String> map = new HashMap<>();

	//@Autowired
	//private UserDAO userDAO;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String serverStarted()
	{
		return "Authentication server started";
	}
	
		
	@PostMapping("login")
	public  ResponseEntity<?> login(@RequestBody User user)
			throws ServletException {

		String jwtToken = "";

		try {
			if (user.getUsername() == null || user.getPassword() == null) {
				throw new ServletException("Please fill in username and password");
			}

			String userName = user.getUsername();
			String password = user.getPassword();

			user = userRepository.validate(userName, password);
		

			if (user == null) {
				throw new ServletException("Invalid credentials.");
			}

		
			jwtToken = Jwts.builder().setSubject(userName).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
			map.clear();
			map.put("message", "user successfully logged in");
			map.put("token", jwtToken);
		} catch (Exception e) {
			String exceptionMessage = e.getMessage();
			map.clear();
			map.put("token", null);
			map.put("message", exceptionMessage);
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	



}
