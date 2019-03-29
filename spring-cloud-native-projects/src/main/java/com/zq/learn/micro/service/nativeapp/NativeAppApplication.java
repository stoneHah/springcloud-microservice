package com.zq.learn.micro.service.nativeapp;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class NativeAppApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
		parentContext.setId("stoneHah");
		parentContext.refresh();

		new SpringApplicationBuilder(NativeAppApplication.class)
				.parent(parentContext)
				.run(args);
	}
}
