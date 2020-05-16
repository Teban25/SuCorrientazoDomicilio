package com.co.s4n.SuCorrientazoDomiclio.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.co.s4n.SuCorrientazoDomiclio.exception.HandleRoutesFileException;

/**
 * Its function is to get the routes to a drone
 * 
 * @author Teban
 *
 */
public class RoutesFileReader {

	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	
	public static Optional<List<String>> getRoutesFromFile(String routesFileLocation) throws HandleRoutesFileException {
		Optional<List<String>> routes = Optional.empty();
		try (Stream<String> stream = Files.lines(Paths.get(routesFileLocation))) {
			routes = Optional.ofNullable(stream.collect(Collectors.toList()));
		} catch (NoSuchFileException | NullPointerException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
			throw new HandleRoutesFileException("The file was not found", ex);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			throw new HandleRoutesFileException("There was an error trying to load the routes file, technical error: ",
					e);
		}
		return routes;
	}

	public static Optional<String> getResourcePath(String nameRoutesFile) {
		Optional<String> path = Optional.empty();
		try {
			path = Optional.ofNullable(Paths.get(ClassLoader.getSystemResource(nameRoutesFile).toURI()).toString());
		} catch (URISyntaxException | NullPointerException e) {
			e.printStackTrace(System.out);
		}
		return path;
	}

	public static Optional<String> getMainFolderResourcesPath() {
		Optional<String> path = Optional.ofNullable(Paths.get("src", "main", "resources").toFile().getAbsolutePath());
		return path;
	}

	public static Optional<List<Path>> getResourcesFromPath(String pathFolder) {
		Optional<List<Path>> routesFiles = Optional.empty();
		try {
			routesFiles = Optional.ofNullable(Files.walk(Paths.get(pathFolder)).collect(Collectors.toList()));
		} catch (NoSuchFileException | NullPointerException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

		return routesFiles;
	}

	public static void writeFinalDeliveredPoints(List<String> finalDeliveyPoints, String droneId) {
		String content = "\n== Reporte de entregas ==";
		Optional<String> resourcePath = getMainFolderResourcesPath();
		if(!resourcePath.isPresent() || resourcePath.get().isEmpty()) {
			LOGGER.log(Level.INFO, "No se puede almacenar los puntos de entrega del dron "+ droneId+
					" Porque la url de recursos no fue encontrada.");
		}
		
		String resourceOutPath = resourcePath.get() + "\\out\\out" + droneId + ".txt";
		
		for (String endPoint : finalDeliveyPoints) {
			content = content.concat("\n" + endPoint);
		}
		
		try {
			Files.write(Paths.get(resourceOutPath), 
					content.getBytes(),
	                StandardOpenOption.CREATE, 
	                StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "No se puede almacenar los puntos de entrega del dron "+ droneId+
					" sucedio un error mientras se escribia el archivo.");
		}
	}
}
