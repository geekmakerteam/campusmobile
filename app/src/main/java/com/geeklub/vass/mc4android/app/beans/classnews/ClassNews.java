package com.geeklub.vass.mc4android.app.beans.classnews;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2014/4/20.
 */
public class ClassNews implements Parcelable{
    private String author;
    private String id;
    private String content;
    private String title;
    private String time;

    public final static Creator<ClassNews> CREATOR = new Creator<ClassNews>() {
        @Override
        public ClassNews createFromParcel(Parcel source) {
            ClassNews classNews = new ClassNews();
            classNews.setAuthor(source.readString());
            classNews.setId(source.readString());
            classNews.setContent(source.readString());
            classNews.setTitle(source.readString());
            classNews.setTime(source.readString());
            return classNews;
        }

        @Override
        public ClassNews[] newArray(int size) {
            return new ClassNews[size];
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


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "标题：" + title + "\n\n" +
                "内容:" + content + "\n\n" +
                "作者:" + author + "\n\n" +
                "发布时间:" + time + "\n\n";
    }


}
