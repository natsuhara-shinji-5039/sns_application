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
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Account;
import com.example.demo.model.SessionAccount;
import com.example.demo.repository.AccountRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	private JavaMailSender sender;
	
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
		    msg.setTo(account.getEmail());// To

		    String insertMessage = "登録完了しました。" + LINE_SEPARATOR;
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
//		一意な画像ファイル名の作成
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
	
	// パスワードリセットメール確認画面
	@GetMapping("/account/reset_password") 
	public String confirm(){
		return "account/resetPassword/setting";
	}
	
	@PostMapping("/account/confirm/mail")
	public String confirmMail(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "confirm_email", defaultValue = "") String confirmEmail,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws MessagingException {
		// エラーチェック
		System.out.println("test1");
		List<String> errors = new ArrayList<>();
		if(email.equals("")) {
			System.out.println("test2");
			errors.add("メールアドレスを入力してください");
		}
		if(confirmEmail.equals("")) {
			System.out.println("test3");
			errors.add("確認用メールアドレスを入力して下さい");
		}
		if(errors.size() == 0 && !email.equals(confirmEmail)) {
			System.out.println("test4");
			errors.add("メールアドレスが一致しません");
		}
		System.out.println("test5");
		List<Account> account = accountRepository.findByEmail(email);
		if(errors.size() == 0 && account.size() == 0) {
			System.out.println("test6");
			errors.add("お使いのメールアドレスのアカウントが存在しません");
		}
		
		if(errors.size() != 0) {
			System.out.println("test7");
			model.addAttribute("errors", errors);
			return "account/resetPassword/setting";
		} else {
			UUID uuid = UUID.randomUUID();
			
//			while() {
//				
//			};
			
			// urlの作成
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/reset_password/" + uuid.toString();
			
//			SimpleMailMessage msg = new SimpleMailMessage();
//		    msg.setTo(email);
		    
		    String insertMessage = "<html>"
	          + "<head></head>"
	          + "<body>"
	          + "<p>以下のurlからパスワードの再設定を行ってください。</p>"
	          + "<p><a href='" + url + "'> " +  url +"</a></p>"
	          + "<p>また、パスワード再設定の有効時間は30分です。</p>"
	          + "</body>"
	          + "</html>";
		    MimeMessage message = sender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message, true);
		    helper.setText("本文", insertMessage);
		    helper.setSubject("パスワードの再設定");
			helper.setTo(email);
		    
		    sender.send(message);

//		    String insertMessage = "以下のurlからパスワードの再設定を行ってください。" + LINE_SEPARATOR;
//		    insertMessage += url + LINE_SEPARATOR;
//		    insertMessage += "また、パスワード再設定の有効時間は30分です。" + LINE_SEPARATOR;

//		    msg.setSubject("パスワードの再設定");// Set Title
//		    msg.setText(insertMessage);// Set Message
//		    mailSender.send(msg);
			
			return "redirect:/account/confirm";
		}
	}
	
	@GetMapping("/account/confirm")
	public String confirmResult() {
		return "account/resetPassword/confirm";
	}
	
	@GetMapping("/reset_password/{id}")
	public String resetPassword() {
		return "account/resetPassword/result";
	}

	@GetMapping("/")
	public String test() {
		
		return "layouts/template";
	}
	
	
	
	
}