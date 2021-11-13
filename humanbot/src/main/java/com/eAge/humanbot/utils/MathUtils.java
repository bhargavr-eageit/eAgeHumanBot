package com.eAge.humanbot.utils;

import java.util.List;

public class MathUtils {

	
	public static void verifySum(List<Integer> numbers, int expectedSum) throws Exception {
		Integer sum = numbers.stream().reduce(0,(a,b)-> a+b);
		if(sum.intValue() != expectedSum) {
			throw new Exception("That’s wrong. Please try again.");
		}
	}
}
