package com.company.api.fetch.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.api.fetch.entity.Rewards;
import com.company.api.fetch.entity.RewardsInput;
import com.company.api.fetch.entity.RewardsService;
import com.google.gson.Gson;


@SpringBootApplication
@Controller
@RequestMapping("/")
public class ApiController {

	@Autowired
	private RewardsService rewardsService;

	@PostMapping(path = "add")
	public ResponseEntity<String> addRewards(@RequestBody RewardsInput rewardsInput) {
		if (rewardsInput != null) {
			rewardsService.addRewards(rewardsInput);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
		return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "balance", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getRewards() {
		String json;
		List<Rewards> rewardsList = rewardsService.getAllRewards();
		if (rewardsList != null) {
			HashMap<String, Integer> rewardsMap = new HashMap<String, Integer>();
			for (Rewards rewards: rewardsList) {
				String key = rewards.getPayer();
				if (rewardsMap.containsKey(key)) {
					rewardsMap.put(key, rewardsMap.get(key) + rewards.getPoints());
				} else {
					rewardsMap.put(key, rewards.getPoints());
				}
			}
			json = new Gson().toJson(rewardsMap);
		} else {
			rewardsList = new ArrayList<Rewards>();
			json = new Gson().toJson(rewardsList);
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	@PostMapping(path = "spend", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> spendRewards(@RequestBody Map<String, Integer> spend) {
		if (spend != null && spend.get("points") != null) {
			int sum = spend.get("points");
			List<Rewards> rewardsList = rewardsService.getAllRewards();
			if (rewardsList != null) {
				Collections.sort(rewardsList);
				HashMap<String, Integer> rewardsMap = new HashMap<String, Integer>();
				HashMap<Integer, Integer> updateRewards = new HashMap<Integer, Integer>();
				for (int i = 0; i < rewardsList.size() && sum != 0; i++) {
					Rewards r = rewardsList.get(i);
					if (sum > r.getPoints()) {
						sum = sum - r.getPoints();
						updateRewards.put(r.getPointsId(), 0);
						if (rewardsMap.containsKey(r.getPayer())) {
							rewardsMap.put(r.getPayer(), rewardsMap.get(r.getPayer()) - r.getPoints());
						} else {
							rewardsMap.put(r.getPayer(), 0 - r.getPoints());
						}
					} else {
						updateRewards.put(r.getPointsId(), r.getPoints() - sum);
						if (rewardsMap.containsKey(r.getPayer())) {
							rewardsMap.put(r.getPayer(), rewardsMap.get(r.getPayer()) - sum);
						} else {
							rewardsMap.put(r.getPayer(), 0 - sum);
						}
						sum = 0;
					}
				}
				if (sum == 0) {
					updateRewards.forEach((key, value) -> rewardsService.updateRewards(key, value));
					return new ResponseEntity<String>(new Gson().toJson(rewardsMap), HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>("The user doesn’t have enough points!", HttpStatus.BAD_REQUEST);
	}

	

}