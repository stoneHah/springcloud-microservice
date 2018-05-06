package com.zq.learn.microservicemovieconsumer.controller;

import com.netflix.discovery.converters.Auto;
import com.zq.learn.microservicemovieconsumer.entity.User;
import com.zq.learn.microservicemovieconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;


    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id.intValue());
    }
}
