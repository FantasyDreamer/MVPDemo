package com.example.exercise.utils.retrofit;

import android.util.Log;

import com.example.exercise.modle.NewsBean;
import com.example.exercise.utils.okhttp.OkHttpManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/7/21.
 * 被观察者Base类
 */

public class RetrofitClient {
    private static final String API_SERVER = "https://www.apiopen.top/";
    private static Retrofit mRetrofit ;
    private static OkHttpClient okHttpClient;
    /*
     *初始化RxRetrofitClient方法
     * baseUrl 需已"/" 结尾 不然会抛出IllegalArgumentException异常
     */
    protected static Retrofit RetrofitClient(){
        if (mRetrofit == null){
            if (okHttpClient == null){
                okHttpClient = OkHttpManager.getHttpClient();
            }
            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(API_SERVER)
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //增加返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //增加返回值为Oservable<T>的支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //增加网络请求所使用的方法 设置使用okhttp网络请求
                    .client(okHttpClient)
                    .build();
        }

        return mRetrofit;
    }
}
