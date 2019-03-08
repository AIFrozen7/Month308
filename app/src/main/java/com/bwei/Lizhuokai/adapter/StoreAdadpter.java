package com.bwei.Lizhuokai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bwei.Lizhuokai.R;
import com.bwei.Lizhuokai.bean.ShopCarBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: 李
 * @Date: 2019/3/8 14:49:56
 * @Description:
 */
public class StoreAdadpter extends RecyclerView.Adapter<StoreAdadpter.ViewHolder>{
    private List<ShopCarBean.DataBean> list;
    private Context context;

    public StoreAdadpter(List<ShopCarBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //获取全选框状态
    public void getCheckBoxState(Boolean isCheck){
        for (int i=1;i<list.size();i++){
            list.get(i).setChecked(isCheck);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_store_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.checkbox_store.setChecked(list.get(i).getChecked());
        viewHolder.checkbox_store.setText(list.get(i).getSellerName());
        final List<ShopCarBean.DataBean.ListBean> productlist = list.get(i).getList();
        for (int j =0;j<productlist.size();j++){
            productlist.get(j).setChecked(list.get(j+1).getChecked());
        }
        final ProductAdapter productAdapter = new ProductAdapter(productlist,context);
        viewHolder.recyclerview_product.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL,false));
        viewHolder.recyclerview_product.setAdapter(productAdapter);
        //设置商家checkbox监听
        viewHolder.checkbox_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productAdapter!=null){
                    productAdapter.getCheckBoxState(viewHolder.checkbox_store.isChecked());
                }
                list.get(i).setChecked(viewHolder.checkbox_store.isChecked());
                for (int j=0;j<list.size();j++){
                    if (!list.get(j).getChecked()){
                        listener.onCheck(false);
                        return;
                    }
                }
                listener.onCheck(true);
            }
        });
        //根据商品改变商家
        productAdapter.setListener(new ProductAdapter.setOnProductCallBack() {
            @Override
            public void oncheck(Boolean ischeck) {
                list.get(i).setChecked(ischeck);
                viewHolder.checkbox_store.setChecked(ischeck);
                listener.onCheck(ischeck);
            }
        });
        productAdapter.setGetPriceCallBack(new ProductAdapter.getPriceCallBack() {
            @Override
            public void getPrice(List<ShopCarBean.DataBean.ListBean> list) {
                listener2.getPrice(productlist);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_store)
        CheckBox checkbox_store;
        @BindView(R.id.recyclerview_product)
        RecyclerView recyclerview_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    setOnStoreClickListener listener;
    public interface setOnStoreClickListener{
        void onCheck(Boolean ischecked);
    }

    public void setListener(setOnStoreClickListener listener) {
        this.listener = listener;
    }

    //价格监听
    getPriceCallBack listener2;
    public interface getPriceCallBack{
        void getPrice(List<ShopCarBean.DataBean.ListBean> list);
    }

    public void setListener2(getPriceCallBack listener2) {
        this.listener2 = listener2;
    }
}
