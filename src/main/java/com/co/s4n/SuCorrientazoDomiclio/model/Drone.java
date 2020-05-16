package com.co.s4n.SuCorrientazoDomiclio.model;

import java.util.ArrayList;
import java.util.List;

import com.co.s4n.SuCorrientazoDomiclio.util.BussinesInfo;

public class Drone {
	/**
	 * Based on file routes name
	 */
	private String id;
	private int positionX;
	private int positionY;
	private BussinesInfo.CARDINAL_DIRECTIONS orientation;
	private List<String> deliveries;
	private List<String> finalDeliveyPoints;
	
	public Drone(String id, List<String> deliveries) {
		super();
		this.id = id;
		this.deliveries = deliveries;
		this.positionX = 0;
		this.positionY = 0;
		this.orientation = BussinesInfo.CARDINAL_DIRECTIONS.N;
		this.finalDeliveyPoints = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public BussinesInfo.CARDINAL_DIRECTIONS getOrientation() {
		return orientation;
	}

	public void setOrientation(BussinesInfo.CARDINAL_DIRECTIONS orientation) {
		this.orientation = orientation;
	}

	public List<String> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(List<String> deliveries) {
		this.deliveries = deliveries;
	}

	public List<String> getFinalDeliveyPoints() {
		return finalDeliveyPoints;
	}

	public void setFinalDeliveyPoints(List<String> finalDeliveyPoints) {
		this.finalDeliveyPoints = finalDeliveyPoints;
	}

	@Override
	public String toString() {
		return "Drone [id=" + id + ", positionX=" + positionX + ", positionY=" + positionY + ", orientation="
				+ orientation + "]";
	}
}
