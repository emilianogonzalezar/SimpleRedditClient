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

    public static final String ARGUMENT_TOP_LISTING = "ARGUMENT_TOP_LISTING";

    private static final int MAX_ITEMS_PER_PAGE = 10;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_view_pager, container, false);

        final TopListing topListing = getArguments().getParcelable(ARGUMENT_TOP_LISTING);
        if (topListing != null) {
            final ViewPager viewPager = (ViewPager) v.findViewById(R.id.fragment_view_pager_viewpager);
            final PageIndicator pageIndicator =
                (TitlePageIndicator) v.findViewById(R.id.fragment_view_pager_pageindicator);

            viewPager.setAdapter(new TopListingPagerAdapter(
                getChildFragmentManager(),
                MAX_ITEMS_PER_PAGE,
                topListing)
            );

            pageIndicator.setViewPager(viewPager);
        }

        return v;
    }
}
