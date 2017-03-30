package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.egonzalez.simpleredditclient.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Bundle args = new Bundle();
        args.putInt(TopListingFragment.BUNDLE_ARGUMENT_COUNT, 0);
        args.putInt(TopListingFragment.BUNDLE_ARGUMENT_LIMIT, 50);

        final Fragment fragment = new TopListingFragment();
        fragment.setArguments(args);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.activity_main_fragment_container, fragment)
            .commit();
    }
}
