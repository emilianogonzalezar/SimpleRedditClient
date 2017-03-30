package com.egonzalez.simpleredditclient.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emgonzalez on 29/3/17.
 */

public final class ServiceFactory {

    private static final String URL = "https://www.reddit.com/";

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public RedditService getRedditService() {
        return createService(RedditService.class);
    }

    private <S> S createService(final Class<S> serviceClass) {
        final Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL)
            .build();

        return retrofit.create(serviceClass);
    }
}
