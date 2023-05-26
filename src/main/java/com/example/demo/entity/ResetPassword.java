package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reset_passwords")
public class ResetPassword {
	
	@Id
	private String id;
	
	@Column(name = "user_id")
	private String userId;
	
	private String email;
	
	public ResetPassword() {
		
	}
}
