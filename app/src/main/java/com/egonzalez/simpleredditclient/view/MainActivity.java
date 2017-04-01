package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.adapter.TopListingPagerAdapter;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import com.viewpagerindicator.TitlePageIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String SAVEDINSTANCESTATE_TOP_LISTING = "SAVEDINSTANCESTATE_TOP_LISTING";

    private static final int MAX_ITEMS_PER_PAGE = 10;

    private View mConnectionError;
    private View mViewPagerLayout;
    private View mProgressBar;
    private TitlePageIndicator mPageIndicator;

    private TopListing mTopListing;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnectionError = findViewById(R.id.activity_main_connection_error);
        mViewPagerLayout = findViewById(R.id.activity_main_viewpager_layout);
        mProgressBar = findViewById(R.id.activity_main_progressbar);
        mPageIndicator = (TitlePageIndicator) findViewById(R.id.activity_main_viewpager_indicator);

        final Button retryButton = (Button) findViewById(R.id.activity_main_connection_error_button);
        retryButton.setOnClickListener(v -> {
            requestData();
        });

        // Restore state
        if (savedInstanceState != null) {
            mTopListing = savedInstanceState.getParcelable(SAVEDINSTANCESTATE_TOP_LISTING);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mTopListing == null) {
            requestData();
        } else {
            populate();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SAVEDINSTANCESTATE_TOP_LISTING, mTopListing);
    }

    private void requestData() {
        showProgress();
        ServiceFactory.getInstance().getRedditService().getTopListing(0, 50).enqueue(new Callback<TopListing>() {
            @Override
            public void onResponse(final Call<TopListing> call, final Response<TopListing> response) {
                mTopListing = response.body();
                populate();
            }

            @Override
            public void onFailure(final Call<TopListing> call, final Throwable t) {
                showConnectionError();
            }
        });
    }

    private void populate() {
        showViewPager();

        final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);

        viewPager.setAdapter(new TopListingPagerAdapter(
            getSupportFragmentManager(),
            MAX_ITEMS_PER_PAGE,
            mTopListing)
        );

        mPageIndicator.setViewPager(viewPager);
    }

    private void showConnectionError() {
        mConnectionError.setVisibility(View.VISIBLE);
        mViewPagerLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void showViewPager() {
        mConnectionError.setVisibility(View.GONE);
        mViewPagerLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgress() {
        mConnectionError.setVisibility(View.GONE);
        mViewPagerLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
