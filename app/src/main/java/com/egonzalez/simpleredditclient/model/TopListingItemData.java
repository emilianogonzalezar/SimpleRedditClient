
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
    @SerializedName("created")
    @Expose
    private Double created;
    @SerializedName("created_utc")
    @Expose
    private Double createdUtc;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("permalink")
    @Expose
    private String permalink;

    public static final Creator<TopListingItemData> CREATOR = new Creator<TopListingItemData>() {

        @SuppressWarnings({"unchecked"})

        public TopListingItemData createFromParcel(final Parcel in) {
            final TopListingItemData instance = new TopListingItemData();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.author = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
            instance.numComments = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.created = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.createdUtc = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            instance.permalink = ((String) in.readValue((String.class.getClassLoader())));
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
        final Double created,
        final Double createdUtc,
        final String url,
        final String permalink) {

        super();
        this.title = title;
        this.author = author;
        this.numComments = numComments;
        this.thumbnail = thumbnail;
        this.created = created;
        this.createdUtc = createdUtc;
        this.url = url;
        this.permalink = permalink;
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

    public Double getCreated() {
        return created;
    }

    public Double getCreatedUtc() {
        return createdUtc;
    }

    public String getUrl() {
        return url;
    }

    public String getPermalink() {
        return permalink;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeValue(title);
        dest.writeValue(author);
        dest.writeValue(numComments);
        dest.writeValue(thumbnail);
        dest.writeValue(created);
        dest.writeValue(createdUtc);
        dest.writeValue(url);
        dest.writeValue(permalink);
    }

    @Override
    public int describeContents() {
        return  0;
    }

}
