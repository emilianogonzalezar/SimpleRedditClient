package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String SAVEDINSTANCESTATE_REQUESTDATA = "SAVEDINSTANCESTATE_REQUESTDATA";

    private boolean mMustRequestData = true;
    private boolean mIsPausing = false;

    private ImageButton mRefreshButton;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefreshButton = (ImageButton) findViewById(R.id.toolbar_refresh_button);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                requestData();
            }
        });

        if (savedInstanceState != null) {
            mMustRequestData = savedInstanceState.getBoolean(SAVEDINSTANCESTATE_REQUESTDATA);
            mRefreshButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SAVEDINSTANCESTATE_REQUESTDATA, mMustRequestData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsPausing = false;

        if (mMustRequestData) {
            requestData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsPausing = true;
    }

    private void requestData() {
        showProgress();
        ServiceFactory.getInstance().getRedditService().getTopListing(0, 50).enqueue(new Callback<TopListing>() {
            @Override
            public void onResponse(final Call<TopListing> call, final Response<TopListing> response) {
                if (!mIsPausing) {
                    if (response.body().getData().getChildren().isEmpty()) {
                        showNoResult();

                    } else {

                        showViewPager(response.body());
                    }
                }
            }

            @Override
            public void onFailure(final Call<TopListing> call, final Throwable t) {
                if (!mIsPausing) {
                    showConnectionError();
                }
            }
        });
    }

    private void showConnectionError() {
        mMustRequestData = true;
        mRefreshButton.setVisibility(View.GONE);

        mRefreshButton.setEnabled(false);
        final ConnectionProblemFragment connectionProblemFragment = new ConnectionProblemFragment();

        connectionProblemFragment.getRetryButtonClicked()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Fragment>() {
                @Override
                public void accept(final Fragment fragment) throws Exception {
                    requestData();
                }
            });

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_main_fragment_container, connectionProblemFragment)
            .commit();
    }

    private void showViewPager(final TopListing topListing) {
        final ViewPagerFragment fragment = new ViewPagerFragment();

        final Bundle args = new Bundle();
        args.putParcelable(ViewPagerFragment.ARGUMENT_TOP_LISTING, topListing);

        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_main_fragment_container, fragment)
            .commit();

        mRefreshButton.setVisibility(View.VISIBLE);
        mMustRequestData = false;
    }

    private void showProgress() {
        mMustRequestData = true;
        mRefreshButton.setVisibility(View.GONE);

        final ProgressBarFragment fragment = new ProgressBarFragment();

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_main_fragment_container, fragment)
            .commit();
    }

    private void showNoResult() {
        final NoResultsFoundFragment fragment = new NoResultsFoundFragment();

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_main_fragment_container, fragment)
            .commit();

        mRefreshButton.setVisibility(View.VISIBLE);
        mMustRequestData = false;
    }
}
