package com.co.s4n.SuCorrientazoDomiclio.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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

	public static Optional<List<String>> getRoutesFromFile(String routesFileLocation) throws HandleRoutesFileException {
		Optional<List<String>> routes = Optional.empty();
		try (Stream<String> stream = Files.lines(Paths.get(routesFileLocation))) {
			routes = Optional.ofNullable(stream.collect(Collectors.toList()));
		} catch (NoSuchFileException | NullPointerException ex) {
			ex.printStackTrace(System.out);
			throw new HandleRoutesFileException("The file was not found", ex);
		} catch (IOException e) {
			e.printStackTrace(System.out);
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
}
