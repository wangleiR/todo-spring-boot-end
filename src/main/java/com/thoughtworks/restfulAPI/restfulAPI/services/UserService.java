package com.thoughtworks.restfulAPI.restfulAPI.services;

import com.thoughtworks.restfulAPI.restfulAPI.model.Token;
import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.repository.UserRepository;


import com.thoughtworks.restfulAPI.restfulAPI.util.Enctrpt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private Enctrpt enctrpt;

    private static final byte[] MY_TOKEN = "password".getBytes();

    public void register(User user) throws NoSuchAlgorithmException {
         user.setPassword(enctrpt.encyrptByMD5(user.getPassword()));
         userRepository.save(user);
    }

    public Token login(User user) throws NoSuchAlgorithmException {
        User userFind = userRepository.findByName(user.getName());
        Token token = new Token();

        if (userFind.getPassword().equals(enctrpt.encyrptByMD5(user.getPassword()))) {

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
