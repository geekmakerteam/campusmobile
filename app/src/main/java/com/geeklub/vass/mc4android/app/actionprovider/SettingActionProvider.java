package com.geeklub.vass.mc4android.app.actionprovider;

import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.utils.KFSettings;
import com.geeklub.vass.mc4android.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by justhacker on 8/05/14.
 */
public class SettingActionProvider extends ActionProvider implements View.OnClickListener
{
	@InjectView(R.id.new_message_notification)
	CheckBox new_message_notification;
	@InjectView(R.id.new_message_voice)
	CheckBox new_message_voice;
	@InjectView(R.id.new_message_vibrate)
	CheckBox new_message_vibrate;

	@InjectView(R.id.add_friends_reback_btn)
	Button mBackBtn;
	private Context context;
	private LayoutInflater inflater;
	private View view;
	private KFSettingsManager mSettingsMgr;
	public SettingActionProvider(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.activity_msg_notification, null);
		ButterKnife.inject(this, view);
		mSettingsMgr = KFSettingsManager.getSettingsManager(context);
	}

	@Override
	public View onCreateActionView() {
		mBackBtn.setOnClickListener(this);
		new_message_notification.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_NOTIFICATION, true));
		new_message_voice.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_VOICE, true));
		new_message_vibrate.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_VIBRATE, true));

		new_message_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_NOTIFICATION, isChecked);

				if(!isChecked)
				{
					new_message_voice.setChecked(false);
					new_message_vibrate.setChecked(false);
					new_message_voice.setVisibility(View.GONE);
					new_message_vibrate.setVisibility(View.GONE);
				}
				else
				{
					new_message_voice.setChecked(true);
					new_message_vibrate.setChecked(true);
					new_message_voice.setVisibility(View.VISIBLE);
					new_message_vibrate.setVisibility(View.VISIBLE);
				}
			}
		});

		new_message_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VOICE, isChecked);
			}
		});

		new_message_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VIBRATE, isChecked);
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

			case R.id.add_friends_reback_btn:
				break;

			default:
				break;
		}
	}
}
