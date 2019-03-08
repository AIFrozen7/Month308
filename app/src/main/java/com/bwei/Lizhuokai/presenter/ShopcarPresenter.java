package com.bwei.Lizhuokai.presenter;

import com.bwei.Lizhuokai.api.Api;
import com.bwei.Lizhuokai.model.IShopcarModel;
import com.bwei.Lizhuokai.model.ShopcarModel;
import com.bwei.Lizhuokai.view.IShopcarView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Auther: 李
 * @Date: 2019/3/8 13:52:53
 * @Description:
 */
public class ShopcarPresenter<T> implements IShopcarPresenter{
    ShopcarModel shopcarModel;
    IShopcarView iShopcarView;
    private Reference reference;

    public ShopcarPresenter(IShopcarView iShopcarView) {
        this.iShopcarView = iShopcarView;
        shopcarModel = new ShopcarModel();
    }
    //弱引用处理内存泄漏
    //绑定
    public void attachView(T t){
        reference = new WeakReference(t);
    }
    //解绑
    public void detachView(){
        if (reference.get() != null){
            reference.clear();
            reference = null;
        }
    }

    @Override
    public void getData() {
        shopcarModel.getData(Api.SHOPCAR, new IShopcarModel.IShopCallBack() {
            @Override
            public void onSuccess(String data) {
                iShopcarView.getData(data);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
