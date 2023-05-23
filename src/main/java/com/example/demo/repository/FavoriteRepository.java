package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	boolean existsByUserIdAndPostId(String userId, Integer PostId);
	void deleteByUserIdAndPostId(String userId, Integer PostId);
}
