package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.adapter.TopListingPagerAdapter;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class ViewPagerFragment extends Fragment {

    private static final String SAVEDINSTANCESTATE_TOP_LISTING = "SAVEDINSTANCESTATE_TOP_LISTING";
    private static final int MAX_ITEMS_PER_PAGE = 10;

    public static final String ARGUMENT_TOP_LISTING = "ARGUMENT_TOP_LISTING";

    private TopListing mTopListing;

    private ViewPager mViewPager;
    private PageIndicator mPageIndicator;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_view_pager, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.fragment_view_pager_viewpager);
        mPageIndicator = (TitlePageIndicator) v.findViewById(R.id.fragment_view_pager_pageindicator);

        // Restore state
        if (savedInstanceState != null) {
            mTopListing = savedInstanceState.getParcelable(SAVEDINSTANCESTATE_TOP_LISTING);

        } else {
            mTopListing = getArguments().getParcelable(ARGUMENT_TOP_LISTING);

        }

        return v;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SAVEDINSTANCESTATE_TOP_LISTING, mTopListing);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mTopListing != null) {
            mViewPager.setAdapter(new TopListingPagerAdapter(
                getChildFragmentManager(),
                MAX_ITEMS_PER_PAGE,
                mTopListing)
            );

            mPageIndicator.setViewPager(mViewPager);
        }
    }
}
