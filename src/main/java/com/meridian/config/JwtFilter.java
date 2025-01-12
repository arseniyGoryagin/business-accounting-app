package com.meridian.config;


import com.meridian.jwt.JwtService;
import com.meridian.jwt.TokenType;
import com.meridian.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.meridian.util.RequestParser.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;



    private void authUser(String token){

        String username = jwtService.getUsername(token, TokenType.ACCESS);

        if(!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() ==  null){

            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            // TODO add valid by email
            if(jwtService.validateToken(token, TokenType.ACCESS)){

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);

                return;

            }
        }


    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER);

        if(StringUtils.isEmpty(authHeader) || !authHeader.startsWith(BEARER_PREFIX)){

            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromHeader(authHeader);
        authUser(token);
        filterChain.doFilter(request, response);

    }




}
