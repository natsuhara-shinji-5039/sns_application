package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	private String body;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	// コンストラクタ
	public Post() {
		
	}
	
	public Post(String userId, Integer categoryId, String body) {
		this.userId = userId;
		this.categoryId = categoryId;
		this.body = body;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	// ゲッター
	public Integer getId() {
		return id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public String getBody() {
		return body;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
