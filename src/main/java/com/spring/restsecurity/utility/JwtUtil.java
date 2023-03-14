package com.spring.restsecurity.utility;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${app.secret}")
	private String secret;

	// TODO: 1. Generate Token

	public String generateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuer("Sushant").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	// TODO: 2. Read Claims
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	// TODO: 3. Read Exp Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}

	// TODO: 4. Read Subject/Username
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}

	// TODO: 5. Validate Expiration Date

	public boolean isTokenExpired(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));

	}

	// TODO: 6. Validate Username in token and database and expiration date

	public boolean validateToken(String token, String username) {
		String userNm = getUserName(token);
		return username.equals(userNm) && !isTokenExpired(token);
	}

}
