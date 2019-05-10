package com.zq.learn.service.orgnization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Source.class)
public class OrganizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
    }

    /*@StreamListener(Sink.INPUT)
    public void handle(Person person) {
        System.out.println("Received: " + person);
    }

    public static class Person {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }*/
}
