package com.eAge.humanbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eAge.humanbot.exceptions.InvalidQueryAnswerException;
import com.eAge.humanbot.exceptions.InvalidQueryException;
import com.eAge.humanbot.exceptions.InvalidQueryResponseException;
import com.eAge.humanbot.model.ClientQuery;

@ExtendWith(MockitoExtension.class)
public class HumanBotServiceTest {
	@Mock
	private JWTTokenService jwtService;
	
	@InjectMocks
	private HumanBotService botService;
	
	@Test
	public void handleQueryThrowsExceptionWhenQueryisInvalid() {
		Assertions.assertThrows(InvalidQueryException.class, ()->{
			botService.handleQuery("Hey Service, can you provide me a question");
		});
	}
	
	@Test
	public void handleQueryReturnsMathQuery() {
		try {
			ClientQuery clientQuery = botService.handleQuery("Hey Service, can you provide me a question with numbers to add?");
		   Assertions.assertNotNull(clientQuery);
		   Assertions.assertEquals("Here you go, solve the question: ", clientQuery.getQueryPreText());
		   Assertions.assertTrue(clientQuery.getQuery().indexOf("Please sum the numbers")>0);
		} catch (Exception e) {
			Assertions.fail("Exception shouldn't be thrown");
		}
	}
	
	@Test
	public void answerToQueryThrowsInvalidQueryException() {
		Assertions.assertThrows(InvalidQueryException.class, ()->{
			botService.answerToQuery("the original question was Please sum the numbers 0,13,10. and the answer is 23.");
		});
	}
	
	@Test
	public void answerToQueryThrowsInvalidQueryExceptionWhenAnswerTextMissing() {
		Assertions.assertThrows(InvalidQueryException.class, ()->{
			botService.answerToQuery("the original question was \\\"Please sum the numbers 0,13,10\\\". and the is 23a.");
		});
	}
	
	@Test
	public void answerToQueryThrowsInvalidQueryResponseException() {
		Assertions.assertThrows(InvalidQueryResponseException.class, ()->{
			botService.answerToQuery("the original question was \"Please sum the numbers 0,13,10\". and the answer is 23a.");
		});
	}
	
	@Test
	public void answerToQueryReturnsSuccessResponse() {
		try {
			String response = botService.answerToQuery("the original question was \"Please sum the numbers 0,13,10\". and the answer is 23.");
			assertEquals("That’s great.", response);
		} catch (Exception e) {
			Assertions.fail("Exception shouldn't be thrown");
		}
	}
	
	@Test
	public void answerToQueryReturnsFailedResponse() {
		try {
			 botService.answerToQuery("the original question was \"Please sum the numbers 0,13,10\". and the answer is 26.");
		} catch (InvalidQueryAnswerException e) {
			assertEquals("That’s wrong. Please try again.", e.getMessage());
		}catch (Exception e) {
			Assertions.fail("Exception shouldn't be thrown");
		}
	}
}
