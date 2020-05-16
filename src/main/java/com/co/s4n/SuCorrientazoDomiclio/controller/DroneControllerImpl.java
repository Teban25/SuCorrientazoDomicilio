package com.co.s4n.SuCorrientazoDomiclio.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.co.s4n.SuCorrientazoDomiclio.exception.HandleRoutesFileException;
import com.co.s4n.SuCorrientazoDomiclio.exception.InvalidNameException;
import com.co.s4n.SuCorrientazoDomiclio.exception.NotRoutesFoundInFile;
import com.co.s4n.SuCorrientazoDomiclio.model.Drone;
import com.co.s4n.SuCorrientazoDomiclio.services.DroneServices;
import com.co.s4n.SuCorrientazoDomiclio.services.DroneServicesImpl;
import com.co.s4n.SuCorrientazoDomiclio.util.RoutesFileReader;

public class DroneControllerImpl implements DroneController {

	private DroneServices droneServices;
	private ExecutorService executor = Executors.newFixedThreadPool(10);

	@Override
	public Optional<List<Path>> getAllDronesPaths() {
		Optional<List<Path>> allDronesPaths = Optional.empty();
		Optional<String> resourcePath = RoutesFileReader.getMainFolderResourcesPath();
		if (resourcePath.isPresent() && !resourcePath.get().isEmpty()) {
			String resourceInPath = resourcePath.get().concat("\\in");
			allDronesPaths = RoutesFileReader.getResourcesFromPath(resourceInPath);
		}
		return allDronesPaths;
	}

	@Override
	public List<Drone> deliveryingLunchs(List<Path> dronesPaths) {
		List<Drone> dronesDelivering = new ArrayList<>();
		dronesPaths.forEach(x -> {
			try {
				Optional<List<String>> droneRoutes = RoutesFileReader.getRoutesFromFile(x.toFile().getAbsolutePath());

				if (!droneRoutes.isPresent() || droneRoutes.get().isEmpty()) {
					throw new NotRoutesFoundInFile();
				}

				Drone droneToDelivering = new Drone(getDroneId(x), droneRoutes.get());
				dronesDelivering.add(droneToDelivering);
				
				executor.execute(()->getDroneServices().makeDeliveries(droneToDelivering));
			} catch (HandleRoutesFileException e) {
				e.printStackTrace(System.err);
				System.out.println("El archivo " + x.toFile().getName() + " no se pudo cargar al dron.");
			} catch (NotRoutesFoundInFile ex) {
				ex.printStackTrace(System.err);
				System.out.println("El archivo " + x.toFile().getName() + " no contenia ninguna ruta.");
			} catch (InvalidNameException ex) {
				ex.printStackTrace(System.err);
			}
		});
		
		try {
			executor.awaitTermination(2,  TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executor.shutdown();
		return dronesDelivering;
	}

	@Override
	public void writeOutDronesFiles(List<Drone> dronesDeliveryFinished) {
		dronesDeliveryFinished
				.forEach(x -> RoutesFileReader.writeFinalDeliveredPoints(x.getFinalDeliveyPoints(), x.getId()));
	}

	private String getDroneId(Path droneRouteFile) throws InvalidNameException {
		String droneName = "";
		try {
			droneName = droneRouteFile.toFile().getName().split("in")[1].split(".txt")[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace(System.err);
			throw new InvalidNameException("The file name does not have the propouse pattern", e);
		}
		return droneName;
	}

	private DroneServices getDroneServices() {
		if (this.droneServices == null) {
			droneServices = new DroneServicesImpl();
		}

		return this.droneServices;
	}
}
