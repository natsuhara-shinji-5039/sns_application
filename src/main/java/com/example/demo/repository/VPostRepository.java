package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VPost;

public interface VPostRepository extends JpaRepository<VPost, Integer> {

}
