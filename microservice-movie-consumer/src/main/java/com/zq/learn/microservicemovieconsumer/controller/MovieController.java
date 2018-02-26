package com.zq.learn.microservicemovieconsumer.controller;

import com.zq.learn.microservicemovieconsumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return restTemplate.getForObject("http://localhost:8000/{1}", User.class, id);
    }
}
