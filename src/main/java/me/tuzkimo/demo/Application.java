package me.tuzkimo.demo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
		PageHelper.startPage(1, 2);
		return userMapper.selectAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
