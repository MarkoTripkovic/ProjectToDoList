package org.jhipster.todo.security.jwt;

import org.jhipster.todo.config.JHipsterProperties;
import org.jhipster.todo.service.CustomUserDetails;
import org.jhipster.todo.service.KorisnikDetailsService;

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

    @PostConstruct
    public void init() {
        this.secretKey =
            jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();

        this.tokenValidityInMilliseconds =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication) {
    	
        
        	
        

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, "")
            .signWith(SignatureAlgorithm.HS256, secretKey)            
            .compact();
    }

    public Authentication getAuthentication(String token) {
    	System.out.println("get autenticication");
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
        	System.out.println("validate token");
        	System.out.println("secretKey "+secretKey);
        	System.out.println("token "+ authToken);
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        	System.out.println("validate token");

            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
        	System.out.println("validate token ex");

            return false;
        }
    }
}
