package com.piyushpatel2005.isbntools;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class StockManagementTests {

	ExternalISBNDataService testWebService;
	StockManager stockManager;
	ExternalISBNDataService testDatabaseService;

	@Before
	public void setup() {
		testWebService = mock(ExternalISBNDataService.class);
		stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		testDatabaseService = mock(ExternalISBNDataService.class);
		stockManager.setDatabaseService(testDatabaseService);
	}

	@Test
	public void testCanGetACorrectLocatorCode() {

		// ExternalISBNDataService testWebService = new
		// ExternalISBNDataService() {
		//
		// @Override
		// public Book lookup(String isbn) {
		// return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
		// }
		// };
		//
		// ExternalISBNDataService testDatabaseService = new
		// ExternalISBNDataService() {
		// @Override
		// public Book lookup(String isbn) {
		// return null;
		// }
		// };

		when(testWebService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));

		when(testDatabaseService.lookup(anyString())).thenReturn(null);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

	@Test
	public void databaseIsUsedIfDataIsPresent() {
		// Creates a dummy class

		when(testDatabaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "ABC", "ABC"));

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		verify(testDatabaseService).lookup("0140177396");
		verify(testWebService, never()).lookup(anyString());
	}

	@Test
	public void webServiceIsUsedIfDataIsNotPresentInDatabase() {

		when(testDatabaseService.lookup("0140177396")).thenReturn(null);
		when(testWebService.lookup("0140177396")).thenReturn(new Book("0140177396", "ABC", "ABC"));

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		verify(testDatabaseService, times(1)).lookup("0140177396");
		verify(testWebService, times(1)).lookup("0140177396");
	}

}
