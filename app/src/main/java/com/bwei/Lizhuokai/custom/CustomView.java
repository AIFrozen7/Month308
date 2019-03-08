package com.bwei.Lizhuokai.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.Lizhuokai.R;
import com.bwei.Lizhuokai.adapter.ProductAdapter;
import com.bwei.Lizhuokai.bean.ShopCarBean;

import java.util.List;

/**
 * @Auther: 李
 * @Date: 2019/3/8 14:31:29
 * @Description:
 */
public class CustomView extends RelativeLayout {

    private Button btn_add;
    private Button btn_remove;
    private EditText product_num;
    private ProductAdapter productAdapter;
    private List<ShopCarBean.DataBean.ListBean> list;
    private int position;
    private int num=1;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.customview_layout,this);
        btn_add = findViewById(R.id.btn_add);
        btn_remove = findViewById(R.id.btn_remove);
        product_num = findViewById(R.id.product_num);

        product_num.setText(num+"");

        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                list.get(position).setNum(num);
                product_num.setText(num+"");
                listener.setAdd();
                productAdapter.notifyDataSetChanged();
            }
        });
        btn_remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num<= 1){
                    Toast.makeText(context, "不能为零", Toast.LENGTH_SHORT).show();
                }else {
                    num--;
                    list.get(position).setNum(num);
                    product_num.setText(num+"");
                    listener.setRemove();
                    productAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void setData(ProductAdapter productAdapter,List<ShopCarBean.DataBean.ListBean> list,int i){
        this.productAdapter = productAdapter;
        this.list = list;
        position = i;
        num = list.get(i).getNum();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //
    setOnAddOrRemoveClickListener listener;
    public interface setOnAddOrRemoveClickListener{
        void setAdd();
        void setRemove();
    }
    public void setListener(setOnAddOrRemoveClickListener listener) {
        this.listener = listener;
    }
}
