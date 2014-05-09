package com.geeklub.vass.mc4android.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.ui.entity.KFRosterEntity;
import com.appkefu.lib.utils.KFSLog;
import com.appkefu.lib.xmpp.XmppFriend;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.RosterListViewAdapter;
import com.umeng.analytics.MobclickAgent;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 
 * @author Edagar Li
 *
 */
public class RosterListActivity extends Activity implements OnClickListener{

	private Button mBackBtn;
	
	private ListView mListView;
	private List<KFRosterEntity> mRosterList = new ArrayList<KFRosterEntity>();
	private RosterListViewAdapter mRosterAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster_list);
		
		mBackBtn = (Button) findViewById(R.id.friends_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.roster_listView);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.friends_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		IntentFilter intentFilter = new IntentFilter(KFMainService.ACTION_XMPP_PRESENCE_CHANGED);
        registerReceiver(mXmppreceiver, intentFilter); 

	}
	
	@Override
	protected void onResume() {
		super.onResume();

		KFIMInterfaces.getFriends(this);

		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();

        unregisterReceiver(mXmppreceiver);
	}
	
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            if (action.equals(KFMainService.ACTION_XMPP_PRESENCE_CHANGED)) 
            {
            	
                int stateInt = intent.getIntExtra("state", XmppFriend.OFFLINE);
                
                String userId = intent.getStringExtra("userid");
                String name = intent.getStringExtra("name");//
                String status = intent.getStringExtra("status");//

                KFSLog.d("userId:"+userId
                		+" state:"+stateInt
                		+" name:"+name 
                		+" status:"+status);
                
                if(StringUtils.parseName(userId).length() > 0)
                {
            		KFRosterEntity entity = new KFRosterEntity();
            		entity.setJid(userId);
            		entity.setNick(name);
            		
            		if(!mRosterList.contains(entity))
            		{
                        mRosterList.add(entity);               		
            		} 
            		else
            		{
            			mRosterList.remove(entity);
            			mRosterList.add(entity);
            		}
            		
            		mRosterAdapter = new RosterListViewAdapter(RosterListActivity.this, mRosterList);
            		mListView.setAdapter(mRosterAdapter);
            		mRosterAdapter.notifyDataSetChanged();
                }
            }            
        }
    };

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}





















