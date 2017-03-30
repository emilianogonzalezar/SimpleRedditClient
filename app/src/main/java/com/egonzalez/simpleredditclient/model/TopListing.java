
package com.egonzalez.simpleredditclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopListing implements Parcelable
{

    @SerializedName("data")
    @Expose
    private TopListingData data;
    public static final Creator<TopListing> CREATOR = new Creator<TopListing>() {

        @SuppressWarnings({"unchecked"})

        @Override
        public TopListing createFromParcel(final Parcel in) {
            final TopListing instance = new TopListing();
            instance.data = ((TopListingData) in.readValue((TopListingData.class.getClassLoader())));
            return instance;
        }

        @Override
        public TopListing[] newArray(final int size) {
            return (new TopListing[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public TopListing() {
    }

    public TopListing(final TopListingData data) {
        super();

        this.data = data;
    }

    public TopListingData getData() {
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
