package com.example.demo.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {
	
	@GetMapping("/posts/new")
	public String create() {
		return "account/posts/newPost";
	}
}
