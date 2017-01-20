package org.jhipster.todo.security.jwt;

import org.jhipster.todo.config.JHipsterProperties;
import org.jhipster.todo.domain.Korisnik;
import org.jhipster.todo.repository.KorisnikRepository;
import org.jhipster.todo.repository.RolaRepository;
import org.jhipster.todo.service.CustomUserDetails;
import org.jhipster.todo.service.KorisnikDetailsService;
import org.jhipster.todo.service.KorisnikService;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.*;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private static final String ID_KEY = "id";

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    @Inject
    private JHipsterProperties jHipsterProperties;
    @Inject
    private KorisnikRepository korisnikRepository;
    /*@Inject
    private RolaRepository rolaRepository;*/
    @PostConstruct
    public void init() {
        this.secretKey =
            jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();

        this.tokenValidityInMilliseconds =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication) throws UnsupportedEncodingException {
    	
    	Korisnik korisnik = korisnikRepository.findByUsername(authentication.getName());
    	System.out.println(korisnik.getPassword());
    	Long id = korisnik.getRola().getId();
    	System.out.println(id);
    	System.out.println(korisnik.getRola().getName());
    	GrantedAuthority auth = new SimpleGrantedAuthority(korisnik.getRola().getName());
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY,auth)
            .claim(ID_KEY, korisnik.getId())
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))            
            .compact();
    }

    public Authentication getAuthentication(String token) throws ClaimJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
    	System.out.println("get autenticication");
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey.getBytes("UTF-8"))
            .parseClaimsJws(token)
            .getBody();
        System.out.println("claims");

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        System.out.println(principal);

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
        	System.out.println("validate token");
        	System.out.println("secretKey "+secretKey);
        	System.out.println("token "+ authToken);
            Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(authToken);
        	System.out.println("validate token");

            return true;
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | UnsupportedEncodingException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
        	System.out.println("validate token ex");

            return false;
        }
    }
    public String getUsernameFromToken(String token){
    	
    	if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            String jwt = token.substring(7, token.length());
            try {
				Claims claims = Jwts.parser()
				        .setSigningKey(secretKey.getBytes("UTF-8"))
				        .parseClaimsJws(jwt)
				        .getBody();
				return claims.getSubject();
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    	return null;
    }
public Integer getUserIdFromToken(String token){
    	
    	if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            String jwt = token.substring(7, token.length());
            try {
				Claims claims = Jwts.parser()
				        .setSigningKey(secretKey.getBytes("UTF-8"))
				        .parseClaimsJws(jwt)
				        .getBody();
						System.out.println(claims.get(ID_KEY));
				return (Integer) claims.get(ID_KEY);
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    	return null;
    }
}
