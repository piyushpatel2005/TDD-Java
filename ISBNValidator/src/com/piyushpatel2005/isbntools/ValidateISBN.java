package com.piyushpatel2005.isbntools;

public class ValidateISBN {

	public boolean checkISBN(String isbn) {
		int total = 0;
		if (isbn.length() != 10)
			throw new NumberFormatException("Nine digit isbn not allowed.");
		
		for (int i = 0; i < 10; i++) {
			if(!Character.isDigit(isbn.charAt(i)))
				throw new NumberFormatException("ISBN Numbers can only contain numeric digits");
			total += isbn.charAt(i) * (10-i);
		}
		if(total % 11 == 0) 
			return true;
		else
			return false;
	}

}
