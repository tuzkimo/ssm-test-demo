package me.tuzkimo.demo.core;

import com.alibaba.fastjson.JSON;

/**
 * RESTful 接口统一封装响应
 *
 * Created by tuzkimo on 2018-01-24 14:11
 */
public class Result {

  private Integer code;
  private String message;
  private Object data;

  public Integer getCode() {
    return code;
  }

  public Result setCode(ResultCode resultCode) {
    this.code = resultCode.getCode();
    return this;
  }

  public String getMessage() {
    return message;
  }

  public Result setMessage(String message) {
    this.message = message;
    return this;
  }

  public Object getData() {
    return data;
  }

  public Result setData(Object data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
