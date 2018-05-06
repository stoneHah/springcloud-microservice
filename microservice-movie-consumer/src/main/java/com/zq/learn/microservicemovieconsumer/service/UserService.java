package com.zq.learn.microservicemovieconsumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.zq.learn.microservicemovieconsumer.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final User EMPTY_USER = new User();

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserFallback")
    public User getUserById(int userId){
        long start = System.currentTimeMillis();

        User user = restTemplate.getForObject("http://microservice-provider-user/{1}", User.class, userId);

        long end = System.currentTimeMillis();
        logger.info("spend Time : {} 毫秒", end - start);

        return user;
    }

    public User getUserFallback(int userId){
        return EMPTY_USER;
    }
}
