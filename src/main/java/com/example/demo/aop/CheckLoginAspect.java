package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
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
	@Around("execution(* com.example.demo.controller.account.PostController.store*(..)) ||"
			+ "execution(* com.example.demo.controller.account.PostController.create*(..))"
			)
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {
		
		if(sessionAccount == null || sessionAccount.getName() == null || sessionAccount.getName().length() == 0) {
			System.err.println("ログインしていません");
			return "redirect:/account/sign_in";
		}
		return jp.proceed();
	}
}
