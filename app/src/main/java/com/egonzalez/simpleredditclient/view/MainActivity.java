package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View progressBar = findViewById(R.id.activity_main_progressbar);

        ServiceFactory.getInstance().getRedditService().getTopListing(0, 50).enqueue(new Callback<TopListing>() {
            @Override
            public void onResponse(final Call<TopListing> call, final Response<TopListing> response) {
                progressBar.setVisibility(View.GONE);

                final Bundle args = new Bundle();
                args.putParcelable(TopListingFragment.BUNDLE_ARGUMENT_ITEMS, response.body());

                final Fragment fragment = new TopListingFragment();
                fragment.setArguments(args);

                final FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment_container, fragment)
                    .commit();
            }

            @Override
            public void onFailure(final Call<TopListing> call, final Throwable t) {

            }
        });
    }
}
