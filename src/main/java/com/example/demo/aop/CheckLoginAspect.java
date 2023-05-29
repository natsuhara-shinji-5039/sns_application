package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.SessionAccount;

@Aspect
@Component
public class CheckLoginAspect {
	@Autowired
	SessionAccount sessionAccount;
	
	// 
	@Around("execution(* com.example.demo.controller.account.*Controller.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {
		Signature sig = jp.getSignature();
		System.err.println(sig.getDeclaringType().getSimpleName() + "#" + sig.getName());
		//未処理の例外
		// ログイン・新規登録ページの制限解除
		if(sig.getDeclaringType().getSimpleName().equals("AccountController") 
				&& (sig.getName().equals("index") 
						|| sig.getName().equals("signUp")
						|| sig.getName().equals("login")
						|| sig.getName().equals("store")
						|| sig.getName().equals("confirm")
						|| sig.getName().equals("confirmMail")
						|| sig.getName().equals("confirmResult")
						|| sig.getName().equals("resetPassword")
						|| sig.getName().equals("result")
						|| sig.getName().equals("result"))) {
			return jp.proceed();
		}
		
		// 投稿一覧ページのログイン制限解除
		if(sig.getDeclaringType().getSimpleName().equals("PostController") 
				&& (sig.getName().equals("index"))) {
			return jp.proceed();
		}
		
		if(sessionAccount == null || sessionAccount.getName() == null || sessionAccount.getName().length() == 0) {
			System.err.println("ログインしていません");
			return "redirect:/account/sign_in";
		}
		return jp.proceed();
	}
}
