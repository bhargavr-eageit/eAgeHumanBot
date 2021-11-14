package com.eAge.humanbot.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eAge.humanbot.model.ClientQuery;
import com.eAge.humanbot.service.HumanBotService;
import com.eAge.humanbot.service.JWTTokenService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class HumanBotControllerTest {

	@MockBean
	private HumanBotService botService;

	@MockBean
	private JWTTokenService jwtTokenService;

	@Autowired
	HumanBotController humanBotController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenHumanBotControllerInjected_thenNotNull() throws Exception {
		Assertions.assertNotNull(humanBotController);
	}

	@Test
	public void whenGetRequestToQueryWithOutQueryParam_thenErrorResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void whenGetRequestToQueryWithQueryParam_thenOkResponse() throws Exception {
		ClientQuery clientQuery = new ClientQuery("Here you go, solve the question: ", "\"Please sum the numbers 0,13,10\".");
		Mockito.when(botService.handleQuery(Mockito.anyString())).thenReturn(clientQuery);
		mockMvc.perform(MockMvcRequestBuilders.get("/")
				.queryParam("query", "Hey Service, can you provide me a question with numbers to add?"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenPostRequestToQueryWithOutBody_thenErrorResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	public void whenPostRequestToQueryIsValid_thenCorrectResponse() throws Exception {
		String query = "Sorry, the original question was \"Please sum the numbers 0,13,10\". and the answer is 26.";
		mockMvc.perform(MockMvcRequestBuilders.post("/").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcIlBsZWFzZSBzdW0gdGhlIG51bWJlcnMgMCwxMywxMFwiIiwiaXNzIjoiand0Lmlzc3VlciIsImV4cCI6MTYzNjgxNDc0NH0.60tzmPLKamaVQcn3BIrRQcFyUTOjEYwqTuBwR7AdgRs")
				.content(query))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void whenPostRequestToQueryWithInvalidHeader_thenErrorResponse() throws Exception {
		String query = "Sorry, the original question was \"Please sum the numbers 0,13,10\". and the answer is 26.";
		mockMvc.perform(MockMvcRequestBuilders.post("/").header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcIlBsZWFzZSBzdW0gdGhlIG51bWJlcnMgMCwxMywxMFwiIiwiaXNzIjoiand0Lmlzc3VlciIsImV4cCI6MTYzNjgxNDc0NH0.60tzmPLKamaVQcn3BIrRQcFyUTOjEYwqTuBwR7AdgRs")
				.content(query))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}
}
