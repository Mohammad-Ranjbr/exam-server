package com.exam.examserver.security;

import com.exam.examserver.config.ApplicationConstans;
import com.exam.examserver.entities.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    public JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService,JwtTokenHelper jwtTokenHelper){
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestToken = request.getHeader(ApplicationConstans.AUTHORIZATION_HEADER);

        String username = null;
        String token = null;

        if(requestToken != null && requestToken.startsWith(ApplicationConstans.JWT_TOKEN_STARTS_WITH)){
            token = requestToken.substring(10);
            try{
                username = jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException illegalArgumentException){
                System.out.println(ApplicationConstans.JWT_TOKEN_ILLEGAL_ARGUMENT_EXCEPTION);
            }
            catch (ExpiredJwtException expiredJwtException){
                System.out.println(ApplicationConstans.JWT_TOKEN_EXPIRED_JWT_EXCEPTION);
            }
            catch (MalformedJwtException malformedJwtException){ // if jwt token header is not correct
                System.out.println(ApplicationConstans.JWT_TOKEN_MALFORMED_JWT_EXCEPTION);
            }
            catch (SignatureException signatureException){ // if payload and signature is not correct
                System.out.println(ApplicationConstans.JWT_TOKEN_SIGNATURE_EXCEPTION);
            }
        } else {
            System.out.println(ApplicationConstans.NULL_JWT_TOKEN);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = (User) customUserDetailsService.loadUserByUsername(username);
            if(jwtTokenHelper.validateToken(token)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println(ApplicationConstans.JWT_TOKEN_FAILED_VALIDATE);
            }
        } else {
            System.out.println(ApplicationConstans.JWT_TOKEN_NULL_USERNAME_OR_CONTEXT);
        }

        filterChain.doFilter(request,response);

    }

}
