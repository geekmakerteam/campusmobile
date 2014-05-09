package com.geeklub.vass.mc4android.app.feedback;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geeklub.vass.mc4android.app.R;

public class ListAdapter extends BaseAdapter {

	private ArrayList<ListItem> mItems = null;

	private Context mContext;

	public ListAdapter(Context context) {
		super();
		mContext = context;
		mItems = new ArrayList<ListItem>();
	}
	
	public ArrayList<ListItem> getItems() {
		return mItems;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem item = (ListItem) getItem(position);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.contentText = (TextView) convertView
					.findViewById(R.id.content_text);
			holder.timeText = (TextView) convertView
					.findViewById(R.id.time_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.contentText.setText(item.mContent);
		holder.timeText.setText(item.mDateTime);

		return convertView;
	}

	private class ViewHolder {
		TextView contentText;
		TextView timeText;
	}

}
