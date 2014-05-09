package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.utils.KFSLog;
import com.geeklub.vass.mc4android.app.R;
import com.umeng.analytics.MobclickAgent;

import org.jivesoftware.smack.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class ResourceActivity extends Activity implements OnClickListener{

	private Button mBackBtn;

	private ListView mListView;

	List<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework);

		mBackBtn = (Button) findViewById(R.id.history_reback_btn);
		mBackBtn.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.history_listView);

		mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				String groupName = data.get(i);
				KFIMInterfaces.joinMUCRoom(ResourceActivity.this, groupName);
			}
		});
	}

	private List<String> getData(){
		data = new ArrayList<String>();
        data.add("形势与政治");
		return data;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.history_reback_btn:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		KFSLog.d("onStart");

		IntentFilter intentFilter = new IntentFilter();
		//监听消息
		intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
		registerReceiver(mXmppreceiver, intentFilter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		super.onStop();

		KFSLog.d("onStop");
		unregisterReceiver(mXmppreceiver);
	}

	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver()
	{
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))
			{
				String body = intent.getStringExtra("body");
				String from = StringUtils.parseName(intent.getStringExtra("from"));

				KFSLog.d("body:"+body+" from:"+from);
			}
		}
	};


	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}



























