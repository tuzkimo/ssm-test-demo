package me.tuzkimo.demo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.tuzkimo.demo.dao.UserMapper;
import me.tuzkimo.demo.model.User;
import me.tuzkimo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
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
	private UserService userService;

	@RequestMapping("/user")
	public List<User> getAll() {
		return userService.findAll();
	}

//	@RequestMapping("/user/{id}")
//	public User getById(@PathVariable Long id) {
//		return userService.findById(id);
//	}

	@RequestMapping("/user/{uid}")
	public User getByUid(@PathVariable String uid) {
		return userService.findBy("uid", uid);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
