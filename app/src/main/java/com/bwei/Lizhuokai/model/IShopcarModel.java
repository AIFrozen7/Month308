package com.bwei.Lizhuokai.model;

/**
 * @Auther: 李
 * @Date: 2019/3/8 13:52:33
 * @Description:
 */
public interface IShopcarModel {
    void getData(String url,IShopCallBack iShopCallBack);
    interface IShopCallBack{
        void onSuccess(String data);
        void onFailed(Exception e);
    }
}
