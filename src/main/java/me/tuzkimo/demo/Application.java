package me.tuzkimo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @RequestMapping("/hello")
	public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
	  return "Hello " + name;
  }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
