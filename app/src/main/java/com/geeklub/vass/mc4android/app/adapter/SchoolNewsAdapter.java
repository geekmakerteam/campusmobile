package com.geeklub.vass.mc4android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hp on 2014/4/21.
 */
public class SchoolNewsAdapter extends BaseAdapter {
    private List<EachNews> mData;
    private LayoutInflater mInflater;


    public SchoolNewsAdapter(List<EachNews> data, Context context){
        mData = data;
        mInflater =LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.item_news_school,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EachNews eachNews = (EachNews) getItem(position);
        viewHolder.tv_title.setText(eachNews.getTitle());
        viewHolder.tv_author.setText(eachNews.getAuthor());
        viewHolder.tv_time.setText(eachNews.getTime());

        return convertView;
    }


    static class ViewHolder{
        @InjectView(R.id.title)
        TextView tv_title;
        @InjectView(R.id.time)
        TextView tv_time;
        @InjectView(R.id.author)
        TextView tv_author;

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }




}
