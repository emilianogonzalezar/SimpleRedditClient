
package com.egonzalez.simpleredditclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopListingItemData implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("num_comments")
    @Expose
    private Integer numComments;
    @SerializedName("created_utc")
    @Expose
    private Double createdUtc;
    @SerializedName("url")
    @Expose
    private String url;

    public static final Creator<TopListingItemData> CREATOR = new Creator<TopListingItemData>() {

        @SuppressWarnings({"unchecked"})

        public TopListingItemData createFromParcel(final Parcel in) {
            final TopListingItemData instance = new TopListingItemData();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.author = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
            instance.numComments = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.createdUtc = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public TopListingItemData[] newArray(final int size) {
            return (new TopListingItemData[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     */
    public TopListingItemData() {
    }

    public TopListingItemData(
        final String title,
        final String author,
        final Integer numComments,
        final String thumbnail,
        final Double createdUtc,
        final String url) {

        super();
        this.title = title;
        this.author = author;
        this.numComments = numComments;
        this.thumbnail = thumbnail;
        this.createdUtc = createdUtc;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Double getCreatedUtc() {
        return createdUtc;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeValue(title);
        dest.writeValue(author);
        dest.writeValue(thumbnail);
        dest.writeValue(numComments);
        dest.writeValue(createdUtc);
        dest.writeValue(url);
    }

    @Override
    public int describeContents() {
        return  0;
    }

}
