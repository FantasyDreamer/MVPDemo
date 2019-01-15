package com.example.exercise;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.exercise.utils.greendao.DaoMaster;
import com.example.exercise.utils.greendao.DaoSession;
import com.example.exercise.utils.greendao.LocalDatabaseDao;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2018/8/3.
 */

public class App extends Application{
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private LocalDatabaseDao localDatabaseDao;

    private static Context instance;

    public static Context getInstance() {
        return instance;
    }
    //静态单例
    public static App instances;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = App.this;
        //greenDao数据库配置
        setDatabase();
        //内存泄漏检查
        //setLeakCanary();
    }
    //设置全局变量
    public static App getInstances(){
        return instances;
    }
    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处sport-db表示数据库名称 可以任意填写
        mHelper = new DaoMaster.DevOpenHelper(this, "yulong.db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        //清空一下内存缓存session会给entities带来缓存,导致删除失败bug
        mDaoSession.clear();
        //实例化数据
        localDatabaseDao = this.mDaoSession.getLocalDatabaseDao();
     }
     //提供外部全局接口调用
     public LocalDatabaseDao getLocalDatabaseDao(){
        return localDatabaseDao;
     }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    //提供外部方法供调用SQLiteDatabase方法
    public SQLiteDatabase getDb() {
        return db;
    }

    //设置LeakCanary初始化
    private void setLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
