package com.example.demo.entity;

import java.time.LocalDateTime;

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
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	public ResetPassword() {
		
	}
	
	public ResetPassword(String id, String userId) {
		this.id = id;
		this.userId = userId;
		this.createdAt = LocalDateTime.now();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
}
