package com.example.exercise.utils.okhttp;

import java.io.Serializable;

import retrofit2.Response;

/**
 * 后台服务返回的数据对应的数据结构,需要根据后台服务的特点调整类的内容
 * <pre>
 * 如果我们需要使用Http响应头消息那么可以这么做
 * 返回参数使用{@link Response}代替,其中泛型参数为{@link ApiResponse}
 * eg:
 * @GET("/account") Observable<ApiResponse<List<User>>> getAccount();
 * 改为
 * @GET("/account") Observable<Response<ApiResponse<List<User>>>> getAccount();
 * </pre>
 *
 * @param <T> 数据类型 "结构体"
 */
public class ApiResponse<T> implements Serializable {
  /** 和后台服务约定的响应码 */
  private int code;
  /** 服务调用结果 描述|错误信息 */
  private String message;
  /** 数据 */
  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public boolean isSuccess() {
    return this.code == 0;
  }

  public int getCode() {
    return this.code;
  }

  public String getMessage() {
    return message;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}