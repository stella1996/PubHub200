package com.pubhub200.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

	@GetMapping("/")
	public String index(){
		System.out.println("Index");
		return "index";
	}
}
