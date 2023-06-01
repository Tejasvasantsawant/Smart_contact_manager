package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.user;

public interface userrepo extends JpaRepository<user, Integer> {

	user findByemail(String email);
	
	
}
