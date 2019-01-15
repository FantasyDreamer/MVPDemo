package com.example.exercise.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.exercise.App;
import com.example.exercise.R;
import com.example.exercise.modle.LocalDatabase;
import com.example.exercise.utils.greendao.DaoSession;
import com.example.exercise.utils.greendao.LocalDatabaseDao;
import com.example.exercise.utils.tools.Utils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/1 0001.
 * Activity 基类
 */

public abstract class BaseActivity extends Activity{
    private DaoSession daoSession;
    private LocalDatabaseDao localDatabaseDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        if (Utils.judgeSDK()){
            ImmersionBar.with(this).statusBarColor(R.color.whiteColor).statusBarDarkFont(true).init();
        }else{
            ImmersionBar.with(this).statusBarColor(R.color.mainBlackColor).init();
        }
        setSQLLite();
        initView();
        setListener();
        setData();
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
        App app = (App) getApplication();
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
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }
}
