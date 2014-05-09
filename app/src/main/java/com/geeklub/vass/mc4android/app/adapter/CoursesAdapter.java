package com.geeklub.vass.mc4android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hp on 2014/4/20.
 */
public class CoursesAdapter extends BaseAdapter {
    private ArrayList<Courses> mData;
    private LayoutInflater mInflater;

    public  CoursesAdapter( ArrayList<Courses> data,Context context){
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null ){
            convertView = mInflater.inflate(R.layout.item_time_table_list,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Courses course = (Courses) getItem(position);
        viewHolder.tv_place.setText(course.getPlace());
        viewHolder.tv_section.setText(course.getSection());
        viewHolder.tv_name.setText(course.getName());
        viewHolder.tv_week.setText(course.getWeek());

        return convertView;
    }




    static class ViewHolder{

            @InjectView(R.id.name)
            TextView tv_name;
            @InjectView(R.id.place)
            TextView tv_place;
            @InjectView(R.id.section)
            TextView tv_section;
            @InjectView(R.id.week)
            TextView tv_week;

            public ViewHolder(View view){
                ButterKnife.inject(this, view);
            }
    }
}
