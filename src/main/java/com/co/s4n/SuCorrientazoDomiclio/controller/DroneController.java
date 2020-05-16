package com.co.s4n.SuCorrientazoDomiclio.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import com.co.s4n.SuCorrientazoDomiclio.model.Drone;

public interface DroneController {

	Optional<List<Path>> getAllDronesPaths();
	List<Drone> deliveryingLunchs(List<Path> dronesPaths);
	void writeOutDronesFiles(List<Drone> dronesDeliveryFinished);
}
