package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
	@Id
	private String id;
	
	private String name;
	
	private String email;
	
	private String introduction;
	
	private String password;
	
	private LocalDate birthday;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	public Account() {
		
	}
	
	public Account(String id, String name, String email, String introduction, String password, LocalDate birthday, String imagePath) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.introduction = introduction;
		this.password = password;
		this.birthday = birthday;
		this.imagePath = imagePath;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	// ゲッター
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	
	public String getPassword() {
		return password;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public LocalDateTime getcreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getupdatedAt() {
		return updatedAt;
	}
}
