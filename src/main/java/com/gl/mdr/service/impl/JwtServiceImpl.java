package com.gl.mdr.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.generic.GenericErrorResponse;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl {
	private static final Logger logger = LogManager.getLogger(JwtServiceImpl.class);
	
	@Autowired
	SysParamServiceImpl configurationService;
	
	public String extractUsername(String token) {
		logger.info("Token to parse:"+token);
		String userName = extractClaim(token, Claims::getSubject);
		logger.info("Extracted user name:"+userName);
		return userName;
	}

	  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    final Claims claims = extractAllClaims(token);
	    if(claims != null)
	    	return claimsResolver.apply(claims);
	    else
	    	return null;
	  }

	  public String generateToken(
	      UserDetails userDetails
	  ) {
		  List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) userDetails.getAuthorities();
		  Date currentTime = new Date(System.currentTimeMillis());
	    return Jwts
	        .builder()
	        .setId(userDetails.getUsername()+currentTime)
	        .setSubject(userDetails.getUsername())
	        .claim("authorities",
	        		grantedAuthorities.stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList()))
	        .setIssuedAt(currentTime)
	        .setExpiration(new Date(System.currentTimeMillis() 
	        		+ Integer.parseInt(configurationService.getConfigurationByTag("BEARER_TOKEN_EXPIRY_PERIOD").getValue())))
	        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
	        .compact();
	  }

	  public boolean isTokenValid(String token, UserDetails userDetails) {
		  final Claims claims = extractAllClaims(token);
		  final String username = claims.getSubject();
		  return (username.equals(userDetails.getUsername())) && !isTokenExpired(claims);
	  }

	  private boolean isTokenExpired(Claims claims) {
		  Date expirationDate = claims.getExpiration();
		  Date currentDate = new Date(System.currentTimeMillis());
		  boolean result = expirationDate.before(currentDate);
		  logger.info("Expiry Date:["+expirationDate+"], Current Date:["+currentDate+"] and is expired: "+result);
		  return result;
	  }

	  private Claims extractAllClaims(String token) {
		  try {
			  return Jwts
					  .parserBuilder()
					  .setSigningKey(getSignInKey())
					  .build()
					  .parseClaimsJws(token)
					  .getBody();
		  }catch(ExpiredJwtException ex) {
			  logger.error("Token is already expired.");
			  return null;
		  }
	  }

	  private Key getSignInKey() {
		  byte[] keyBytes = Decoders.BASE64.decode(configurationService.getConfigurationByTag("BEARER_TOKEN_KEY").getValue());
		  return Keys.hmacShaKeyFor(keyBytes);
	  }
	  
}
