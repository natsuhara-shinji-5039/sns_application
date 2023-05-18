package com.example.demo.controller.account;

import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Controller
public class AccountController {
	@Autowired
	AccountRepository accountRepository;
	
	// 新規登録画面
	@GetMapping("/account/sign_up")
	public String signUp() {
		return "/account/signUp";
	}
	
	// 新規登録処理
	@PostMapping("/account/sign_up")
	public String store(
			@RequestParam(name="name", defaultValue="") String name,
			@RequestParam(name="email") String email,
			@RequestParam(name="introduction", defaultValue="") String introduction,
			@RequestParam(name="password", defaultValue="") String password,
			@RequestParam(name="birthday", defaultValue="") String birthday,
			Model model) throws ParseException {
//		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
//		Date birthdayDate = sdFormat.parse(birthday);
		String userId = "test";
		String imagePath = "";
		LocalDate birthdayDate = LocalDate.parse(birthday);
		System.out.println(userId);
		Account account = new Account(userId, name, email, introduction, password, birthdayDate, imagePath);
		accountRepository.save(account);
		return "/account/signUp";
	}
	
}