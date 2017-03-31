package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.adapter.TopListingPagerAdapter;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_ITEMS_PER_PAGE = 10;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceFactory.getInstance().getRedditService().getTopListing(0, 50).enqueue(new Callback<TopListing>() {
            @Override
            public void onResponse(final Call<TopListing> call, final Response<TopListing> response) {
                final View progressBar = findViewById(R.id.activity_main_progressbar);
                final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);

                progressBar.setVisibility(View.GONE);
                viewPager.setAdapter(new TopListingPagerAdapter(
                        getSupportFragmentManager(),
                        MAX_ITEMS_PER_PAGE,
                        response.body())
                );

            }

            @Override
            public void onFailure(final Call<TopListing> call, final Throwable t) {

            }
        });
    }
}
