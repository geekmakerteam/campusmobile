package com.geeklub.vass.mc4android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.classnews.ClassNews;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hp on 2014/4/20.
 */
public class ClassNewsAdapter extends BaseAdapter {
    private List<ClassNews> mData;
    private LayoutInflater mInflater;
    private List<Integer> mBackgroundColors = new ArrayList<Integer>();


    public ClassNewsAdapter(List<ClassNews> data, Context context) {
        mData = data;
        mInflater = LayoutInflater.from(context);

        mBackgroundColors.add(R.color.ALIZARIN);
        mBackgroundColors.add(R.color.ORANGE);
        mBackgroundColors.add(R.color.BELIZE_HOLE);
        mBackgroundColors.add(R.color.CONCRETE);
        mBackgroundColors.add(R.color.CARROT);
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_news_class, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        ClassNews classNews = (ClassNews) getItem(position);
        viewHolder.tv_title.setText(classNews.getTitle());
        viewHolder.tv_content.setText(classNews.getContent());
        viewHolder.tv_author.setText(classNews.getAuthor());
        viewHolder.tv_time.setText(classNews.getTime());

        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));


        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.title)
        TextView tv_title;
        @InjectView(R.id.content)
        TextView tv_content;
        @InjectView(R.id.time)
        TextView tv_time;
        @InjectView(R.id.author)
        TextView tv_author;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
