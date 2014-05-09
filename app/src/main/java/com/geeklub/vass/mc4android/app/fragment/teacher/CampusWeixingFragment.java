package com.geeklub.vass.mc4android.app.fragment.teacher;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFSLog;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.MainActivityAdapter;
import com.geeklub.vass.mc4android.app.adapter.SchoolNewsAdapter;
import com.geeklub.vass.mc4android.app.beans.ApiEntity;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.ui.AddFriendActivity;
import com.geeklub.vass.mc4android.app.ui.ChangePasswordActivity;
import com.geeklub.vass.mc4android.app.ui.GroupActivity;
import com.geeklub.vass.mc4android.app.ui.HistoryActivity;
import com.geeklub.vass.mc4android.app.ui.HomeworkActivity;
import com.geeklub.vass.mc4android.app.ui.LoginActivity;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.geeklub.vass.mc4android.app.ui.ProfileActivity;
import com.geeklub.vass.mc4android.app.ui.ProfileFriendActivity;
import com.geeklub.vass.mc4android.app.ui.RosterListActivity;
import com.geeklub.vass.mc4android.app.utils.FastJSONUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;

import org.jivesoftware.smack.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * Created by justhacker on 3/05/14.
 */
public class CampusWeixingFragment extends Fragment implements AdapterView.OnClickListener {

	//@InjectView(R.id.api_list_view)
	//ListView mApiListView;
	@InjectView(R.id.textView1)
	TextView textView1;
	@InjectView(R.id.textView2)
	TextView textView2;
	@InjectView(R.id.textView3)
	TextView textView3;
	@InjectView(R.id.textView4)
	TextView textView4;
	@InjectView(R.id.textView5)
	TextView textView5;
	@InjectView(R.id.textView6)
	TextView textView6;
	@InjectView(R.id.textView7)
	TextView textView7;
	@InjectView(R.id.textView8)
	TextView textView8;

	private List<ApiEntity> mApiArray= new ArrayList<ApiEntity>();
	private OnFragmentInteractionListener mListener;
	private AnimationAdapter animationAdapter;
	private MainActivityAdapter mAdapter;
	private KFSettingsManager mSettingsMgr;

	public static CampusWeixingFragment newInstance() {
		CampusWeixingFragment fragment = new CampusWeixingFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public CampusWeixingFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSettingsMgr = KFSettingsManager.getSettingsManager(getActivity());
		if (getArguments() != null) {
			mAdapter = new MainActivityAdapter(getActivity(),mApiArray);
	//      animationAdapter = new SwingLeftInAnimationAdapter(mAdapter);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tablelayout, null);
		ButterKnife.inject(this, view);

		//mApiListView.setOnItemClickListener(this);

		//animationAdapter.setAbsListView(mApiListView);
		//mApiListView.setAdapter(animationAdapter);
		return view;
	}

	@Override
	public void onStart() {

		super.onStart();

		initView();

		login();

		IntentFilter intentFilter = new IntentFilter();
		//监听网络连接变化情况
		intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
		//监听消息
		intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
		//监听添加好友/删除好友消息
		intentFilter.addAction(KFMainService.ACTION_XMPP_PRESENCE_SUBSCRIBE);
		//
		getActivity().registerReceiver(mXmppreceiver, intentFilter);

	}


	private void login()
	{
		//检查 用户名/密码 是否已经设置,如果已经设置，则登录
		if(!"".equals(mSettingsMgr.getUsername())
				&& !"".equals(mSettingsMgr.getPassword()))
			KFIMInterfaces.login(getActivity(), mSettingsMgr.getUsername(), mSettingsMgr.getPassword());
	}


	private void initView() {

		mApiArray = new ArrayList<ApiEntity>();

		mAdapter = new MainActivityAdapter(getActivity(),  mApiArray);
	//	mApiListView.setAdapter(mAdapter);

		ApiEntity entity = new ApiEntity(1, "注册");
		mApiArray.add(entity);
		entity = new ApiEntity(2, "登录");
		mApiArray.add(entity);
		/*entity = new ApiEntity(3, "聊天");
		mApiArray.add(entity);
		entity = new ApiEntity(4, "发送文本消息接口");
		mApiArray.add(entity);*/
		entity = new ApiEntity(5, "添加好友");
		mApiArray.add(entity);
		entity = new ApiEntity(6, "好友列表");
		mApiArray.add(entity);
		entity = new ApiEntity(7, "历史聊天记录");
		mApiArray.add(entity);
	//	entity = new ApiEntity(8, "会话消息记录");
	//	mApiArray.add(entity);
		entity = new ApiEntity(9, "个人资料信息");
		mApiArray.add(entity);
		entity = new ApiEntity(10, "其他用户信息");
		mApiArray.add(entity);
		entity = new ApiEntity(11, "修改密码");
		mApiArray.add(entity);
		entity = new ApiEntity(12, "群聊");
		mApiArray.add(entity);
		entity = new ApiEntity(13, "消息通知设置");
		mApiArray.add(entity);
		entity = new ApiEntity(14, "退出登录");
		mApiArray.add(entity);

		mAdapter.notifyDataSetChanged();


		textView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), AddFriendActivity.class);
				startActivity(intent);
			}
		});


		textView2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), RosterListActivity.class);
				startActivity(intent);
			}
		});

		textView3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity() ,HistoryActivity.class);
				startActivity(intent);
			}
		});

		textView4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), ProfileActivity.class);
				startActivity(intent);
			}
		});

		textView5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
				startActivity(intent);
			}
		});

		textView6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), GroupActivity.class);
			//	intent.putExtra("chatName", "");
				startActivity(intent);
			}
		});

		textView7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), HomeworkActivity.class);
				startActivity(intent);
			}
		});

		textView8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), GroupActivity.class);
				startActivity(intent);
			}
		});

	/*	mApiListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				// TODO Auto-generated method stub

				ApiEntity entity = mApiArray.get(index);
				Log.d("MainActivity", "OnItemClickListener:" + entity.getApiName());

				Intent intent;
				switch(entity.getId()) {
					case 1:
					//	intent = new Intent(MainActivity.this, RegisterActivity.class);
					//	startActivity(intent);
						break;
					case 2:
						intent = new Intent(getActivity(), LoginActivity.class);
						startActivity(intent);
						break;
					case 3:

					*//*	KFIMInterfaces.startChatWithUser(
								MainActivity.this,//上下文Context
								"admin",//对方用户名
								"自定义窗口标题");//自定义会话窗口标题
*//*
						break;
					case 4:
						//自定义：发送文本消息接口
						//KFIMInterfaces.sendTextMessage(String msgContent, String toUsername, String type, Context context)
						//其中：msgContent: 聊天消息内容，toUsername: 对方用户名，type:一对一聊天固定为"chat",不能修改
						//例如：给用户admin发送文本消息“自定义消息内容”为：

						//判断当前用户是否已经登录，true：已经登录，false：未登录
						if(!KFIMInterfaces.isLogin())
						{
			//				Toast.makeText(this, "未登录,不能发送消息", Toast.LENGTH_SHORT).show();
							return;
						}

					*//*	new AlertDialog.Builder(this)
								.setMessage("确定要发送自定义消息内容？")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,
											                    int which) {

										//		KFIMInterfaces.sendTextMessage(MainActivity.this, "自定义消息内容_android", "admin", "chat");
										//		Toast.makeText(MainActivity.this, "自定义消息已发送", Toast.LENGTH_SHORT).show();
											}
										})
								.setNegativeButton("取消", null)
								.create()
								.show();*//*
						break;
					case 5:
						intent = new Intent(getActivity(), AddFriendActivity.class);
						startActivity(intent);
						break;
					case 6:
						intent = new Intent(getActivity(), RosterListActivity.class);
						startActivity(intent);
						break;
					case 7:
						intent = new Intent(getActivity() ,HistoryActivity.class);
						startActivity(intent);
						break;
					case 8:
					*//*	intent = new Intent(MainActivity.this, MessageActivity.class);
						startActivity(intent);*//*
						break;
					case 9:
						intent = new Intent(getActivity(), ProfileActivity.class);
						startActivity(intent);
						break;
					case 10:
						//他人（"admin"）个人资料信息，实际操作时需要将admin替换为对方真实的用户名
						*//*intent = new Intent(MainActivity.this, ProfileFriendActivity.class);
						intent.putExtra("chatName", "admin");
						startActivity(intent);*//*
						break;
					case 11:
				*//*		intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
						startActivity(intent);*//*
						break;
					case 12:
					*//*	intent = new Intent(MainActivity.this, GroupChatActivity.class);
						startActivity(intent);*//*
						break;
					case 13:
						*//*intent = new Intent(MainActivity.this, MsgNotificationActivity.class);
						startActivity(intent);*//*
						break;
					case 14:
					//	logout();
						break;
					default:
						break;
				}

			}
		});*/
	}

	@Override
	public void onResume() {
		super.onResume();

		/*if(KFIMInterfaces.isLogin())
		{
			mApiArray.get(1).setApiName("登录("+mSettingsMgr.getUsername()+"已登录)");
		}
		else
		{
			mApiArray.get(1).setApiName("登录(未登录)");
		}
*/
	//	mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onStop() {
		super.onStop();

		getActivity().unregisterReceiver(mXmppreceiver);
	}



	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver()
	{
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();

			if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED))
			{
				updateStatus(intent.getIntExtra("new_state", 0));
			}
			else if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))
			{
				//其中: body为消息内容，from为发消息者的用户名
				String body = intent.getStringExtra("body");
				String from = StringUtils.parseName(intent.getStringExtra("from"));

				KFSLog.d("body:" + body + " from:" + from);
			}
			else if(action.equals(KFMainService.ACTION_XMPP_PRESENCE_SUBSCRIBE))
			{
				String type = intent.getStringExtra("type");
				String from = StringUtils.parseName(intent.getStringExtra("from"));

				KFSLog.d("type:"+type+" from:"+from);

				if(type.equals("subscribe"))
				{
					KFSLog.d(from+" 请求添加您为好友");
				}
				else if(type.equals("subscribed"))
				{
					KFSLog.d(from+" 同意您的好友请求");
				}
				else if(type.equals("unsubscribed"))
				{
					KFSLog.d(from +" 拒绝您的好友请求");
				}
			}

		}
	};


	//根据监听到的连接变化情况更新界面显示
	private void updateStatus(int status) {

		switch (status) {
			case KFXmppManager.CONNECTED:
				mApiArray.get(1).setApiName("登录("+mSettingsMgr.getUsername()+"已登录)");
				break;
			case KFXmppManager.DISCONNECTED:
				mApiArray.get(1).setApiName("登录(未登录)");
				break;
			case KFXmppManager.CONNECTING:
				mApiArray.get(1).setApiName("登录(未登录)");
				break;
			case KFXmppManager.DISCONNECTING:
				mApiArray.get(1).setApiName("登录(未登录)");
				break;
			case KFXmppManager.WAITING_TO_CONNECT:
			case KFXmppManager.WAITING_FOR_NETWORK:
				mApiArray.get(1).setApiName("登录(未登录)");
				break;
			default:
				throw new IllegalStateException();
		}

		mAdapter.notifyDataSetChanged();
	}



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			((MainActivity) getActivity()).onSectionAttached(R.string.campus_weixing);

			mListener = (OnFragmentInteractionListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(ApiEntity entity);
	}


	@Override
	public void onClick(View view) {

	}
}
