package com.co.s4n.SuCorrientazoDomiclio.services;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.co.s4n.SuCorrientazoDomiclio.model.Drone;
import com.co.s4n.SuCorrientazoDomiclio.util.BussinesInfo;

public class DroneServicesImpl implements DroneServices{

	@Override
	public void makeDeliveries(Drone drone) {
		final int totalDeliveries = drone.getDeliveries().size();

		for (int i = 0; i < totalDeliveries; i = i + 3) {
			List<String> nextDeliveries = (i + 3) > totalDeliveries ? drone.getDeliveries().subList(i, totalDeliveries)
					: drone.getDeliveries().subList(i, BussinesInfo.ORDERS_BY_DELIVERY_IN_DRONE);

			deliveringLunch(nextDeliveries, drone);
			returnHome(drone);
		}
	}

	private void deliveringLunch(List<String> nextDeliveries, Drone drone) {
		for (String route : nextDeliveries) {
			List<Character> movesByElement = convertRoute(route);
			movesByElement.forEach(x -> makeDroneMove(x, drone));
			registerEachFinalDeliveryPoint(drone);
		}
	}

	private void registerEachFinalDeliveryPoint(Drone drone) {
		String finalPoint = getFinalDeliveryPoint(drone);
		drone.getFinalDeliveyPoints().add(finalPoint);
	}

	private String getFinalDeliveryPoint(Drone drone) {
		return "(" + drone.getPositionX() + "," + drone.getPositionY() + ") "
				+ BussinesInfo.USER_DIRECTION_TRANSLATE.get(drone.getOrientation());
	}

	private void returnHome(Drone drone) {
		drone.setPositionX(0);
		drone.setPositionY(0);
		drone.setOrientation(BussinesInfo.CARDINAL_DIRECTIONS.N);
	}

	private List<Character> convertRoute(String route) {
		List<Character> separatedMovesByElements = route.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
		return separatedMovesByElements;
	}

	private void makeDroneMove(Character movement, Drone drone) {
		if (isGoForwad.test(movement)) {
			makeGoForward.accept(drone.getOrientation(), drone);
		} else if (isDirectionRight.test(movement)) {
			drone.setOrientation(nextRightDirection.apply(drone.getOrientation().name().charAt(0)));
		} else if (isDirectionLeft.test(movement)) {
			drone.setOrientation(nextLeftDirection.apply(drone.getOrientation().name().charAt(0)));
		} else {
			System.out.println("Invalid move, going forward to the next one");
		}
	}

	private final Predicate<BussinesInfo.CARDINAL_DIRECTIONS> isNorth = p -> p.name().equals("N");
	private final Predicate<BussinesInfo.CARDINAL_DIRECTIONS> isSouth = p -> p.name().equals("S");
	private final Predicate<Character> isGoForwad = p -> p.equals('A');
	private final Predicate<Character> isDirectionRight = p -> p.equals('D');
	private final Predicate<Character> isDirectionLeft = p -> p.equals('I');

	private final Function<Character, BussinesInfo.CARDINAL_DIRECTIONS> nextRightDirection = x -> BussinesInfo.MOVES_TO_RIGHT
			.get(x);
	private final Function<Character, BussinesInfo.CARDINAL_DIRECTIONS> nextLeftDirection = x -> BussinesInfo.MOVES_TO_LEFT
			.get(x);

	private BiConsumer<BussinesInfo.CARDINAL_DIRECTIONS, Drone> makeGoForward = (orientation, drone) -> {
		if (isNorth.or(isSouth).test(orientation)) {
			drone.setPositionY(drone.getPositionY()
					+ BussinesInfo.MOVEMENTS_BY_DIRECTION.get(orientation.name().charAt(0)).getValueMovement());
		} else {
			drone.setPositionX(drone.getPositionX()
					+ BussinesInfo.MOVEMENTS_BY_DIRECTION.get(orientation.name().charAt(0)).getValueMovement());
		}
	};
}
