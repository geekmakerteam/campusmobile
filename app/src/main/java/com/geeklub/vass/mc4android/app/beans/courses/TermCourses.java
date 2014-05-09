package com.geeklub.vass.mc4android.app.beans.courses;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * Created by hp on 2014/4/21.
 */
public class TermCourses {
   private ArrayList<ArrayList<Courses>> course;

    @JSONField(name = "course")
    public ArrayList<ArrayList<Courses>> getCourse() {
        return course;
    }
    @JSONField(name = "course")
    public void setCourse(ArrayList<ArrayList<Courses>> courses) {
        this.course = courses;
    }


    @Override
    public String toString() {
        return "TermCourses{" +
                "courses=" + course +
                '}';
    }
}
