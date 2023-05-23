package com.example.demo.controller.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Post;
import com.example.demo.entity.VPost;
import com.example.demo.model.SessionAccount;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.VPostRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	SessionAccount sessionAccount;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	VPostRepository vPostRepository;
	
	// 新規登録ページ
	@GetMapping("/posts/new")
	public String create() {
		return "account/posts/newPost";
	}
	
	// 新規登録処理
	@PostMapping("/posts")
	public String store(
			@RequestParam(name="body", defaultValue="") String body,
			Model model) {
		Post post = new Post(sessionAccount.getId(), body);
		postRepository.save(post);
		return "redirect:/posts";
	}
	
	// 一覧ページ
	@GetMapping("/posts")
	public String index(Model model) {
		List<VPost> posts = vPostRepository.findAllByOrderByIdDesc();
		model.addAttribute("posts", posts);
		return "account/posts/index";
	}
	
	// 詳細ページ
	@GetMapping("/posts/{id}")
	public String show(
			@PathVariable("id") Integer id,
			Model model) {
		VPost post = vPostRepository.findById(id).get();
		model.addAttribute("post", post);
		return "account/posts/show";
	}
	
	// 投稿削除
	@PostMapping("posts/{id}/delete")
	public String delete(@PathVariable("id") Integer id) {
		postRepository.deleteById(id);
		return "redirect:/posts";
	}
}
