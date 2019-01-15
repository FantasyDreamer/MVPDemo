package com.example.exercise.utils.retrofit;

import com.example.exercise.modle.NewsBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/7/21.
 * Rxtrofit 请求服务
 */

public interface RequestService {
    //Get请求，方法中指定@Path参数和@Query参数
    //@path参数用于替换url中{}的部分，
    //@Query将在url地址中追加类似"page=1"的字符串

    /**
     * 常用的注解：
     *
     * @GET GET请求方式
     * @POST POST请求方式
     * @Query GET请求参数
     * @Header 用来添加Header请求头
     * @FormUrlEncoded post请求头标示
     * <p>
     * 其他注解请求方式：
     * @PUT 表示这是一个PUT请求
     * @DELETE 表示这是一个DELETE请求
     * @HEAD 表示这是一个HEAD请求
     * @OPTIONS 表示这是一个OPTION请求
     * @PATCH 表示这是一个PAT请求
     */

    //设缓存有效期为1天
    long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    String CACHE_CONTROL_NETWORK = "max-age=0";

    //POST请求
    @FormUrlEncoded
    @POST("/a/login")
    Observable<NewsBean> getVerificationCodePost(@Field("__ajax") String __ajax, @Field("username") String username, @Field("password") String password, @Field("mobileLogin") String mobileLogin);

    //POST请求
    @FormUrlEncoded
    @POST("/a/login")
    Observable<NewsBean> getVerificationCodePostMap(@FieldMap Map<String, String> map);

    //GET请求
    @GET("/a/login")
    Observable<NewsBean> getVerificationGet(@Field("__ajax") String __ajax, @Field("username") String username, @Field("password") String password, @Field("mobileLogin") String mobileLogin);

    //GET请求，设置缓存
    @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
    @GET("/a/login")
    Observable<NewsBean> getVerificationGetCache(@Field("__ajax") String __ajax, @Field("username") String username, @Field("password") String password, @Field("mobileLogin") String mobileLogin);

    @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
    @GET("journalismApi")
    Observable<NewsBean> getMainMenu();
}
