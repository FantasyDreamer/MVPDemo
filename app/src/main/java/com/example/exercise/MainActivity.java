package com.example.exercise;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercise.contract.DataContract;
import com.example.exercise.modle.NewsBean;
import com.example.exercise.presenter.MainPresenter;
import com.example.exercise.utils.tools.Utils;
import com.example.exercise.view.activity.LocalDatabaseActivity;
import com.example.exercise.view.adapter.MainAdapter;
import com.example.exercise.view.fragment.HomePageFragment;
import com.example.exercise.view.fragment.MyFragment;
import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements DataContract.View<NewsBean>{
    private DataContract.Presenter presenter;

    private Fragment mTab1;
    private Fragment mTab2;
    private Fragment mTab3;
    private Fragment mTab4;
    //底部的4个图片
    @BindView(R.id.tab_one_img)
    ImageView tab_one_img;
    @BindView(R.id.tab_two_img)
    ImageView tab_two_img;
    @BindView(R.id.tab_three_img)
    ImageView tab_three_img;
    @BindView(R.id.tab_four_img)
    ImageView tab_four_img;
    //时长
    private long clickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getPermission();//权限
        initView();
    }

    private void initView() {
        if (Utils.judgeSDK()){
            ImmersionBar.with(this).statusBarColor(R.color.whiteColor).statusBarDarkFont(true).init();
        }else{
            ImmersionBar.with(this).statusBarColor(R.color.mainBlackColor).init();
        }
        setSelect(0); //默认选择第一个
        new MainPresenter(this,this);
    }

    @Override
    public void setPresenter(DataContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showDataInfo(NewsBean newsBean) {
        if (newsBean == null) return;

    }

    @OnClick({R.id.tab_one_ll,R.id.tab_two_ll,R.id.tab_three_ll,R.id.tab_four_ll})
    public void onViewClicked(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.tab_one_ll:
                setSelect(0);
                break;
            case R.id.tab_two_ll:
                setSelect(1);
                break;
            case R.id.tab_three_ll:
                setSelect(2);
                break;
            case R.id.tab_four_ll:
                setSelect(3);
                break;
        }
    }

    /**
     * 展示点击的Fragment
     * @param i 位置
     */
    private void setSelect(int i){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (i){
            case 0:
                if (mTab1 == null){
                    mTab1 = new HomePageFragment();
                    fragmentTransaction.add(R.id.main_fragment,mTab1);
                }else{
                    fragmentTransaction.show(mTab1);
                }
                tab_one_img.setImageResource(R.mipmap.ic_launcher_round);
                break;
            case 1:
              /*  if (mTab2 == null){
                    mTab2 = new LookingRoomFragment();
                    fragmentTransaction.add(R.id.main_fragment,mTab2);
                }else{
                    fragmentTransaction.show(mTab2);
                }
                tab_two_img.setImageResource(R.mipmap.ic_launcher_round);*/
                break;
            case 2:
             /*   if (mTab3 == null){
                    mTab3 = new LifeFragment();
                    fragmentTransaction.add(R.id.main_fragment,mTab3);
                }else{
                    fragmentTransaction.show(mTab3);
                }
                tab_three_img.setImageResource(R.mipmap.ic_launcher_round);*/
                break;
            case 3:
                if (mTab4 == null){
                    mTab4 = new MyFragment();
                    fragmentTransaction.add(R.id.main_fragment,mTab4);
                }else{
                    fragmentTransaction.show(mTab4);
                }
                tab_four_img.setImageResource(R.mipmap.ic_launcher_round);
                break;
        }
        fragmentTransaction.commit();
    }
    /**
     * 隐藏Fragment
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mTab1 != null){
            fragmentTransaction.hide(mTab1);
        }
        if (mTab2 != null){
            fragmentTransaction.hide(mTab2);
        }
        if (mTab3 != null){
            fragmentTransaction.hide(mTab3);
        }
        if (mTab4 != null){
            fragmentTransaction.hide(mTab4);
        }
    }
    /**
     * 将所有的图片、文字切换为默认状态
     */
    private void resetImg() {
        tab_one_img.setImageResource(R.mipmap.ic_launcher);
        tab_two_img.setImageResource(R.mipmap.ic_launcher);
        tab_three_img.setImageResource(R.mipmap.ic_launcher);
        tab_four_img.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    /**
     * 物理返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
                clickTime = System.currentTimeMillis();
                return true;
            } else {
                this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限申请
     */
    private void getPermission(){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.
                request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
    }
}
