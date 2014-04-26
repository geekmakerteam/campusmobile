package com.geeklub.vass.mc4android.app.beans.schoolnews;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2014/4/21.
 */
public class EachNews implements Parcelable{
    private String title;
    private String content;
    private String author;
    private String time;
    private String id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public final static Creator<EachNews> CREATOR = new Creator<EachNews>() {
        @Override
        public EachNews createFromParcel(Parcel source) {
            EachNews eachNews = new EachNews();

            eachNews.setAuthor(source.readString());
            eachNews.setId(source.readString());
            eachNews.setContent(source.readString());
            eachNews.setTitle(source.readString());
            eachNews.setTime(source.readString());

            return eachNews;
        }

        @Override
        public EachNews[] newArray(int size) {
            return new EachNews[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(title);
        dest.writeString(time);
    }


    @Override
    public String toString() {
        return "EachNews{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
