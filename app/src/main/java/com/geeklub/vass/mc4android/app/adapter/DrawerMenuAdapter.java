package com.geeklub.vass.mc4android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.DrawerMenuItem;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by hp on 2014/4/26.
 */
public class DrawerMenuAdapter extends BaseAdapter {

    private List<DrawerMenuItem> mData;
    private LayoutInflater mInflater;

    public DrawerMenuAdapter(List<DrawerMenuItem> data,Context context){
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
            convertView = mInflater.inflate(R.layout.item_menu_drawer,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        DrawerMenuItem drawerMenu = (DrawerMenuItem) getItem(position);
        viewHolder.iv_icon.setImageResource(drawerMenu.getmIconRes());
        viewHolder.tv_name.setText(drawerMenu.getmNameRes());

        return convertView;
    }


    static class ViewHolder{

        @InjectView(R.id.icon)
        ImageView iv_icon;
        @InjectView(R.id.content)
        TextView tv_name;


        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
}
