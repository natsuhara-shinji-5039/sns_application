package com.example.demo.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Relationship;
import com.example.demo.model.SessionAccount;
import com.example.demo.repository.RelationshipRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class RelationshipController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	SessionAccount sessionAccount;
	
	@Autowired
	RelationshipRepository relationshipRepository;
	
	@RequestMapping("/accounts/{id}/follow")
	@Transactional
	public String follow(@PathVariable("id") String followId) {
		if(relationshipRepository.existsByFollowerIdAndFollowedId(sessionAccount.getId(), followId) == true) {
			relationshipRepository.deleteByFollowerIdAndFollowedId(sessionAccount.getId(), followId);
		} else {
			Relationship relationship = new Relationship(sessionAccount.getId(), followId);
			relationshipRepository.save(relationship);
		}
		return "redirect:/posts";
	}
}
