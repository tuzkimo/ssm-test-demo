package me.tuzkimo.demo;

import me.tuzkimo.demo.dao.UserMapper;
import me.tuzkimo.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@SpringBootApplication
@MapperScan("me.tuzkimo.demo.dao")
@RestController
@RequestMapping("/api")
public class Application {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/user")
	public List<User> getAll() {
		return userMapper.selectAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
