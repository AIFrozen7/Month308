package com.bwei.Lizhuokai;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bwei.Lizhuokai.bean.ShopCarBean;
import com.bwei.Lizhuokai.frag.MineFragment;
import com.bwei.Lizhuokai.frag.ShopCarFragment;
import com.bwei.Lizhuokai.presenter.ShopcarPresenter;
import com.bwei.Lizhuokai.view.IShopcarView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShopcarPresenter shopcarPresenter;
    private Unbinder bind;
    @BindView(R.id.btn_shopcar)
    Button btn_shopcar;
    @BindView(R.id.btn_mine)
    Button btn_mine;
    private ShopCarFragment shopCarFragment;
    private MineFragment mineFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        shopCarFragment = new ShopCarFragment();
        mineFragment = new MineFragment();

        //事务管理者并提交
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout,shopCarFragment);
        transaction.add(R.id.framelayout,mineFragment);
        transaction.hide(mineFragment).show(shopCarFragment);
        transaction.commit();


        btn_shopcar.setOnClickListener(this);
        btn_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.btn_shopcar:
                transaction.hide(mineFragment).show(shopCarFragment).commit();
                break;
            case R.id.btn_mine:
                transaction.hide(shopCarFragment).show(mineFragment).commit();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopcarPresenter.detachView();
        bind.unbind();
    }


}
