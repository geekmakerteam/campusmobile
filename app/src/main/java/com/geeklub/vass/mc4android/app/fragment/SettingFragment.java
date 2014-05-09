package com.geeklub.vass.mc4android.app.fragment;



import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.utils.KFSettings;
import com.geeklub.vass.mc4android.app.R;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 */
public class SettingFragment extends Fragment implements OnClickListener{

	@InjectView(R.id.new_message_notification)
	 CheckBox new_message_notification;
	@InjectView(R.id.new_message_voice)
	 CheckBox new_message_voice;
	@InjectView(R.id.new_message_vibrate)
	 CheckBox new_message_vibrate;

	@InjectView(R.id.add_friends_reback_btn)
	 Button mBackBtn;

	private KFSettingsManager mSettingsMgr;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_msg_notification, null);
		ButterKnife.inject(this, view);
		mSettingsMgr = KFSettingsManager.getSettingsManager(getActivity());
		mBackBtn.setOnClickListener(this);
		new_message_notification.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_NOTIFICATION, true));
		new_message_voice.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_VOICE, true));
		new_message_vibrate.setChecked(mSettingsMgr.getBoolean(KFSettings.NEW_MESSAGE_VIBRATE, true));

		new_message_notification.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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

		new_message_voice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VOICE, isChecked);
			}
		});

		new_message_vibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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
