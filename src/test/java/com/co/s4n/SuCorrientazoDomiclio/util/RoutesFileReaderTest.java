package com.co.s4n.SuCorrientazoDomiclio.util;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.co.s4n.SuCorrientazoDomiclio.exception.HandleRoutesFileException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RoutesFileReader.class,Files.class,Paths.class})
public class RoutesFileReaderTest {
	
	private final String FAKE_LOCATION = "/fakeLocation";
	private final String FAKE_FILE = "fake01.txt";
	private final String IN_PATH = "\\in\\";

	/**
	 * Test that get the path of the routes files
	 */
	@Test
	public void testThat_getResourcePathEmptyValue_whenFileNameNotExists() {
		// GIVEN
		// WHEN
		Optional<String> resourcePath = RoutesFileReader.getResourcePath(FAKE_LOCATION);
		// THEN
		assert !resourcePath.isPresent();
	}

	/**
	 * Test that get the path of the routes files
	 */
	@Test
	public void testThat_getResourcePath_whenFileNameExists() {
		// GIVEN
		final String nameRoutesFile = IN_PATH.concat("in01.txt");
		// WHEN
		final Optional<String> resourcePath = RoutesFileReader.getResourcePath(nameRoutesFile);
		// THEN
		assert resourcePath.isPresent();
		assert resourcePath.get().contains(nameRoutesFile);
	}

	/**
	 * Test that throw an IOException when a file is corrupted or there was any
	 * problem handle the file
	 * 
	 * @throws IOException
	 * @throws HandleRoutesFileException
	 */
	@Test(expected = HandleRoutesFileException.class)
	public void testThat_throwHandleRoutesFileException_whenResourcePathNotExists() throws HandleRoutesFileException {
		// GIVEN
		// WHEN
		RoutesFileReader.getRoutesFromFile(FAKE_FILE);
		// THEN
	}

	/**
	 * Test that throw an IOException when a file is corrupted or there was any
	 * problem handle the file
	 * 
	 * @throws IOException
	 */
	@Test(expected = HandleRoutesFileException.class)
	public void testThat_throwIOException_whenFileLocationIsNull() throws HandleRoutesFileException {
		// GIVEN
		final String fakeLocation = null;
		// WHEN
		RoutesFileReader.getRoutesFromFile(fakeLocation);
		// THEN
	}

	/**
	 * Test that check when a file is given correctly but is empty, the process
	 * return an empty collection.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testThat_notGetAnyRoutes_whenFileIsEmpty() throws HandleRoutesFileException {
		// GIVEN
		final Optional<String> path = RoutesFileReader.getResourcePath(IN_PATH.concat("in03.txt"));
		// WHEN
		if (!path.isPresent()) {
			fail("Path not exists!");
		}
		Optional<List<String>> emptyRoutes = RoutesFileReader.getRoutesFromFile(path.get());
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
	public void testThat_getSomeRoutes_whenFileExists() throws HandleRoutesFileException {
		// GIVEN
		final Optional<String> path = RoutesFileReader.getResourcePath(IN_PATH.concat("in01.txt"));
		// WHEN
		if (!path.isPresent()) {
			fail("Path not exists!");
		}

		Optional<List<String>> emptyRoutes = RoutesFileReader.getRoutesFromFile(path.get());
		// THEN
		assert !emptyRoutes.get().isEmpty();
	}

	@Test(expected = HandleRoutesFileException.class)
	public void testThat_throwIOException_whenSomethingWentWrongLoadingTheFile() throws HandleRoutesFileException {
		// GIVEN
		PowerMockito.mockStatic(Files.class);
		PowerMockito.mockStatic(Paths.class);
		Path path = Mockito.mock(Path.class);		
		// WHEN
		PowerMockito.when(Paths.get(anyString())).thenReturn(path);
		try {
			PowerMockito.when(Files.lines(any(Path.class))).thenThrow(new IOException("Something went wrong"));
		} catch (IOException e) {
			fail("Another problem ocurred trying to mock the file class");
		}
		RoutesFileReader.getRoutesFromFile("AnyRoute");
		// THEN
	}
	
	/**
	 * Test that return the resource path folder
	 */
	@Test
	public void testThat_returnTheResourcePath() {
		// GIVEN
		// WHEN
		final Optional<String> resourceFolderPath = RoutesFileReader.getMainFolderResourcesPath();
		// THEN
		assert resourceFolderPath.isPresent();
		assert resourceFolderPath.get().contains("resources");
	}
	
	/**
	 * Test that return the resource path folder
	 */
	@Test
	public void testThat_returnAllFilesPathsFromResourcePath() {
		// GIVEN
		final Optional<String> resourceFolderPath = RoutesFileReader.getMainFolderResourcesPath();
		// WHEN
		if(!resourceFolderPath.isPresent()) {
			fail("The resource folder doesn't exist");
		}
		
		Optional<List<Path>> resourcesFromPath = RoutesFileReader.getResourcesFromPath(resourceFolderPath.get());
		// THEN
		assert resourcesFromPath.isPresent();
		assert resourcesFromPath.get().size() > 0;
	}
	
	/**
	 * Test that return the resource path folder
	 */
	@Test
	public void testThat_getEmptyCollectionPath_whenTheFolderPathIsNull() {
		// GIVEN
		// WHEN
		Optional<List<Path>> resourcesFromPath = RoutesFileReader.getResourcesFromPath(null);
		// THEN
		assert !resourcesFromPath.isPresent();
	}
	
	/**
	 * Test that return the resource path folder
	 */
	@Test
	public void testThat_getEmptyCollectionPath_whenTheFolderPathNotExist() {
		// GIVEN
		// WHEN
		Optional<List<Path>> resourcesFromPath = RoutesFileReader.getResourcesFromPath(FAKE_LOCATION);
		// THEN
		assert !resourcesFromPath.isPresent();
	}
	
	/**
	 * Test that write an out file with final delivered points
	 * @throws HandleRoutesFileException 
	 */
	@Test
	public void testThat_writeOutFileWithFinalPoints() throws HandleRoutesFileException {
		// GIVEN
		final String droneId = "1000";
		Optional<String> resourcePath = RoutesFileReader.getMainFolderResourcesPath();
		String resourceOutPath = resourcePath.get() + "\\out\\out" + droneId + ".txt";
		final List<String> finalPoints = Arrays.asList("(-2,4) dirección Occidente","(-1,3) dirección Sur");
		// WHEN
		RoutesFileReader.writeFinalDeliveredPoints(finalPoints, droneId);
		// THEN
		assert RoutesFileReader.getRoutesFromFile(resourceOutPath).get().contains("(-2,4) dirección Occidente");
	}
}
