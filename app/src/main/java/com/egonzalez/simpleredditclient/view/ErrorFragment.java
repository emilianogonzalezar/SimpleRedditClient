package com.egonzalez.simpleredditclient.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.egonzalez.simpleredditclient.R;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ErrorFragment extends Fragment {

    private final PublishSubject<Fragment> onClickSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_error, container, false);

        final Button retryButton = (Button) view.findViewById(R.id.fragment_error_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickSubject.onNext(ErrorFragment.this);
            }
        });

        return view;
    }

    public Observable<Fragment> getRetryButtonClicked() {
        return onClickSubject;
    }
}
