package com.company.api.fetch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rewards")
public class Rewards implements Comparable<Rewards> {

	public Rewards() {
		super();
	}

	public Rewards(int pointsId, String payer, int points, long timestamp) {
		super();
		this.payer = payer;
		this.points = points;
		this.timestamp = timestamp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pointsId;

	public int getPointsId() {
		return pointsId;
	}

	private String payer;

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	private int points;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(Rewards r) {
		Long rts = this.timestamp;
		Long crts = r.getTimestamp();
		return rts.compareTo(crts);
	}

}