package me.tuzkimo.demo.core;

/**
 * RESTful 统一响应结果生成器
 *
 * Created by tuzkimo on 2018-01-24 17:03
 */
public class ResultGenerator {

  private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

  public static Result generateSuccessResult() {
    return new Result()
        .setCode(ResultCode.OK)
        .setMessage(DEFAULT_SUCCESS_MESSAGE);
  }

  public static Result generateSuccessResult(Object data) {
    return new Result()
        .setCode(ResultCode.OK)
        .setMessage(DEFAULT_SUCCESS_MESSAGE)
        .setData(data);
  }

  public static Result generateFailedResult(String message) {
    return new Result()
        .setCode(ResultCode.INVALID_REQUEST)
        .setMessage(message);
  }
}
