package com.co.s4n.SuCorrientazoDomiclio.model;

import com.co.s4n.SuCorrientazoDomiclio.util.BussinesInfo;

public class Movement {
	
	private final BussinesInfo.CARDINAL_DIRECTIONS cardinalDirection;
	private final int valueMovement;
	
	public Movement(BussinesInfo.CARDINAL_DIRECTIONS cardinalDirection, int valueMovement) {
		super();
		this.cardinalDirection = cardinalDirection;
		this.valueMovement = valueMovement;
	}

	public BussinesInfo.CARDINAL_DIRECTIONS getCardinalDirection() {
		return cardinalDirection;
	}

	public int getValueMovement() {
		return valueMovement;
	}
}
