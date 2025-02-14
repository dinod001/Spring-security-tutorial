package com.example.security.filter;

import com.example.security.Service.JwtService;
import com.example.security.entity.UserEntity;
import com.example.security.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //getting token
        String token = authHeader.substring(7);
        //getting username
        String username=jwtService.getUsername(token);
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //if username not null, we'll check that username in the database
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //if user exist in database , that mean user if authenticated , we can pass it to the spring context holder
        if (SecurityContextHolder.getContext().getAuthentication()!=null){
            filterChain.doFilter(request, response);
            return;
        }

        //if context holder is null, that mean there is no authenticated users currently there, so we have to add this authenticated user now
        UserDetails userData= User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userData,null,userData.getAuthorities());

        //add othe details like user ip ...
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource());

        //now we can add our autheticated use to springcontext holder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);

    }
}
