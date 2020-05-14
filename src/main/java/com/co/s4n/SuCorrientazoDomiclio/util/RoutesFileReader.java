package com.co.s4n.SuCorrientazoDomiclio.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Its function is get the routes to a drone
 * 
 * @author Teban
 *
 */
public class RoutesFileReader {

	public static Optional<List<String>> getRoutesFromFile(String routesFileLocation) throws IOException {
		Optional<List<String>> routes = Optional.empty();
		try (Stream<String> stream= Files.lines(Paths.get(routesFileLocation))) {
			
		} catch (IOException e) {
			e.printStackTrace(System.out);
			throw new IOException(
					"There was an error trying to load the routes file, technical error: " + e.getMessage());
		}
		return routes;
	}
}
