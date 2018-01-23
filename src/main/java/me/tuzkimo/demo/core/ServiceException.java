package me.tuzkimo.demo.core;

/**
 * 业务异常
 *
 * Created by tuzkimo on 2018-01-23
 */
public class ServiceException extends RuntimeException {

  public ServiceException() {
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
