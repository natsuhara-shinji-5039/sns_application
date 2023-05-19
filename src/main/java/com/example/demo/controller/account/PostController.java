package com.example.demo.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Post;
import com.example.demo.model.SessionAccount;
import com.example.demo.repository.PostRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	SessionAccount sessionAccount;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/posts/new")
	public String create() {
		return "account/posts/newPost";
	}
	
	@PostMapping("/posts")
	public String store(
			@RequestParam(name="body", defaultValue="") String body,
			Model model) {
		Post post = new Post(sessionAccount.getId(), 1, body);
		postRepository.save(post);
		return "redirect:/posts/new";
	}
}
