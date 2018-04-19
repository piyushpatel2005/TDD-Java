package com.piyushpatel2005.isbntools;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class StockManagementTests {

	@Test
	public void testCanGetACorrectLocatorCode() {
		
//		ExternalISBNDataService testWebService = new ExternalISBNDataService() {
//			
//			@Override
//			public Book lookup(String isbn) {
//				return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
//			}
//		};
//		
//		ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
//			@Override
//			public Book lookup(String isbn) {
//				return null;
//			}
//		};
		
		ExternalISBNDataService testWebService = mock(ExternalISBNDataService.class);
		when(testWebService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));
		
		ExternalISBNDataService testDatabaseService = mock(ExternalISBNDataService.class);
		when(testDatabaseService.lookup(anyString())).thenReturn(null);

		StockManager stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDatabaseService(testDatabaseService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

	
	@Test
	public void databaseIsUsedIfDataIsPresent() {
		// Creates a dummy class 
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "ABC", "ABC"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		verify(databaseService).lookup("0140177396");
		verify(webService, never()).lookup(anyString());
	}
	
	@Test
	public void webServiceIsUsedIfDataIsNotPresentInDatabase () {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "ABC", "ABC"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		verify(databaseService, times(1)).lookup("0140177396");
		verify(webService, times(1)).lookup("0140177396");
	}

}
