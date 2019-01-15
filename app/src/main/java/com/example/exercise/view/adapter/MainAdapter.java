package com.example.exercise.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.exercise.R;
import com.example.exercise.modle.NewsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class MainAdapter extends BaseQuickAdapter<NewsBean.DataBean.MoneyBean,BaseViewHolder>{

    public MainAdapter(int layoutResId, @Nullable List<NewsBean.DataBean.MoneyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean.DataBean.MoneyBean item) {
        helper.setText(R.id.tvTitle,item.getTitle().toString())
              .setText(R.id.tvContent,item.getDigest().toString());
    }
}
