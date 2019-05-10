package com.zq.learn.stream;

import com.zq.learn.stream.sender.SinkSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019/4/29上午11:03
 */
@SpringBootApplication
@EnableBinding({Sink.class})
public class StreamDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamDemoApplication.class, args);
    }


    @StreamListener(Sink.INPUT)
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
    }
}
