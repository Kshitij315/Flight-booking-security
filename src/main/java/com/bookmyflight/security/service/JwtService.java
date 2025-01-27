package com.bookmyflight.security.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bookmyflight.security.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {  

	@Value("${jwt.secret}")
	private String secretkey;
	
	
	
//	public JwtService() {
//		try {
//			KeyGenerator keyGen= KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk= keyGen.generateKey();
//			secretkey=Base64.getEncoder().encodeToString(sk.getEncoded());
//			
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			throw new RuntimeException(e);
//		}
//	}

	public  String generateToken(String username,Role role) {
		
		Map<String,Object> claims = new HashMap<>();
		
// 1 day
		
		claims.put("role", role );
		System.out.println("generateToken executed");
		return Jwts.builder()
				.claims(claims)
				
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))	
				.signWith(getKey())
				.compact();
		
	}

	private  SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}



	public boolean validateToken(String token, UserDetails userDetails) {
		  final String userName = extractUserName(token);
	        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	  public String extractUserName(String token) {
	        // extract the username from jwt token
	        return extractClaim(token, Claims::getSubject);
	    }

	    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	        		.verifyWith(getKey())
	        		.build()
	        		.parseSignedClaims(token)
	        		.getPayload();
                    
	    }
	    private boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    private Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	    
	    


	
}
