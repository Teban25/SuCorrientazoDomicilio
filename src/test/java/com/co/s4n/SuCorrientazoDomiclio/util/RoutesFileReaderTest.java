package com.co.s4n.SuCorrientazoDomiclio.util;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class RoutesFileReaderTest {

	/**
	 * Test that throw an IOException when a file is corrupted or there was any
	 * problem handle the file
	 * 
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void testThat_notGetAnyRoutes_whenThereIsNotFile() throws IOException {
		// GIVEN
		String fakeLocation = "fakeLocation.txt";
		// WHEN
		Optional<List<String>> emptyRoutes = RoutesFileReader.getRoutesFromFile(fakeLocation);
		// THEN
		assert !emptyRoutes.isPresent();
	}
	
	/**
	 * Test that throw an IOException when a file is corrupted or there was any
	 * problem handle the file
	 * 
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void testThat_throwIOException_whenFileLocationIsNull() throws IOException {
		// GIVEN
		String fakeLocation = null;
		// WHEN
		Optional<List<String>> emptyRoutes = RoutesFileReader.getRoutesFromFile(fakeLocation);
		// THEN
		assert !emptyRoutes.isPresent();
	}

	/**
	 * Test that check when a file is given correctly but is empty, the process
	 * return an empty collection.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testThat_notGetAnyRoutes_whenFileIsEmpty() throws IOException {
		// GIVEN
		String fakeLocation = "t";
		// WHEN
		Optional<List<String>> emptyRoutes = RoutesFileReader.getRoutesFromFile(fakeLocation);
		// THEN
		assert emptyRoutes.get().isEmpty();
	}

	/**
	 * Test that check when a file is given correctly, the process return some
	 * routes.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testThat_getSomeRoutes_whenFileExists() throws IOException {
		// GIVEN
		String fakeLocation = "t";
		// WHEN
		Optional<List<String>> emptyRoutes = RoutesFileReader.getRoutesFromFile(fakeLocation);
		// THEN
		assert !emptyRoutes.get().isEmpty();
	}
}
