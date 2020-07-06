package com.jwhh.bookapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class Books implements Parcelable {
    public String id;
    public String title;
    public String subtitle;
    public String authors;
    public String publisher;
    public String publishedDate;
    public String description;
    public String thumbnail;

    public Books(String id, String title, String subtitle, String[] authors,
                 String publisher, String publishedDate, String description, String thumbnail) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = TextUtils.join(", \n", authors);
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    protected Books(Parcel in) {
        id = in.readString();
        title = in.readString();
        subtitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeString(thumbnail);
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (imageUrl == null) {
            view.setBackgroundResource(R.drawable.book_open_icon);
        } else {
            Picasso.get().load(imageUrl).into(view);
        }
    }
}
