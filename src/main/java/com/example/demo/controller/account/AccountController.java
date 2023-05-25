package com.example.demo.controller.account;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Account;
import com.example.demo.model.SessionAccount;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class AccountController {
	@Autowired
	HttpSession session;
	
	@Autowired
	SessionAccount sessionAccount;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	private MailSender mailSender;
	
	// メール改行文字
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
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
			LocalDate birthdayDate = LocalDate.parse(birthday);
			Account account = new Account(id, name, email, introduction, password, birthdayDate);
			accountRepository.save(account);
			sessionAccount.setName(account.getName());
			sessionAccount.setId(account.getId());
			
			SimpleMailMessage msg = new SimpleMailMessage();
		    msg.setTo("shinzi7280.18@gmail.com");// To

		    String insertMessage = "Test from Spring Mail" + LINE_SEPARATOR;
		    insertMessage += "Test from Spring Mail" + LINE_SEPARATOR;

		    msg.setSubject("新規登録の完了");// Set Title
		    msg.setText(insertMessage);// Set Message
		    mailSender.send(msg);
			
			return "redirect:/my_page";
		}
	}
	
	// ログイン画面
	@GetMapping("/account/sign_in")
	public String index() {
		return "account/login";
	}
	
	
	// ログイン処理
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
			sessionAccount.setName(account.get(0).getName());
			sessionAccount.setId(account.get(0).getId());
			return "redirect:/my_page";
		}
	}
	
	// ログアウト
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "account/login";
	}
	
	// アカウントマイページ
	@GetMapping("/my_page")
	public String myPage(Model model) {
		List<Account> account = accountRepository.findById(sessionAccount.getId());
		model.addAttribute("account", account.get(0));
		model.addAttribute("imagePath", account.get(0).getId() + ".png");
		return "account/myPage/myPage";
	}
	
	// アカウント編集ページ
	@GetMapping("/my_page/edit")
	public String edit(Model model) {
		List<Account> account = accountRepository.findById(sessionAccount.getId());
		model.addAttribute("account", account.get(0));
		return "account/myPage/edit";
	}
	
	@PostMapping("/my_page/edit")
	public String upload(
			@RequestParam(name="id", defaultValue="") String id,
			@RequestParam(name="name", defaultValue="") String name,
			@RequestParam(name="email") String email,
			@RequestParam(name="introduction", defaultValue="") String introduction,
			@RequestParam(name="password", defaultValue="") String password,
			@RequestParam(name="birthday", defaultValue="") String birthday,
			@RequestParam(name = "profile_image", defaultValue="") MultipartFile profileImage,
			Model model) throws IOException {
		Account account = accountRepository.findById(id).get(0);
		
//		String originalFileName= profileImage.getOriginalFilename();
//		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//		UUID uuid = UUID.randomUUID();
//        String fileName = uuid.toString() + extension;
//	    Path filePath=Paths.get("static/" + fileName);
		Path filePath=Paths.get("static/" + account.getId() + ".png");
		Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		
		LocalDate birthdayDate = LocalDate.parse(birthday);
		account.setId(id);
		account.setName(name);
		account.setEmail(email);
		account.setPassword(password);
		account.setIntroduction(introduction);
		account.setBirthday(birthdayDate);
		account.setUpdatedAt();
		accountRepository.save(account);
		sessionAccount.setName(account.getName());
		sessionAccount.setId(account.getId());
		return "redirect:/my_page";
	}

	@GetMapping("/")
	public String test() {
		
		return "layouts/template";
	}
	
	
	@PostMapping("/")
	  public String sendMail() {

	      

	    return "index";
	  }
	
	
	
	
}