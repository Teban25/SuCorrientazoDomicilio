package com.co.s4n.SuCorrientazoDomiclio;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.co.s4n.SuCorrientazoDomiclio.controller.DroneController;
import com.co.s4n.SuCorrientazoDomiclio.controller.DroneControllerImpl;
import com.co.s4n.SuCorrientazoDomiclio.model.Drone;

public class Main {

	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	public static void main(String[] args) {
		DroneController droneController = new DroneControllerImpl();
		
		//Get all drones paths to make deliveries
		Optional<List<Path>> dronesPaths = droneController.getAllDronesPaths();
		if (!dronesPaths.isPresent() && dronesPaths.get().isEmpty()) {
			LOGGER.log(Level.INFO,"No es posible ejecutar las operaciones de entrega con los drones,"
					+ " porque no hay ningun archivo tipo in para operar.");
			System.exit(0);
		}
		//Deleting the main folder path
		List<Path> dronesPathsWithOutFolder = dronesPaths.get().subList(1, dronesPaths.get().size());
		
		//Make the delivering to all drones
		List<Drone> dronesDeliveringFinished = droneController.deliveryingLunchs(dronesPathsWithOutFolder);
		
		// Finally, write all out files
		droneController.writeOutDronesFiles(dronesDeliveringFinished);
	}
}
