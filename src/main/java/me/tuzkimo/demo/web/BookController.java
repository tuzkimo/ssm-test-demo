package me.tuzkimo.demo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.tuzkimo.demo.core.Result;
import me.tuzkimo.demo.core.ResultGenerator;
import me.tuzkimo.demo.model.Book;
import me.tuzkimo.demo.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2018-01-25T15:18:23.83
 */
@RestController
@RequestMapping("/book")
public class BookController {

  @Resource
  private BookService bookService;

  @PostMapping
  public Result add(@RequestBody Book book) {
    bookService.save(book);
    return ResultGenerator.generateSuccessResult();
  }

  @DeleteMapping("/{id}")
  public Result delete(@PathVariable Long id) {
    bookService.deleteById(id);
    return ResultGenerator.generateSuccessResult();
  }

  @PutMapping
  public Result update(@RequestBody Book book) {
    bookService.update(book);
    return ResultGenerator.generateSuccessResult();
  }

  @GetMapping("/{id}")
  public Result detail(@PathVariable Long id) {
    Book book = bookService.findById(id);
    return ResultGenerator.generateSuccessResult(book);
  }

  @GetMapping
  public Result list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "0") Integer size) {
    PageHelper.startPage(page, size);
    List<Book> list = bookService.findAll();
    PageInfo pageInfo = new PageInfo(list);
    return ResultGenerator.generateSuccessResult(pageInfo);
  }
}
