package com.eAge.humanbot.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.eAge.humanbot.model.ClientQuery;
import com.eAge.humanbot.model.QueryModel;
import com.eAge.humanbot.utils.MathUtils;

@Service
public class HumanBotService {

	private static final String THAT_S_WRONG_PLEASE_TRY_AGAIN = "That’s wrong. Please try again.";
	private static final String THAT_S_GREAT = "That’s great.";
	private static final String PLEASE_SUM_THE_NUMBERS = "Please sum the numbers";
	private static final String ANSWER_IS = "answer is";
	Random random = new Random(10);

	public ClientQuery handleQuery(String clientQuestion) throws Exception {
		validateClientQuery(clientQuestion);
		int nextInt = random.nextInt(6);
		if(nextInt <=1) nextInt = 2;
		IntStream ints = random.ints(nextInt,0,20);
		String numbers = ints.boxed().map(String::valueOf).collect(Collectors.joining(","));
		String query = String.format("\"Please sum the numbers %s\"", numbers);
		return new ClientQuery("Here you go, solve the question: ", query);
	}

	private void validateClientQuery(String clientQuestion) throws Exception {
		if(clientQuestion.indexOf("question with numbers to add")<0) {
			throw new Exception("Invalid Query!");
		}
	}

	private String extractQuery(String queryResponse) throws Exception {
		if(queryResponse.indexOf("\"")<0) {
			throw new Exception("Invalid Response");
		}
		String question = queryResponse.substring(queryResponse.indexOf("\"")+1);
		question = question.substring(0,question.indexOf("\""));
		return question.trim();
	}

	private void validateQuestion(String question) {
		// TODO Auto-generated method stub

	}

	public String answerToQuery(String queryResponse) throws Exception {
		QueryModel queryModel = mapToQueryModel(queryResponse);
		return verifyQueryAnswer(queryModel);
	}

	private String verifyQueryAnswer(QueryModel queryModel) throws Exception {
		String query = queryModel.getQuery();
		String numbers = query.substring(query.indexOf(PLEASE_SUM_THE_NUMBERS)+PLEASE_SUM_THE_NUMBERS.length(), query.length());
		List<Integer> numberList = Stream.of(numbers.split(","))
				.map(String::trim)
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		MathUtils.verifySum(numberList,queryModel.getAnswer());
		return THAT_S_GREAT;
	}

	private QueryModel mapToQueryModel(String queryResponse) throws Exception {
		String question = extractQuery(queryResponse);
		validateQuestion(question);
		String answer = extractAnswer(queryResponse);
		try {
			Integer.valueOf(answer);
		} catch (NumberFormatException nfe) {
			throw new Exception(THAT_S_WRONG_PLEASE_TRY_AGAIN);
		}
		return new QueryModel(question, Integer.valueOf(answer));
	}

	private String extractAnswer(String queryResponse) throws Exception {
		if(queryResponse.indexOf(ANSWER_IS)<0) {
			throw new Exception("Invalid Query Response!");
		}

		String response = queryResponse.substring(queryResponse.indexOf(ANSWER_IS)+ANSWER_IS.length());
		if(response.indexOf(".")>0) {
			response = response.substring(0, response.indexOf(".")).trim();
		}else {
			response = response.substring(0, response.length()).trim();
		}
		return response;
	}
}
