package com.thoughtworks.restfulAPI.restfulAPI.services;

import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private  HashMap<Long, HashMap<String, String>> sessions = new HashMap<>();

    public void register(User user) {
         userRepository.save(user);
    }

    public Long login(User user) {

        User userFind = userRepository.findByName(user.getName());

        if (userFind.getPassword().equals(user.getPassword())) {
            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put("userName", userFind.getName());
            userInfo.put("userId", userFind.getId().toString());
            Long key = new Date().getTime();
            sessions.put(key, userInfo);
            return key;
        }

        return null;
    }

    public String verifySessionId(Long sessionId){
        if (sessions.containsKey(sessionId)) {
            return  sessions.get(sessionId).get("userId");
        }
        return null;
    }
}
