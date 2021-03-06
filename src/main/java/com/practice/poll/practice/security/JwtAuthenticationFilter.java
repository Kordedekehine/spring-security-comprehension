package com.practice.poll.practice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
 //   get the JWT token from the request
    //   validate it
    //   load  user associated with the token,
    //   and pass it to Spring Security

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
       try{
           String jwt = getJwtFromRequest(request);

           if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
               Long UserId = jwtTokenProvider.getUserIdFromJwt(jwt);

               UserDetails userDetails = customUserDetailsService.loadUserById(UserId.longValue());//Todo
               UsernamePasswordAuthenticationToken authenticationToken = new
                       UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }
       }catch (Exception e){
           logger.error("AUTHENTICATION COULD NOT BE SET USER IN SECURITY CONTEXT",e);
       }
       filterChain.doFilter(request,response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(" Bearer ")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }

//    We use JWTAuthenticationFilter to implement a filter that -
//
//    reads JWT authentication token from the Authorization header of all the requests
//    validates the token
//    loads the user details associated with that token.
//    Sets the user details in Spring Security???s SecurityContext. Spring Security uses
//    the user details to perform authorization checks. We can
//    also access the user details stored in the SecurityContext in our controllers to perform our business logic.
}
