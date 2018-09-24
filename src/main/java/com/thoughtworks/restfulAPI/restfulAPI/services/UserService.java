package com.thoughtworks.restfulAPI.restfulAPI.services;

import com.thoughtworks.restfulAPI.restfulAPI.model.Token;
import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final byte[] MY_TOKEN = "password".getBytes();

    public void register(User user) {
         userRepository.save(user);
    }

    public Token login(User user) {
        User userFind = userRepository.findByName(user.getName());
        Token token = new Token();

        if (userFind.getPassword().equals(user.getPassword())) {

            String signature = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS512, MY_TOKEN)
                    .claim("userId", userFind.getId())
                    .compact();
            token.setToken(signature);
            return token;
        }

        return null;
    }

    public Long getUserIdByToken(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(MY_TOKEN)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId",Long.class);

    }
}
