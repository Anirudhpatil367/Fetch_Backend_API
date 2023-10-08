package com.company.api.fetch.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardsService {

	@Autowired
	private RewardsRepository repo;

	public List<Rewards> getAllRewards() {
		List<Rewards> rewardsList = new ArrayList<Rewards>();
		repo.findAll().forEach(rewardsList::add);
		return rewardsList;
	}
	public void updateRewards(int pointsId, int points) {
		Rewards rewardsObj = null;
		Optional<Rewards> r = repo.findById(pointsId);
		if (r.isPresent()) {
			rewardsObj = r.get();
		}
		if (rewardsObj != null) {
			rewardsObj.setPoints(points);
			repo.save(rewardsObj);
		}
	}
	
	public void addRewards(RewardsInput rewardsInput) {
		Instant instant = Instant.parse(rewardsInput.getTimestamp());
		Rewards rewards = new Rewards();
		rewards.setPayer(rewardsInput.getPayer());
		rewards.setPoints(rewardsInput.getPoints());
		rewards.setTimestamp(instant.getEpochSecond());
		repo.save(rewards);
	}

	

}