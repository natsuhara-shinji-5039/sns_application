package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ResetPassword;
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Integer> {
	List<ResetPassword> findById(String id);
}
