package com.eAge.humanbot.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class Test {

	public static void main(String[] args) {
		
		Date date = new Date();
		System.out.println(">>>>>date="+date);
		date.setMinutes(date.getMinutes()+5);
		System.out.println(">>>>>date="+date);
		String tokenToverify = null;
		 Algorithm algorithm = null;
		try {
		    algorithm = Algorithm.HMAC256("ysdfrek90jsdfsfs");
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .sign(algorithm);
		    
		    System.out.println("JWT token is :"+token);
		    tokenToverify = token;
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
		try {
		   // Algorithm algorithm = Algorithm.HMAC256("secret");
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("auth0")
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(tokenToverify);
		    System.out.println(">>>>decoded JWT="+jwt.getPayload());
		} catch (JWTVerificationException exception){
		    //Invalid signature/claims
			exception.printStackTrace();
		}
	}
}
