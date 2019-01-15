package com.example.exercise.utils.network;

import com.example.exercise.modle.NewsBean;
import com.example.exercise.utils.retrofit.RequestService;
import com.example.exercise.utils.retrofit.RetrofitClient;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/8/3.
 */

public class NetWorks extends RetrofitClient{
    //创建实现接口调用
    private static final RequestService service = RetrofitClient().create(RequestService.class);

    //POST请求
    public static void verificationCodePost(String __ajax, String username, String password, String mobileLogin, Observer<NewsBean> observer) {
        setSubscribe(service.getVerificationCodePost(__ajax, username, password, mobileLogin), observer);
    }

    //POST请求参数以map传入
    public static void verificationCodePostMap(Map<String, String> map, Observer<NewsBean> observer) {
        setSubscribe(service.getVerificationCodePostMap(map), observer);
    }

    //Get请求设置缓存
    public static void verificationCodeGetCache(String __ajax, String username, String password, String mobileLogin, Observer<NewsBean> observer) {
        setSubscribe(service.getVerificationGetCache(__ajax, username, password, mobileLogin), observer);
    }

    //Get请求
    public static void verificationCodeGet(String __ajax, String username, String password, String mobileLogin, Observer<NewsBean> observer) {
        setSubscribe(service.getVerificationGet(__ajax, username, password, mobileLogin), observer);
    }

    //Get请求
    public static void verificationCodeGetsub(String __ajax, String username, String password, String mobileLogin, Observer<NewsBean> observer) {
        setSubscribe(service.getVerificationGet(__ajax, username, password, mobileLogin), observer);
    }

    //Get请求
    public static void GetCache(Observer<NewsBean> observer) {
        setSubscribe(service.getMainMenu(), observer);
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    private static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
