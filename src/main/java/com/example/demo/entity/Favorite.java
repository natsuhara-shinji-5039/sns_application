package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite")
public class Favorite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "post_id")
	private Integer postId;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	public Favorite() {
		
	}
	
	public Favorite(String userId, Integer postId) {
		this.userId = userId;
		this.postId = postId;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
