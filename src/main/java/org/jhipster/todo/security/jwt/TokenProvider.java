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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

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
}
