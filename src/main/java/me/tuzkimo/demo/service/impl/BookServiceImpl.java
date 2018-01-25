package me.tuzkimo.demo.service.impl;

import me.tuzkimo.demo.core.AbstractService;
import me.tuzkimo.demo.dao.BookMapper;
import me.tuzkimo.demo.model.Book;
import me.tuzkimo.demo.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018-01-25T15:18:23.83
 */
@Service
@Transactional
public class BookServiceImpl extends AbstractService<Book> implements BookService {

  @Resource
  private BookMapper bookMapper;
}
