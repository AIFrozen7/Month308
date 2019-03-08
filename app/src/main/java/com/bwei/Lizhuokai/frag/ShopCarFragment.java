package com.bwei.Lizhuokai.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.Lizhuokai.R;
import com.bwei.Lizhuokai.adapter.StoreAdadpter;
import com.bwei.Lizhuokai.bean.ShopCarBean;
import com.bwei.Lizhuokai.presenter.ShopcarPresenter;
import com.bwei.Lizhuokai.view.IShopcarView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Auther: 李
 * @Date: 2019/3/8 14:15:00
 * @Description:
 */
public class ShopCarFragment extends Fragment implements IShopcarView {

    private View view;
    private ShopcarPresenter shopcarPresenter;
    private Unbinder bind;
    @BindView(R.id.recyclerview_store)
    RecyclerView recyclerview_store;
    @BindView(R.id.checkbox_all)
    CheckBox checkbox_all;
    @BindView(R.id.price_all)
    TextView price_all;
    @BindView(R.id.btn_jiesuan)
    Button btn_jiesuan;
    private StoreAdadpter storeAdadpter;
    private int totalPrice = 0;
    private int totalNum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopcar_frag_layout, container, false);
        bind = ButterKnife.bind(this, view);

        recyclerview_store.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false));

        shopcarPresenter = new ShopcarPresenter(this);
        shopcarPresenter.attachView(this);
        shopcarPresenter.getData();

        //全选监听
        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeAdadpter!=null){
                    storeAdadpter.getCheckBoxState(checkbox_all.isChecked());
                }
            }
        });
        return view;
    }

    @Override
    public void getData(String data) {
        Gson gson = new Gson();
        ShopCarBean shopCarBean = gson.fromJson(data, ShopCarBean.class);
        List<ShopCarBean.DataBean> datalist = shopCarBean.getData();
        storeAdadpter = new StoreAdadpter(datalist,getActivity());
        storeAdadpter.setListener(new StoreAdadpter.setOnStoreClickListener() {
            @Override
            public void onCheck(Boolean ischecked) {
                checkbox_all.setChecked(ischecked);
            }
        });
        storeAdadpter.setListener2(new StoreAdadpter.getPriceCallBack() {
            @Override
            public void getPrice(List<ShopCarBean.DataBean.ListBean> list) {
                int priceAll = 0;
                int count  = 0;
                for (int i=0;i<list.size();i++){
                    if (list.get(i).getChecked()){
                        Double price = list.get(i).getPrice();
                        int num = list.get(i).getNum();
                        priceAll = (int) (price*num);
                        count = num;
                    }else {
                        priceAll = 0;
                        count = 0;
                    }
                    totalPrice += priceAll;
                    totalNum += count;
                }
                price_all.setText("￥:"+totalPrice);
                btn_jiesuan.setText("结算("+ totalNum+")");
            }
        });
        recyclerview_store.setAdapter(storeAdadpter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shopcarPresenter.detachView();
        bind.unbind();
    }
}
