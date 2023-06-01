package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.contact;


public interface conrep extends JpaRepository<contact, Integer> {

	
	Iterable<contact> findByname(String name);
}
