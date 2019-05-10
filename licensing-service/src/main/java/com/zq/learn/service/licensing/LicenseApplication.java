package com.zq.learn.service.licensing;

import com.zq.learn.infrastructure.utils.UserContextFilter;
import com.zq.learn.infrastructure.utils.UserContextInterceptor;
import com.zq.learn.service.orgnization.api.event.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableBinding(Sink.class)
public class LicenseApplication {

    private static final Logger logger = LoggerFactory.getLogger(LicenseApplication.class);

    /*@Bean
    public UserContextFilter userContextFilter(){
        return new UserContextFilter();
    }*/

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        List interceptors = template.getInterceptors();
        if (interceptors==null){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }
        else{
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }

        return template;
    }

    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange){
        logger.debug("Received an event for organization id {}",orgChange.getOrganizationId());
    }


    public static void main(String[] args) {
        SpringApplication.run(LicenseApplication.class, args);
    }
}
