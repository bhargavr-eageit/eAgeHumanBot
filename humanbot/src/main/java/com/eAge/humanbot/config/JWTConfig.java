package com.eAge.humanbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

@Configuration
public class JWTConfig {

	@Value("jwt.secret")
	private String secret;

	@Value("jwt.issuer")
	private String issuer;

	@Bean
	Algorithm getHMACAlgorothm() {
		return Algorithm.HMAC256(secret);
	}

	@Bean
	JWTVerifier getJWTVerifier(Algorithm algorithm) {
		return JWT.require(algorithm)
				.withIssuer(issuer)
				.build();
	}

	public String getIssuer() {
		return this.issuer;
	}
}
