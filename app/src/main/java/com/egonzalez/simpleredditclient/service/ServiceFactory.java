package com.egonzalez.simpleredditclient.service;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emgonzalez on 29/3/17.
 */

public final class ServiceFactory {

    public static final String URL = "https://www.reddit.com/";

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private Retrofit mRetrofit = new Retrofit.Builder()
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public void setRetrofit(final Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public RedditService getRedditService() {
        return createService(RedditService.class);
    }

    private <S> S createService(final Class<S> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
