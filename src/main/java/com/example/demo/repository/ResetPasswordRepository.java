package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ResetPassword;
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Integer> {

}
