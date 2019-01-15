package com.example.exercise.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.exercise.R;
import com.example.exercise.modle.LocalDatabase;

import java.util.List;

/**
 * Created by Administrator on 2018/11/1 0001.
 */

public class LocalDataAdapter extends BaseQuickAdapter<LocalDatabase,BaseViewHolder> {
    public LocalDataAdapter(int layoutResId, @Nullable List<LocalDatabase> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, LocalDatabase item) {
        helper.setText(R.id.tvTitle,item.getName().toString())
                .setText(R.id.tvContent,item.getSex().toString());
    }
}
