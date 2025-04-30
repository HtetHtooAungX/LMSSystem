package com.hha.demo.jwt;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hha.demo.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	
	@Value("${app.jwt.expire.in.minute}")
	private int expireTime;

	public boolean validateToken(String token) {
		try {
			boolean validate = Jwts.parser()
					.verifyWith(secretKey())
					.build()
					.isSigned(token);
			return validate;
			
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		}
		
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(secretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims.getSubject();
	}

	public String generate(Authentication authenticated) {
		String username = authenticated.getName();
		
		return Jwts.builder().subject(username)
				.signWith(secretKey())
				.expiration(getExpiration())
				.compact();
	}

	private Date getExpiration() {
		Date currentTime = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.MINUTE, expireTime);
		return calendar.getTime();
	}

	private SecretKey secretKey() {
		return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
	}
}
