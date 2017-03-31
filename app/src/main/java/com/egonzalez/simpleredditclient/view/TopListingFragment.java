package com.egonzalez.simpleredditclient.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ProgressBar;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.adapter.TopListingAdapter;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopListingFragment extends Fragment {

    public static final String BUNDLE_ARGUMENT_COUNT = "BUNDLE_ARGUMENT_COUNT";
    public static final String BUNDLE_ARGUMENT_LIMIT = "BUNDLE_ARGUMENT_LIMIT";

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_top_listing, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_top_listing_progressbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_top_listing_recyclerview);

        populateView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void populateView() {
        final Bundle args = getArguments();
        final int count = args.getInt(BUNDLE_ARGUMENT_COUNT);
        final int limit = args.getInt(BUNDLE_ARGUMENT_LIMIT);

        showProgress();
        ServiceFactory.getInstance().getRedditService().getTopListing(count, limit).enqueue(new Callback<TopListing>() {
            @Override
            public void onResponse(final Call<TopListing> call, final Response<TopListing> response) {
                hideProgress();

                final TopListingAdapter adapter = new TopListingAdapter(response.body());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setAdapter(adapter);
                adapter.getItemClicks().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                        if (URLUtil.isValidUrl(item.getUrl())) {
                            final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                            startActivity(browserIntent);
                        }
                    });
            }

            @Override
            public void onFailure(final Call<TopListing> call, final Throwable t) {
            }
        });
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }
}
