package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
	boolean existsByFollowerIdAndFollowedId(String myId, String followId);
	void deleteByFollowerIdAndFollowedId(String myId, String followId);
	
	// フォローされているカウント(一覧)
	@Query(value = "SELECT followed_id, count(*) FROM relationships GROUP BY followed_id", nativeQuery = true)
	public List<Object[]> getFollowedCount();
	
	default Map<String, Long> findFollowedCount() {
		Map<String, Long> map = new HashMap<String, Long>();
		for(int i = 0; i < getFollowedCount().size(); i++) {
			String key = (String) getFollowedCount().get(i)[0];
			Long value = (Long) getFollowedCount().get(i)[1];
			map.put(key, value);
		}
		return map;
	}
	
	// 自分がフォローしたアカウント
	@Query(value = "SELECT followed_id FROM relationships WHERE follower_id = :accountid", nativeQuery = true)
	public Object[] getMyFollow(String accountid);
	
	default List<String> findMyFollow(String accountid) {
		List<String> myFollow = new ArrayList<String>();
		Object[] myFollowObj = getMyFollow(accountid);
		for(int i = 0; i < myFollowObj.length; i++) {
			myFollow.add((String) myFollowObj[i]);
		}
		return myFollow;
	}
}
