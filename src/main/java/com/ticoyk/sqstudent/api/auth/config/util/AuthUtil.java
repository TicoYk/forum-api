package com.ticoyk.sqstudent.api.auth.config.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.algorithms.Algorithm;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    public Algorithm algorithm() {
        return Algorithm.HMAC256("secret".getBytes());
    }

    public Date jwtTimeToLive() {
        return new Date(System.currentTimeMillis() + 10 * 60 * 1000);
    }

}
