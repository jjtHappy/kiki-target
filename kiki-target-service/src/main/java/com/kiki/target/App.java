package com.kiki.target;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication()
@EnableScheduling//开启调度
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
