package com.co.s4n.SuCorrientazoDomiclio.util;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	/**
	 * Test that get the path of the routes files
	 */
	@Test
	public void testThat_getResourcePathEmptyValue_whenFileNameNotExists() {
		// GIVEN
		final String nameRoutesFile = "fake01.txt";
		// WHEN
		Optional<String> resourcePath = RoutesFileReader.getResourcePath(nameRoutesFile);
		// THEN
		assert !resourcePath.isPresent();
	}

	/**
	 * Test that get the path of the routes files
	 */
	@Test
	public void testThat_getResourcePath_whenFileNameExists() {
		// GIVEN
		final String nameRoutesFile = "in01.txt";
		// WHEN
		Optional<String> resourcePath = RoutesFileReader.getResourcePath(nameRoutesFile);
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
		String fakePath = "/fakeLocation.txt";
		// WHEN
		RoutesFileReader.getRoutesFromFile(fakePath);
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
		String fakeLocation = null;
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
		Optional<String> path = RoutesFileReader.getResourcePath("empty.txt");
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
		Optional<String> path = RoutesFileReader.getResourcePath("in01.txt");
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
}
