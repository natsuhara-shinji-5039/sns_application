package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "relationships")
public class Relationship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "follower_id")
	private String followerId;
	
	@Column(name = "followed_id")
	private String followedId;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	public Relationship() {
		
	}
	
	public Relationship(String myId, String followId) {
		this.followerId = myId;
		this.followedId = followId;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	public String getFollowerId() {
		return followerId;
	}
	
	public String getFollowedId() {
		return followedId;
	}
}
