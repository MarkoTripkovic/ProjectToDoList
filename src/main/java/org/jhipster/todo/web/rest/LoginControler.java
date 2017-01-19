package org.jhipster.todo.web.rest;

import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jhipster.todo.security.jwt.JWTConfigurer;
import org.jhipster.todo.security.jwt.TokenProvider;
import org.jhipster.todo.service.KorisnikCredentionals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
@RestController
@RequestMapping("/api")

public class LoginControler {
	

	    @Inject
	    private TokenProvider tokenProvider;

	    @Inject
	    private AuthenticationManager authenticationManager;

	    @PostMapping("/login")
	    @Timed
	    public ResponseEntity<?> authorize(@Valid @RequestBody KorisnikCredentionals credentionals, HttpServletResponse response) {

	        UsernamePasswordAuthenticationToken authenticationToken =
	            new UsernamePasswordAuthenticationToken(credentionals.getUsername(), credentionals.getPassword());

	        try {
	        	
	            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            String jwt = tokenProvider.createToken(authentication);
	            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
	            return ResponseEntity.ok(new JWTToken("Bearer "+jwt));
	        } catch (AuthenticationException exception) {
	            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
	        }
	    }

}
