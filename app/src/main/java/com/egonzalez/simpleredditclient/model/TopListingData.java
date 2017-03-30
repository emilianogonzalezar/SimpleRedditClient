
package com.egonzalez.simpleredditclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TopListingData implements Parcelable
{
    @SerializedName("children")
    @Expose
    private List<TopListingItem> children = null;
    public static final Creator<TopListingData> CREATOR = new Creator<TopListingData>() {

        @SuppressWarnings({"unchecked"})

        @Override
        public TopListingData createFromParcel(final Parcel in) {
            final TopListingData instance = new TopListingData();
            in.readList(instance.children, (TopListingItem.class.getClassLoader()));
            return instance;
        }

        @Override
        public TopListingData[] newArray(final int size) {
            return (new TopListingData[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public TopListingData() {
    }

    public TopListingData(final List<TopListingItem> children) {
        super();
        this.children = children;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeList(children);
    }

    @Override
    public int describeContents() {
        return  0;
    }

}
