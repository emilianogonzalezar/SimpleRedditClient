package com.egonzalez.simpleredditclient.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.model.TopListingData;
import com.egonzalez.simpleredditclient.model.TopListingItem;
import com.egonzalez.simpleredditclient.view.TopListingFragment;
import java.util.List;

public class TopListingPagerAdapter extends FragmentStatePagerAdapter {

    private final List<TopListingItem> mTopListingItems;
    private final int mItemsPerPage;

    public TopListingPagerAdapter(final FragmentManager fm, final int itemsPerPage, final TopListing topListing) {
        super(fm);

        mTopListingItems = topListing.getData().getChildren();
        mItemsPerPage = itemsPerPage;
    }

    @Override
    public Fragment getItem(final int position) {

        final List<TopListingItem> topListingSublist =
            mTopListingItems.subList(mItemsPerPage * position, mItemsPerPage * (position + 1));

        final Bundle args = new Bundle();
        args.putParcelable(TopListingFragment.BUNDLE_ARGUMENT_ITEMS, new TopListingData(topListingSublist));

        final Fragment fragment = new TopListingFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return mTopListingItems.size() / mItemsPerPage;
    }
}
