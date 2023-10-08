package com.company.api.fetch.entity;

public class RewardsInput {

	private String payer;
	private int points;
	private String timestamp;
	
	public RewardsInput() {
		super();
	}
	
	public RewardsInput(String payer, int points, String timestamp) {
		super();
		this.payer = payer;
		this.points = points;
		this.timestamp = timestamp;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
