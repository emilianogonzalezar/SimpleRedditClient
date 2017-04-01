package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String SAVEDINSTANCESTATE_REQUESTDATA = "SAVEDINSTANCESTATE_REQUESTDATA";

    private boolean mMustRequestData = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mMustRequestData = savedInstanceState.getBoolean(SAVEDINSTANCESTATE_REQUESTDATA);
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

        if (mMustRequestData) {
            requestData();
        }
    }

    private void requestData() {
        showProgress();
        ServiceFactory.getInstance().getRedditService().getTopListing(0, 50).enqueue(new Callback<TopListing>() {
            @Override
            public void onResponse(final Call<TopListing> call, final Response<TopListing> response) {
                showViewPager(response.body());
            }

            @Override
            public void onFailure(final Call<TopListing> call, final Throwable t) {
                showConnectionError();
            }
        });
    }

    private void showConnectionError() {
        final ConnectionProblemFragment connectionProblemFragment = new ConnectionProblemFragment();

        connectionProblemFragment.getRetryButtonClicked()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fragment -> requestData());

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

        mMustRequestData = false;
    }

    private void showProgress() {
        final ProgressBarFragment fragment = new ProgressBarFragment();

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_main_fragment_container, fragment)
            .commit();
    }
}
