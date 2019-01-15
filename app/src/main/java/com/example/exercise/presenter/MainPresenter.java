package com.example.exercise.presenter;

import android.content.Context;
import android.util.Log;

import com.example.exercise.App;
import com.example.exercise.contract.DataContract;
import com.example.exercise.modle.NewsBean;
import com.example.exercise.utils.network.NetWorks;
import com.example.exercise.utils.retrofit.RetrofitClient;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/8/3.
 */

public class MainPresenter implements DataContract.Presenter{
    private DataContract.View view;
    private Context context;

    public MainPresenter(DataContract.View view , Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
    }

    @Override
    public void start(String parameterMap) {
        loadData(parameterMap);
    }

    @Override
    public void loadData(String s) {
        NetWorks.GetCache(new Observer<NewsBean>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(NewsBean newsBean) {//成功
                view.showDataInfo(newsBean);
            }

            @Override
            public void onError(Throwable e) {//异常

            }

            @Override
            public void onComplete() {//完成
            }
        });
    }
}
