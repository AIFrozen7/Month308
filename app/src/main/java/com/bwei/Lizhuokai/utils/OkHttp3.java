package com.bwei.Lizhuokai.utils;

import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Auther: 李
 * @Date: 2019/3/8 13:39:58
 * @Description:
 */
public class OkHttp3 {

    private final OkHttpClient httpClient;
    private static OkHttp3 instance;
    private Handler handler = new Handler();

    //应用拦截器
    public Interceptor getInterceptor(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response;
            }
        };
        return interceptor;
    }
    //日志拦截器
    public HttpLoggingInterceptor getHttpLogInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

            }
        });
        return httpLoggingInterceptor;
    }
    //
    public OkHttp3(){
        //添加拦截器
//设置超时
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())//添加拦截器
                .addInterceptor(getHttpLogInterceptor())
                .readTimeout(3000, TimeUnit.SECONDS)//设置超时
                .connectTimeout(3000,TimeUnit.SECONDS)
                .build();
    }
    //单例模式
    public static OkHttp3 getInstance(){
        if (instance==null){
            synchronized (OkHttp3.class){
                if (instance==null){
                    instance = new OkHttp3();
                }
            }
        }
        return instance;
    }
    public interface NetCallBack{
        void onSuccess(String data);
        void onFailed(Exception e);
    }
    //Get请求
    public void doGet(String url, final NetCallBack netCallBack){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.onFailed(e);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.onSuccess(data);
                    }
                });
            }
        });
    }
}
