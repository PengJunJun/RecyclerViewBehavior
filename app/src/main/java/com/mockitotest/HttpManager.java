package com.mockitotest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bykj003 on 2016/9/23.
 */
class HttpManager {
    private static final String HOST_URL = "http://www.vmaking.com/";
    private static volatile HttpManager mHttpManager;
    private Retrofit mRetrofit;
    private volatile UserApi mUserApi;

    private HttpManager() {
        mRetrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST_URL)
                .build();
        mUserApi = mRetrofit.create(UserApi.class);
    }

    static synchronized HttpManager getInstance() {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                if (mHttpManager == null) {
                    mHttpManager = new HttpManager();
                }
            }
        }
        return mHttpManager;
    }

    public UserApi getUserApi() {
        return mUserApi;
    }
}
