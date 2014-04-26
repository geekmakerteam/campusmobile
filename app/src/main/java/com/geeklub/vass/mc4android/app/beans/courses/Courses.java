package com.geeklub.vass.mc4android.app.beans.courses;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2014/4/20.
 */
public class Courses implements Parcelable{
    private String id;
    private String name;
    private String place;
    private String section;
    private String teacherName;
    private String week;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(place);
        dest.writeString(section);
        dest.writeString(teacherName);
        dest.writeString(week);
    }

    public final static Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel source) {

            Courses courses = new Courses();
            courses.setId(source.readString());
            courses.setName(source.readString());
            courses.setPlace(source.readString());
            courses.setSection(source.readString());
            courses.setTeacherName(source.readString());
            courses.setWeek(source.readString());

            return courses;
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


    @Override
    public String toString() {
        return name + "\n\n" +
                place + "\n\n"+
                section + "\n\n";
    }


}
