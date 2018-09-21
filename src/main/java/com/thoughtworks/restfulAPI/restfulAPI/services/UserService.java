package com.thoughtworks.restfulAPI.restfulAPI.services;

import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.repository.UserRepository;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private  HashMap<String, HashMap<String, String>> sessions = new HashMap<>();

    public void register(User user) {
         userRepository.save(user);
    }

    /**
     * return session id
     */
    public String login(User user) {
        User userFind = userRepository.findByName(user.getName());

        if (userFind.getPassword().equals(user.getPassword())) {

            String signature = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS512, "password".getBytes())
                    .claim("userId", userFind.getId())
                    .compact();
            return signature;
        }

        return null;
    }

    public Long getUserIdByToken(String token){

        Claims claims = Jwts.parser()
                .setSigningKey("password".getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId",Long.class);

    }
}
