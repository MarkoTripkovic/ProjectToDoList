package org.jhipster.todo.security.jwt;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String jwt = httpServletRequest.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
            System.out.println("token"+jwt);
            if(jwt!=null){
            	jwt = resolveToken(jwt);
            }
            
            if (StringUtils.hasText(jwt)) {
            	System.out.println(this.tokenProvider.validateToken(jwt));
                if (this.tokenProvider.validateToken(jwt)) {
                	System.out.println("valid token");
                    Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String resolveToken(String token){
    	System.out.println("reslove token");
        String bearerToken = token;
        System.out.println("bearer "+bearerToken);
        System.out.println(StringUtils.hasText(bearerToken));
        //bearerToken = "Bearer "+bearerToken;
        System.out.println(bearerToken.startsWith("Bearer "));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return bearerToken;
    }
}
