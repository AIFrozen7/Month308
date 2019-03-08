package com.bwei.Lizhuokai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.Lizhuokai.R;
import com.bwei.Lizhuokai.bean.ShopCarBean;
import com.bwei.Lizhuokai.custom.CustomView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: 李
 * @Date: 2019/3/8 14:50:06
 * @Description:
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ShopCarBean.DataBean.ListBean> list;
    private Context context;

    public ProductAdapter(List<ShopCarBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //获取商家选中状态
    public void getCheckBoxState(Boolean isCheck){
        for (int i=0;i<list.size();i++){
            list.get(i).setChecked(isCheck);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_product_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.checkbox_product.setChecked(list.get(i).getChecked());
        viewHolder.product_name.setText(list.get(i).getTitle());
        viewHolder.product_price.setText(list.get(i).getPrice()+"");
        Glide.with(context).load(list.get(i).getImages()).into(viewHolder.product_img);
        viewHolder.custom_addOrremove.setListener(new CustomView.setOnAddOrRemoveClickListener() {
            @Override
            public void setAdd() {
                getPriceCallBack.getPrice(list);
            }

            @Override
            public void setRemove() {
                getPriceCallBack.getPrice(list);
            }
        });
        viewHolder.checkbox_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ischecked = viewHolder.checkbox_product.isChecked();
                list.get(i).setChecked(ischecked);
                for (int j=0;j<list.size();j++){
                    if (!list.get(j).getChecked()){
                        listener.oncheck(false);
                        getPriceCallBack.getPrice(list);
                        return;
                    }
                }
                listener.oncheck(true);
                getPriceCallBack.getPrice(list);
            }
        });
        getPriceCallBack.getPrice(list);
        viewHolder.custom_addOrremove.setData(this,list,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_product)
        CheckBox checkbox_product;
        @BindView(R.id.product_img)
        ImageView product_img;
        @BindView(R.id.product_price)
        TextView product_price;
        @BindView(R.id.product_name)
        TextView product_name;
        @BindView(R.id.custom_addOrremove)
        CustomView custom_addOrremove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    //选中监听
    setOnProductCallBack listener;
    public interface setOnProductCallBack{
        void oncheck(Boolean ischeck);
    }
    public void setListener(setOnProductCallBack listener) {
        this.listener = listener;
    }
    //价格监听
    getPriceCallBack getPriceCallBack;
    public interface getPriceCallBack{
        void getPrice(List<ShopCarBean.DataBean.ListBean> list);
    }

    public void setGetPriceCallBack(ProductAdapter.getPriceCallBack getPriceCallBack) {
        this.getPriceCallBack = getPriceCallBack;
    }
}
