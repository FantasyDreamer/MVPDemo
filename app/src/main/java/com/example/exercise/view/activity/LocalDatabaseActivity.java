package com.example.exercise.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.exercise.R;
import com.example.exercise.modle.LocalDatabase;
import com.example.exercise.view.adapter.LocalDataAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/1 0001.
 * 测试GrenDao数据库操作
 */

public class LocalDatabaseActivity extends BaseActivity {
    private List<LocalDatabase> localDatabases;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    private LocalDataAdapter localDataAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_local_database;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        localDataAdapter = new LocalDataAdapter(R.layout.activity_main_list, null);
        rvList.setAdapter(localDataAdapter);
    }

    @Override
    protected void setListener() {

    }
    @OnClick({R.id.butClick,R.id.butDelete})
     public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butClick:

                break;
            case R.id.butDelete:
                break;
        }
    }

    @Override
    protected void setData() {

    }
}
