package com.example.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.conrep;
import com.example.dao.userrepo;
import com.example.entities.contact;
import com.example.entities.user;

import com.example.services.service;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController

public class homecontroller {
	
	@Autowired
	private userrepo userrep;
	
	@Autowired
	private conrep conre;
	
	@Autowired
	private service sr;
	
	@GetMapping("/signin")
	public ModelAndView customlogin(Model model)
	{
		ModelAndView mv=new ModelAndView("login");
		model.addAttribute("title", "home- smart contact manager");
		return mv;
		
	}
	
	@PostMapping("/sig")
	public ModelAndView signin(@RequestParam("username") String email, @RequestParam("password") String password)
	{
		user u = this.sr.fetch(email);
		
		boolean log=sr.hello(email, password);
		if(log==true)
		{
		ModelAndView mv= new ModelAndView("landing");
		mv.addObject("u",u);
		return mv;
		}
		ModelAndView mv=new ModelAndView("login");
		mv.addObject("msg","Invalid Id or Password");
		return mv;
		
		
	}		

	
	
	
	
	
	@RequestMapping("/")
	public ModelAndView home(Model model)
	{
		ModelAndView mv=new ModelAndView("home");
		model.addAttribute("title", "home- smart contact manager");
		return mv;
	}
	
	@RequestMapping("/about")
	public ModelAndView about(Model model)
	{
		ModelAndView mv=new ModelAndView("about");
		model.addAttribute("title", "about- smart contact manager");
		return mv;
	}
	
	@RequestMapping("/signup")
	public ModelAndView signup(Model model)
	{
		ModelAndView mv=new ModelAndView("signup");
		model.addAttribute("title", "register- smart contact manager");
		model.addAttribute("user",new user());
		return mv;
	}
	
	@PostMapping(value="/do_register")
	public ModelAndView registeruser(@ModelAttribute("user") @Valid user user ,Model model,HttpSession session) 
	{
		try {
			ModelAndView mv=new ModelAndView("signup");
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageurl("default.png");
			
			System.out.println("user"+user);
			
			user result=this.userrep.save(user);
			
			model.addAttribute("user",new user());
			
			return mv;

		

			
		} 
		
		catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv=new ModelAndView("signup");
			mv.addObject("msg","name is not proper");
			return mv;
		}
			
		
			
	}
	
	@GetMapping("/connup")
	public ModelAndView addcontact(Model model)
	{
		ModelAndView mv=new ModelAndView("regcon");
		model.addAttribute("title", "registercon- smart contact manager");
		model.addAttribute("contact",new contact());
		return mv;
	}
	
	@PostMapping("/do_con")
	public ModelAndView addcon(@ModelAttribute("contact") @Valid contact contact ,Model model)
	{
		ModelAndView mv=new ModelAndView("landing");
		
		contact.setImage("default.png");
		
		
		System.out.println("contact"+contact);
		
		contact result=this.conre.save(contact);
		
		model.addAttribute("contact",new contact());
		
		return mv;

		
	}
	
	@GetMapping("/viewcontacts/{name}")
	public ModelAndView viewContacts(@PathVariable("name") String email)
	{
		
	    
	    Iterable<contact> contact=conre.findByname(email);
	    ModelAndView mv = new ModelAndView("viewcontacts");
	    mv.addObject("contact", contact);
	    
	    return mv;
	}
	
	@GetMapping("/editContact/{cid}")
	public ModelAndView editContact(@PathVariable("cid") int email)
	{
	   contact c= this.sr.findbyid(email);
	    ModelAndView mv = new ModelAndView("update");
	    mv.addObject("c", c);
	    return mv;
		
	}
	
	@PostMapping("/updateContact/{cid}")
	public ModelAndView updateContact(@PathVariable("cid") int id, @RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("phone") String phone, Model model) 
	{
		
		contact con= this.sr.findbyid(id);
		con.setName(name);
		con.setEmail(email);
		con.setPhone(phone);
		conre.save(con);
	    ModelAndView mv = new ModelAndView("update");
	    mv.addObject("c",con);
	    mv.addObject("msg","updated Successfully");
	    return mv;

	}
	
	@RequestMapping("/delete/{cid}")	
	public ModelAndView delete(@PathVariable("cid") int cid)
	{
		sr.byid(cid);
		ModelAndView mv=new ModelAndView("delete");
		mv.addObject("msg","Deleted Successfully");
		return mv;
		
	}
	
}
