package com.example.demo.controller.account;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
			@RequestParam(name="id", defaultValue="") String id,
			@RequestParam(name="name", defaultValue="") String name,
			@RequestParam(name="email") String email,
			@RequestParam(name="introduction", defaultValue="") String introduction,
			@RequestParam(name="password", defaultValue="") String password,
			@RequestParam(name="birthday", defaultValue="") String birthday,
			Model model) throws ParseException {
		// エラーチェック
		List<String> errors = new ArrayList<>();
		List<Account> accountList = accountRepository.findById(id);
		if(id.equals("")) {
			errors.add("IDを入力してください");
			System.out.println("test1");
		} else if(accountList.size() != 0) {
			System.out.println("test2");
			errors.add("入力されたIDはすでに使用されております");
		}
		
		if(name.equals("")) {
			errors.add("名前を入力してください");
		}
		
		if(email.equals("")) {
			errors.add("メールアドレスを入力してください");
		}
		
		if(password.equals("")) {
			errors.add("パスワードを入力してください");
		}
		
		if(birthday.equals("")) {
			errors.add("誕生日を選択してください");
		}
		
		if(errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "/account/signUp";
		} else {
			String imagePath = "";
			LocalDate birthdayDate = LocalDate.parse(birthday);
			Account account = new Account(id, name, email, introduction, password, birthdayDate, imagePath);
			accountRepository.save(account);
			return "/account/signUp";
		}
	}
	
	// ログイン画面
	@GetMapping("/account/sign_in")
	public String index() {
		return "account/login";
	}
	
	@PostMapping("/account/sign_in")
	public String login(
			@RequestParam(name="id", defaultValue="") String id,
			@RequestParam(name="password", defaultValue="") String password,
			Model model) {
		
		List<Account> account = accountRepository.findById(id);
		// エラーチェック
		List<String> errors = new ArrayList<>();
		if(id.equals("")) {
			errors.add("ユーザーIDを入力してください");
		} else if(account.size() == 0) {
			errors.add("アカウントが存在しません");
		}
		
		if(password.equals("")) {
			errors.add("パスワードを入力してください");
		}
		
		if(errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "account/login";
		} else {
			return "account/login";
		}
	}
	
	
	
	
	
	
	
	
	
}