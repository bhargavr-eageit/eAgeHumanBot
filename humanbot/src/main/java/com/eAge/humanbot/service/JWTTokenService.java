package com.eAge.humanbot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.eAge.humanbot.config.JWTConfig;

@Service
public class JWTTokenService {

	@Autowired
	private Algorithm algorithm;

	@Autowired
	private JWTVerifier jwtVerifier;

	@Autowired
	private JWTConfig config;


	public String generateToken(String query) {
		try {
			Date date = new Date();
			date.setMinutes(date.getMinutes()+5);
			String token = JWT.create()
					.withIssuer(config.getIssuer())
					.withSubject(query)
					.withExpiresAt(date)
					.sign(algorithm);

			System.out.println("JWT token is :"+token);
			return token;
		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
		}
		return "";
	}

	public DecodedJWT verify(String token, String queryResponse) throws Exception {
		DecodedJWT jwt = jwtVerifier.verify(token);
		Date currentDate = new Date();
		if(jwt.getExpiresAt().before(currentDate)) {
			throw new Exception("Token expired!");
		}
		String subject = jwt.getSubject();
		if(queryResponse.indexOf(subject) <0) {
			throw new Exception("Invalid query!. Query doesn't match the earlier query request!");
		}
		return jwt;
	}
}
