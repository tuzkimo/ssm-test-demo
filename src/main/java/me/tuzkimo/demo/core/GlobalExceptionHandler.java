package me.tuzkimo.demo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tuzkimo on 2018-01-25 16:25
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private Result ServiceExceptionHandler(ServiceException e) {
    LOGGER.warn("业务异常", e);
    return ResultGenerator.generateFailedResult(e.getMessage());
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  private Result NoHandlerFoundExceptionHandler(NoHandlerFoundException e, HttpServletRequest request) {
    LOGGER.warn("接口不存在", e);
    return ResultGenerator.generateFailedResult("接口 [" + request.getRequestURI() + "] 不存在")
        .setCode(ResultCode.NOT_FOUND);
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private Result ServletExceptionHandler(ServletException e) {
    LOGGER.warn("Servlet 异常", e);
    return ResultGenerator.generateFailedResult(e.getMessage());
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private Result RuntimeExceptionHandler(Exception e) {
    LOGGER.error("服务器错误", e);
    return ResultGenerator.generateFailedResult(e.getMessage())
        .setCode(ResultCode.INTERNAL_SERVER_ERROR);
  }
}
