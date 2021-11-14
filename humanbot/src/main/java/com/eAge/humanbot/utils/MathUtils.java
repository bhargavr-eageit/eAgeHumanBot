package com.eAge.humanbot.utils;

import java.util.List;

import com.eAge.humanbot.exceptions.InvalidQueryAnswerException;

public class MathUtils {


	private static final String THAT_S_WRONG_PLEASE_TRY_AGAIN = "That’s wrong. Please try again.";

	public static void verifySum(List<Integer> numbers, int expectedSum) throws Exception {
		Integer sum = numbers.stream().reduce(0,(a,b)-> a+b);
		if(sum.intValue() != expectedSum) {
			throw new InvalidQueryAnswerException(THAT_S_WRONG_PLEASE_TRY_AGAIN);
		}
	}
}
