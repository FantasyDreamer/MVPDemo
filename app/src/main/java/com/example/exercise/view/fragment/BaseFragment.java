package com.example.exercise.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exercise.App;
import com.example.exercise.R;
import com.example.exercise.utils.greendao.DaoSession;
import com.example.exercise.utils.greendao.LocalDatabaseDao;
import com.example.exercise.utils.tools.Utils;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/12/26 0026.
 */

public abstract class BaseFragment extends ImmersionFragment {

    private DaoSession daoSession;
    private LocalDatabaseDao localDatabaseDao;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), getLayoutResId(), null);
        ButterKnife.bind(this,view);
        setSQLLite();
        initView();
        setListener();
        setData();
        return view;
    }

    @Override
    public void initImmersionBar() {
        if (Utils.judgeSDK()){
            ImmersionBar.with(this).statusBarColor(R.color.whiteColor).statusBarDarkFont(true).init();
        }else{
            ImmersionBar.with(this).statusBarColor(R.color.mainBlackColor).init();
        }
    }

    /**
     * 设置页面布局文件地址
     */
    protected abstract int getLayoutResId();
    /**
     * 初始化控件
     */
    protected abstract void initView();
    /**
     * 设置监听
     */
    protected abstract void setListener();
    /**
     * 设置填充数据
     */
    protected abstract void setData();
    /**
     * 使用数据库 查询数据
     */
    private void setSQLLite(){
        App app = (App) getActivity().getApplication();
        daoSession = app.getDaoSession();
        localDatabaseDao = app.getLocalDatabaseDao();
    }

    /**
     * 设置公共方法 返回查询数据库的数据
     * @return
     */
    protected LocalDatabaseDao getLocalDatabaseDao(){
        return localDatabaseDao;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放绑定
        unbinder.unbind();
    }
}
