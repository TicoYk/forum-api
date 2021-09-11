package com.ticoyk.forumapi.auth.config.filter;


import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticoyk.forumapi.auth.config.util.AuthUtil;

public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final AuthUtil authUtil;

    public AppAuthenticationFilter(AuthenticationManager authenticationManager, AuthUtil authUtil) {
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = "";
        String password = "";
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                Map<String, String> userJson = extractJsonUser(request);
                username = userJson.get("username");
                password = userJson.get("password");
            } catch (Exception exception) {
                response.setHeader("error_message", exception.getMessage());
            }
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(authUtil.jwtTimeToLive())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("authority", user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(authUtil.algorithm());
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(authUtil.jwtTimeToLive())
                .withIssuer(request.getRequestURL().toString())
                .sign(authUtil.algorithm());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private Map<String, String> extractJsonUser(HttpServletRequest request) throws Exception {
        Map<String, String> userJson = new HashMap<>();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("{") || line.equals("}") ) {
                    continue;
                }
                line = line.replace(",", "");
                line = line.replace("\"", "");
                line = line.trim();
                String property = line.substring(0, line.indexOf(":")).trim();
                String value = line.substring(line.indexOf(":") + 1).trim();
                userJson.put(property, value);
            }
        } finally {
            reader.close();
        }
        if (userJson.containsKey("username") && userJson.containsKey("password")) {
            return userJson;
        }
        throw new Exception("Body doesn't contain username and password");
    }

}
