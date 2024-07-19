package com.social.Hizam.config;

import com.social.Hizam.service.JwtService;
import com.social.Hizam.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization ";

    private final JwtService jwtService;

    private final UserService userService;



    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader,BEARER_PREFIX))
        {
            filterChain.doFilter(request,response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var userName = jwtService.extractUserName(jwt);

        if(StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(userName);

            if(jwtService.isTokenValid(jwt,userDetails))
            {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);


            }
        }

        filterChain.doFilter(request,response);

    }
}
