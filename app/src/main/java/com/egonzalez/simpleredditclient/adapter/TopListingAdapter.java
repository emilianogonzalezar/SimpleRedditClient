package com.egonzalez.simpleredditclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.model.TopListingItemData;

public class TopListingAdapter extends RecyclerView.Adapter<TopListingAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(final View view) {
            super(view);
        }
    }

    private final TopListing mTopListing;

    public TopListingAdapter(final TopListing topListing) {
        mTopListing = topListing;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_listing_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final View v = holder.itemView;

        final TopListingItemData itemData = mTopListing.getData().getChildren().get(position).getData();

        final TextView textView = (TextView) v.findViewById(R.id.top_listing_item_title);
        textView.setText(itemData.getTitle());
    }

    @Override
    public int getItemCount() {
        return mTopListing.getData().getChildren().size();
    }
}
