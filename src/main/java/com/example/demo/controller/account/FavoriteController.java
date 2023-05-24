package com.example.demo.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Favorite;
import com.example.demo.model.SessionAccount;
import com.example.demo.repository.FavoriteRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class FavoriteController {
	@Autowired
	HttpSession session;
	
	@Autowired
	SessionAccount sessionAccount;
	
	@Autowired
	FavoriteRepository favoriteRepository;
	
	@RequestMapping("/posts/{id}/favorites")
	@Transactional
	public String favorite(@PathVariable("id") Integer postId) {
		System.out.println("test");
		if(favoriteRepository.existsByUserIdAndPostId(sessionAccount.getId(), postId) == true) {
			System.out.println("test2");
			System.out.println(sessionAccount.getId());
			System.out.println(postId);
			favoriteRepository.deleteByUserIdAndPostId(sessionAccount.getId(), postId);
			System.out.println("test9");
		} else {
			System.out.println("test3");
			Favorite favorite = new Favorite(sessionAccount.getId(), postId);
			System.out.println("test4");
			favoriteRepository.save(favorite);
		}
		return "redirect:/posts";
	}
}
