package me.tuzkimo.demo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.tuzkimo.demo.core.Result;
import me.tuzkimo.demo.core.ResultGenerator;
import me.tuzkimo.demo.model.User;
import me.tuzkimo.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2018-01-25T15:18:23.83
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Resource
  private UserService userService;

  @PostMapping
  public Result add(@RequestBody User user) {
    userService.save(user);
    return ResultGenerator.generateSuccessResult();
  }

  @DeleteMapping("/{id}")
  public Result delete(@PathVariable Long id) {
    userService.deleteById(id);
    return ResultGenerator.generateSuccessResult();
  }

  @PutMapping
  public Result update(@RequestBody User user) {
    userService.update(user);
    return ResultGenerator.generateSuccessResult();
  }

  @GetMapping("/{id}")
  public Result detail(@PathVariable Long id) {
    User user = userService.findById(id);
    return ResultGenerator.generateSuccessResult(user);
  }

  @GetMapping
  public Result list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "0") Integer size) {
    PageHelper.startPage(page, size);
    List<User> list = userService.findAll();
    PageInfo pageInfo = new PageInfo(list);
    return ResultGenerator.generateSuccessResult(pageInfo);
  }
}
