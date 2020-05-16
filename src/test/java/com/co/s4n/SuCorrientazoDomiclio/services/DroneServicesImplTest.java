package com.co.s4n.SuCorrientazoDomiclio.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.co.s4n.SuCorrientazoDomiclio.model.Drone;

public class DroneServicesImplTest {

	private DroneServicesImpl droneServicesImpl = new DroneServicesImpl();
	
	@Test
	public void testThat_returnEndFinalPoints_whenDroneIsDelivering() {
		// GIVEN
		List<String> routes = Arrays.asList("AAAAIAA","DDDAIAD");
		Drone drone = new Drone("01",routes);
		// WHEN
		droneServicesImpl.makeDeliveries(drone);
		// THEN
		assert drone.getFinalDeliveyPoints().size() == 2;
		assert drone.getFinalDeliveyPoints().contains("(-2,4) direccion Occidente");
		assert drone.getFinalDeliveyPoints().contains("(-1,3) direccion Sur");
	}
	
	@Test
	public void testThat_returnEndFinalPoints_whenDroneIsDeliveringMoreThanThreeDeliversAndOneHasAWronMove() {
		// GIVEN
		List<String> routes = Arrays.asList("AAAAIAA","DDDAIAD","AAIADAD","AAVIA");
		Drone drone = new Drone("01",routes);
		// WHEN
		droneServicesImpl.makeDeliveries(drone);
		// THEN
		assert drone.getFinalDeliveyPoints().size() == 4;
		assert drone.getFinalDeliveyPoints().contains("(-2,4) direccion Occidente");
		assert drone.getFinalDeliveyPoints().contains("(-1,3) direccion Sur");
		assert drone.getFinalDeliveyPoints().contains("(0,0) direccion Occidente");
		assert drone.getFinalDeliveyPoints().contains("(-1,2) direccion Occidente");
	}
}
