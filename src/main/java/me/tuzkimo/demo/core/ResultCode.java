package me.tuzkimo.demo.core;

/**
 * RESTful 接口统一封装响应码
 *
 * Created by tuzkimo on 2018-01-24 14:25
 */
public enum ResultCode {
  OK(200),
  INVALID_REQUEST(400),
  UNAUTHORIZED(401),
  NOT_FOUND(404),
  INTERNAL_SERVER_ERROR(500);

  private final Integer code;

  ResultCode(Integer code) {
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}
