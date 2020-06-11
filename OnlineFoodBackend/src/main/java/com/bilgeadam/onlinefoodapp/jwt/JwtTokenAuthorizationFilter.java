package com.bilgeadam.onlinefoodapp.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtUserDetailsAdminService jwtUserDetailsAdminService;
    private final JwtUserDetailsCustomerService jwtUserDetailsCustomerService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenAuthorizationFilter(JwtUserDetailsAdminService jwtUserDetailsAdminService, JwtUserDetailsCustomerService jwtUserDetailsCustomerService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsAdminService = jwtUserDetailsAdminService;
        this.jwtUserDetailsCustomerService = jwtUserDetailsCustomerService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("Authentication Request For '{}'", request.getRequestURL());

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("JWT_TOKEN_UNABLE_TO_GET_USERNAME", e);
            } catch (ExpiredJwtException e) {
                logger.warn("JWT_TOKEN_EXPIRED", e);
            }
        } else {
            logger.warn("JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING");
        }

        logger.debug("JWT_TOKEN_USERNAME_VALUE '{}'", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String userType = jwtTokenUtil.getClaimFromToken(jwtToken, claims -> (String) claims.get("userType"));
            UserDetails userDetails = null;
            if ("admin".equals(userType))
                userDetails = this.jwtUserDetailsAdminService.loadUserByUsername(username);
            else if ("customer".equals(userType))
                userDetails = this.jwtUserDetailsCustomerService.loadUserByUsername(username);

            if (userDetails != null && jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}