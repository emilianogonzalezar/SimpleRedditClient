package com.egonzalez.simpleredditclient.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.adapter.TopListingAdapter;
import com.egonzalez.simpleredditclient.model.TopListingData;
import com.egonzalez.simpleredditclient.model.TopListingItemData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TopListingFragment extends Fragment {

    public static final String BUNDLE_ARGUMENT_ITEMS = "BUNDLE_ARGUMENT_ITEMS";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_top_listing, container, false);

        final TopListingData topListingItems = getArguments().getParcelable(BUNDLE_ARGUMENT_ITEMS);
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_top_listing_recyclerview);

        if (topListingItems != null) {
            final TopListingAdapter adapter = new TopListingAdapter(topListingItems.getChildren());
            recyclerView.setAdapter(adapter);
            adapter.getItemClicks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopListingItemData>() {
                    @Override
                    public void accept(final TopListingItemData topListingItemData) throws Exception {
                        if (URLUtil.isValidUrl(topListingItemData.getUrl())) {
                            final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(topListingItemData.getUrl()));
                            startActivity(browserIntent);
                        }
                    }
                });
        }

        return view;
    }
}
