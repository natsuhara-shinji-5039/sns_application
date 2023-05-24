package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "v_posts")
public class VPost {
	@Id
	private Integer id;
	
	private String name;
	
	@Column(name = "user_id")
	private String userId;
	
	private String body;
	
	@Transient
	private Integer favoriteCount;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	// ゲッター
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUserId() {
		return userId;
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
