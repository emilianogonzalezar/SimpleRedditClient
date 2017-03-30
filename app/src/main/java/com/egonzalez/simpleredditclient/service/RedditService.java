package com.egonzalez.simpleredditclient.service;

import com.egonzalez.simpleredditclient.model.TopListing;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by emgonzalez on 29/3/17.
 */

public interface RedditService {
    @GET("top.json?raw_json=1&")
    Call<TopListing> getTopListing(
        @Query("count") int count,
        @Query("limit") int limit);
}
