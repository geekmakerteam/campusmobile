package com.geeklub.vass.mc4android.app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.Student;
import com.geeklub.vass.mc4android.app.utils.MCApplication;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hp on 2014/4/23.
 */
public class CallNamesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Student> mData;

    public CallNamesAdapter(List<Student> data,Context context){
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_names_call,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Student student = (Student) getItem(position);

        viewHolder.tv_name.setText(student.getUserName());
        viewHolder.tv_xuehao.setText(student.getXh());
        viewHolder.tv_status.setText(student.getStatus());
        viewHolder.tv_time.setText(student.getTime());

//        Animation in = AnimationUtils.loadAnimation(MCApplication.getApplication(),android.R.anim.fade_in);
//        Animation out = AnimationUtils.loadAnimation(MCApplication.getApplication(),android.R.anim.fade_out);
//
//        viewHolder.tv_switcher.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                TextView test = new TextView(MCApplication.getApplication());
//                test.setGravity(Gravity.CENTER);
//                test.setText("just test");
//                return test;
//            }
//        });
//
//
//        viewHolder.tv_switcher.setInAnimation(in);
//        viewHolder.tv_switcher.setInAnimation(out);


        return  convertView;
    }

    public void remove(int position) {
        if (!mData.isEmpty()) {
            mData.remove(position);
            notifyDataSetChanged();
        }
    }


    static class ViewHolder{
        @InjectView(R.id.name)
        TextView tv_name;
        @InjectView(R.id.xh)
        TextView tv_xuehao;
        @InjectView(R.id.status)
        TextView tv_status;
        @InjectView(R.id.time)
        TextView tv_time;
//        @InjectView(R.id.text_switcher)
//        TextSwitcher tv_switcher;



        public ViewHolder(View view){
            ButterKnife.inject(this,view);
        }





    }
}
