package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
	boolean existsByFollowerIdAndFollowedId(String myId, String followId);
	void deleteByFollowerIdAndFollowedId(String myId, String followId);
}
