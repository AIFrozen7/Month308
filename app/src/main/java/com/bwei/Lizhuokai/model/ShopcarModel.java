package com.bwei.Lizhuokai.model;

import com.bwei.Lizhuokai.utils.OkHttp3;

/**
 * @Auther: 李
 * @Date: 2019/3/8 13:52:38
 * @Description:
 */
public class ShopcarModel implements IShopcarModel{
    @Override
    public void getData(String url, final IShopCallBack iShopCallBack) {
        OkHttp3.getInstance().doGet(url, new OkHttp3.NetCallBack() {
            @Override
            public void onSuccess(String data) {
                iShopCallBack.onSuccess(data);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
