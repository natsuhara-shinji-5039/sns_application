package com.example.demo.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FavoriteController {
	
	@PostMapping("/posts/{id}/favorites")
	public String create(@PathVariable("id") Integer id) {
		
		return "redirect:";
	}
}
