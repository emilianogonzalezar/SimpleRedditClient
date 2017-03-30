package com.egonzalez.simpleredditclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.egonzalez.simpleredditclient.R;
import com.egonzalez.simpleredditclient.model.TopListing;
import com.egonzalez.simpleredditclient.model.TopListingItemData;
import com.squareup.picasso.Picasso;
import java.util.Calendar;
import java.util.TimeZone;
import org.ocpsoft.prettytime.PrettyTime;

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

        if (itemData.getThumbnail() != null) {
            final ImageView imageView = (ImageView) v.findViewById(R.id.top_listing_item_thumbnail);

            Picasso.with(v.getContext().getApplicationContext())
                .load(itemData.getThumbnail())
                .into(imageView);

            imageView.setVisibility(View.VISIBLE);
        }

        final TextView title = (TextView) v.findViewById(R.id.top_listing_item_title);
        title.setText(itemData.getTitle());

        final Calendar created = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        created.setTimeInMillis(itemData.getCreatedUtc().longValue() * 1000L);

        final String authorName = itemData.getAuthor();
        final String authorAndDateText = v.getContext().getString(
            R.string.author_and_date,
            new PrettyTime().format(created),
            authorName);

        final TextView authorAndDate = (TextView) v.findViewById(R.id.top_listing_item_author);
        authorAndDate.setText(authorAndDateText);

        final String commentsNumberText = v.getContext().getString(R.string.comments_number, itemData.getNumComments());

        final TextView commentsNumber = (TextView) v.findViewById(R.id.top_listing_item_comments_number);
        commentsNumber.setText(commentsNumberText);
    }

    @Override
    public int getItemCount() {
        return mTopListing.getData().getChildren().size();
    }
}
