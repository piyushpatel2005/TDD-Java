package com.piyushpatel2005.isbntools;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateISBNTest {

	@Test
	public void checkAValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0140449116");
		assertTrue("First Value", result);
		result = validator.checkISBN("0140177396");
		assertTrue("Second Value", result);
	}
	
	@Test
	public void checkAValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9780771038518");
		assertTrue("First Value", result);
		result = validator.checkISBN("9781853267338");
		assertTrue("First Value", result);
	}
	
	@Test
	public void TenDigitISBNNumbersEndingInAnXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("012000030X");
		assertTrue(result);
	}
	
	@Test
	public void checkAnInvalid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0140449117");
		assertFalse(result);
	}
	
	@Test
	public void checkAnInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781853267336");
		assertFalse(result);
	}
	
	@Test(expected = NumberFormatException.class)
	public void nineDigitISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		validator.checkISBN("123456789");
	}
	
	@Test(expected=NumberFormatException.class)
	public void nonNumericIsbnsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		validator.checkISBN("helloworld");
	}

}
