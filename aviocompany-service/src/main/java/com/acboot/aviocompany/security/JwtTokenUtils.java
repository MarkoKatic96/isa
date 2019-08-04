package com.acboot.aviocompany.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.service.KorisnikService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


//@Component
public class JwtTokenUtils
{
//	@Value("${security.jwt.token.secret-key:secret-key}")
//	private String secretKey;
//	
//	@Value("${security.jwt.token.expire-length:86400000}")
//	private long validityInMilliseconds = 86400000;
//	
//	@Autowired
//	private com.acboot.aviocompany.security.UserDetails mineUserDetails;
//
//	@Autowired
//	private KorisnikService korisnikService;
//	
//	@PostConstruct
//	protected void init() {
//		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//	}
//	
//	
//	public String createToken(String email)
//	{
//		List<SimpleGrantedAuthority> tipoviKorisnika = new ArrayList<>();
//		Korisnik korisnik = korisnikService.getUserByEmail(email);
//		tipoviKorisnika.add(new SimpleGrantedAuthority(korisnik.getRola().toString()));
//		
//		Claims claims = Jwts.claims().setSubject(email);
//		claims.put("auth", tipoviKorisnika);
//		
//		Date now = new Date();
//		Date validity = new Date(now.getTime() + validityInMilliseconds);
//		
//		return Jwts.builder().setClaims(claims)
//				.setIssuedAt(now)
//				.setExpiration(validity)
//				.signWith(SignatureAlgorithm.HS256, secretKey)
//				.compact();
//		
//	}
//	
//	public Authentication getAuthentication(String token) {
//		UserDetails userDetails = mineUserDetails.loadUserByUsername(getUsername(token));
//		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//	}
//
//	public String getUsername(String token) {
//		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//	}
//
//	public String resolveToken(HttpServletRequest req) {
//		String bearerToken = req.getHeader("Authorization");
//		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7, bearerToken.length());
//		}
//		return null;
//	}
//
//	public boolean validateToken(String token) {
//		try {
//			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//			return true;
//		} catch (JwtException | IllegalArgumentException e) {
//			throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
}
