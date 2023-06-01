package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.userrepo;
import com.example.entities.user;

@Service
public class service {
	
	@Autowired
	private userrepo userrep;
	
	
	public boolean hello(String email,String password) 
	{
		user u = this.userrep.findByemail(email);
		if (u.getEmail().equals(email)&&u.getPassword().equals(password))
		{
			return true;
		}
		return false;
	}

}
