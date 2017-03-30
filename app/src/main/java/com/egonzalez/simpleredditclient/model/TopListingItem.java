
package com.egonzalez.simpleredditclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopListingItem implements Parcelable
{
    @SerializedName("data")
    @Expose
    private TopListingItemData data;
    public static final Creator<TopListingItem> CREATOR = new Creator<TopListingItem>() {

        @SuppressWarnings({"unchecked"})

        public TopListingItem createFromParcel(final Parcel in) {
            final TopListingItem instance = new TopListingItem();
            instance.data = ((TopListingItemData) in.readValue((TopListingItemData.class.getClassLoader())));
            return instance;
        }

        public TopListingItem[] newArray(final int size) {
            return (new TopListingItem[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public TopListingItem() {
    }

    public TopListingItem(final TopListingItemData data) {
        super();

        this.data = data;
    }

    public TopListingItemData getData() {
        return data;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeValue(data);
    }

    @Override
    public int describeContents() {
        return  0;
    }

}
