package com.eAge.humanbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.eAge.humanbot.model.ClientQuery;
import com.eAge.humanbot.service.HumanBotService;
import com.eAge.humanbot.service.JWTTokenService;

@RestController
public class HumanBotController {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";

	@Autowired
	private HumanBotService botService;
	
	@Autowired
	private JWTTokenService jwtTokenService;

	@GetMapping("/")
	public ResponseEntity<String> handleQuery(@RequestParam String query) {
		HttpHeaders responseHeaders = new HttpHeaders();
	
		String handleQueryResponse = null;
		try {
			ClientQuery clientQuery = botService.handleQuery(query);
			String jwtToken = jwtTokenService.generateToken(clientQuery.getQuery());
			responseHeaders.set(AUTHORIZATION, BEARER+jwtToken);
			handleQueryResponse = clientQuery.toString();
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(),  HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(handleQueryResponse,responseHeaders, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<String> answerToQuery(@RequestHeader(AUTHORIZATION) String authorization,  @RequestBody String queryResponse) {
		String handleQueryResponse = null;
		try {
			if (authorization == null || !authorization.startsWith(BEARER)) {
				throw new Exception("JWT token missing!");
			}
			String jwtToken = authorization.substring(BEARER.length());
			jwtTokenService.verify(jwtToken,queryResponse);
			handleQueryResponse = botService.answerToQuery(queryResponse);
		}catch(JWTVerificationException jve) {
			return new ResponseEntity<String>("Invalid JWT Authorization header or Token expired!",  HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(),  HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(handleQueryResponse,  HttpStatus.OK);
	}
}
