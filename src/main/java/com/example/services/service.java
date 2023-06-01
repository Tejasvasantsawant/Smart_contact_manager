package com.example.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.dao.conrep;
import com.example.dao.userrepo;
import com.example.entities.contact;
import com.example.entities.user;

@Service
public class service {
	
	@Autowired
	private userrepo userrep;
	
	@Autowired
	conrep c;
	
	
	public boolean hello(String email,String password) 
	{
		try
		{
		user u = this.userrep.findByemail(email);
		if (u.getEmail().equals(email)&&u.getPassword().equals(password))
		{
			return true;
		}
		}
		catch (Exception e) {
			return false;
		}
		return false;
	}

	public user fetch(String email)
	{
		return this.userrep.findByemail(email);
	}
	
	public contact findbyid(int id)
	{
		return this.c.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"service not found");
		});
	}
	
	public contact update(int cid,contact c)
	{
		contact ct = this.findbyid(cid);
		ct.setName(c.getName());
		ct.setEmail(c.getEmail());
		ct.setPhone(c.getPhone());
		return this.c.save(ct);
	}
	
	public String byid(int id)
	{
		c.deleteById(id);
		return "deleted";
	}
	
}
