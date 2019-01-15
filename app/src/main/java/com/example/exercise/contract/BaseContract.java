package com.example.exercise.contract;


import com.example.exercise.presenter.BasePresenter;

public interface BaseContract {
    interface View<T> extends BaseView<BaseContract.Presenter>{

        void setPresenter(BaseContract.Presenter presenter);

        void showLoading();//展示加载框

        void dismissLoading();//取消加载框展示

        void showDataInfo(T t);//将网络请求得到的用户信息回调
    }

    interface Presenter extends BasePresenter {
        void loadData(String content);
    }
}
