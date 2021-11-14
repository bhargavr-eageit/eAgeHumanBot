package com.eAge.humanbot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.eAge.humanbot.exceptions.InvalidJWTException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JWTTokenServiceTest {

	@Autowired
	private JWTTokenService jwtTokenService;
	
	@Test
	public void generateTokenReturnsToken() throws Exception {
		String generatedToken = jwtTokenService.generateToken("Please sum the numbers 0,13,10");
		Assertions.assertNotNull(generatedToken);
	}
	
	@Test
	public void verifyTokenReturnsJWTObj() throws Exception {
		String generatedToken = jwtTokenService.generateToken("Please sum the numbers 0,13,10");
		DecodedJWT jwt = jwtTokenService.verify(generatedToken,"Please sum the numbers 0,13,10");
		Assertions.assertNotNull(jwt);
	}
	
	@Test
	public void verifyTokenThrowsInvalidJWTException() throws Exception {
		
		Assertions.assertThrows(InvalidJWTException.class, ()->{
			String generatedToken = jwtTokenService.generateToken("Please sum the numbers 0,13,10");
			jwtTokenService.verify(generatedToken,"Please sum the numbers 0,13");
		});
	}
}
