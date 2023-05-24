package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	boolean existsByUserIdAndPostId(String userId, Integer PostId);
	void deleteByUserIdAndPostId(String userId, Integer postId);
	
	@Query(value = "SELECT post_id, count(*) FROM favorites GROUP BY post_id", nativeQuery = true)
	public List<Object[]> getFavoriteCount();
	
	default Map<Integer, Long> findFavoriteCount() {
		Map<Integer, Long> map = new HashMap<Integer, Long>();
		for(int i = 0; i < getFavoriteCount().size(); i++) {
			int key = (int) getFavoriteCount().get(i)[0];
			System.out.println("test5");
			Long value = (Long) getFavoriteCount().get(i)[1];
			map.put(key, value);
		}
		return map;
	}
	
	
	@Query(value = "SELECT post_id FROM favorites WHERE user_id = :userid", nativeQuery = true)
	public Object[] getMyFavorites(String userid);
	
	default List<Integer> findMyFavorites(String userid) {
		List<Integer> myFavorites = new ArrayList<Integer>();
		Object[] myFavoriteObj = getMyFavorites(userid);
		for(int i = 0; i < myFavoriteObj.length; i++) {
			myFavorites.add((Integer)myFavoriteObj[i]);
		}
		return myFavorites;
	}
}
