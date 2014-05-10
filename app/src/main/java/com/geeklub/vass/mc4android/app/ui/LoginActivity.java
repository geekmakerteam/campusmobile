package com.geeklub.vass.mc4android.app.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFSLog;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.UserPassword;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.common.APIParams;
import com.geeklub.vass.mc4android.app.utils.LoginUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.geeklub.vass.mc4android.app.utils.SharedPreferencesUtils;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import org.json.JSONObject;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 登陆的Activity
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    @InjectView(R.id.username_edit)
    EditText et_userName;
    @InjectView(R.id.password_edit)
    EditText et_passWord;
    @InjectView(R.id.login_btn)
    Button btn_login;
	@InjectView(R.id.reset_btn)
	Button reset_btn;

    private String userName;
    private String passWord;

	private KFSettingsManager mSettingsMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

	    mSettingsMgr = KFSettingsManager.getSettingsManager(this);
	   // 设置开发者调试模式，默认为true，如要关闭开发者模式，请设置为false
	    KFIMInterfaces.enableDebugMode(this, true);

        ButterKnife.inject(this);

        initView();
	    MobclickAgent.onError(this);
	   // UmengUpdateAgent.setUpdateOnlyWifi(false);

	    final Context mContext=this;
	    UmengUpdateAgent.setUpdateAutoPopup(false);
	    // 设置更新回调，自主处理更新
	    UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

		    @Override
		    public void onUpdateReturned(int updateStatus,
		                                 UpdateResponse updateInfo) {
			    switch (updateStatus) {
				    case UpdateStatus.Yes: // 检测到有更新
				//	    if(parseResponse(mContext)==null)
				//	    {
						    UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
				//		    saveResponse(mContext,updateInfo);
				//	    }
					    break;
				    case UpdateStatus.No: // 检测到没有更新
					    //Log.d("ssssssss------",updateInfo.version);
					    break;
				    case UpdateStatus.NoneWifi: // 当前不是Wifi环境
					    Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新",
							    Toast.LENGTH_SHORT).show();
					    break;
				    case UpdateStatus.Timeout: // 检测超时
					    Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
					    break;
			    }
		    }
	    });
	    UmengUpdateAgent.update(this);
    }
	private void saveResponse(Context context,UpdateResponse updateInfo){
		SharedPreferences update = context.getSharedPreferences("um_update_info", MODE_PRIVATE);
		if( update != null ) {
			update.edit().putString("serial_update_info", updateInfo.toString()).commit();
		}
	}

	private UpdateResponse parseResponse(Context context){

		SharedPreferences update = context.getSharedPreferences("um_update_info", MODE_PRIVATE);
		JSONObject json = null;

		if(update != null ){
			try{
				json = new JSONObject(update.getString("serial_update_info", null));
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return new UpdateResponse( json );
	}



	@Override
	protected void onStart() {
		super.onStart();
		KFSLog.d("onStart");

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
		registerReceiver(mXmppreceiver, intentFilter);

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
			if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED))
			{
				updateStatus(intent.getIntExtra("new_state", 0));
			}

		}
	};

	private void updateStatus(int status) {
		switch (status) {
			case KFXmppManager.CONNECTED:
				KFSLog.d("登录成功");
				finish();
				break;
			case KFXmppManager.DISCONNECTED:
				KFSLog.d("未登录");
				btn_login.setEnabled(true);
				break;
			case KFXmppManager.CONNECTING:
				KFSLog.d("登录中");
				break;
			case KFXmppManager.DISCONNECTING:
				KFSLog.d("登出中");
				break;
			case KFXmppManager.WAITING_TO_CONNECT:
			case KFXmppManager.WAITING_FOR_NETWORK:
				KFSLog.d("waiting to connect");
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void initView() {
        btn_login.setOnClickListener(this);
		reset_btn.setOnClickListener(this);
    }


    public void login() {

	    UserPassword userPassword = SharedPreferencesUtils
			    .readSharedPreferences(getApplicationContext());


	    if (userPassword != null) {
		    et_userName.setText(userPassword.getUserName());
		    et_passWord.setText(userPassword.getPassword());
	    }


        userName = et_userName.getText().toString();
        passWord = et_passWord.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            SuperToast.create(MCApplication.getApplication(), "密码或用户名不能为空...", SuperToast.Duration.SHORT,
                    Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();
        } else {
	        btn_login.setEnabled(false);

	        SuperToast.create(MCApplication.getApplication(), "正在登录...", SuperToast.Duration.SHORT,
			        Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();

	        verify();


	        KFIMInterfaces.login(this, userName, passWord);
        }
    }


    //登陆验证身份
    private void verify() {
        MCRestClient.post(API.LOGIN, new APIParams().with("username", userName).with("password", passWord), new JsonHttpResponseHandler() {
                @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                try {
                    if (response.getBoolean("status")) {
                        finish();

                        showSuperToast();
                        LoginUtil.setUserName(MCApplication.getApplication(), userName);
	                    SharedPreferencesUtils.writeSharedPreferences(
			                    getApplicationContext(), userName, passWord);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
	                else
                    {
	                    SuperToast.create(MCApplication.getApplication(), "用户名或密码错误...", SuperToast.Duration.SHORT,
			                    Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();
	                    btn_login.setEnabled(true);
                    }
                } catch (Exception e) {
	                SuperToast.create(MCApplication.getApplication(), "网络错误...", SuperToast.Duration.SHORT,
			                Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();
	                btn_login.setEnabled(true);
	                e.printStackTrace();
                }
            }

	        @Override
	        public void onFailure(Throwable e, JSONObject errorResponse) {
		        super.onFailure(e, errorResponse);
		        btn_login.setEnabled(true);
		        SuperToast.create(MCApplication.getApplication(), "网络错误....", SuperToast.Duration.SHORT,
				        Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();
	        }


        }, MCApplication.getApplication());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
       switch(v.getId())
       {
	       case R.id.reset_btn:
		      et_userName.setText("");
		      et_passWord.setText("");
              break;
	       case R.id.login_btn:
		       login();
		       break;
	       default:
		       break;
       }
    }


    private void showSuperToast(){
        SuperToast.create(this, "登陆成功...", SuperToast.Duration.SHORT,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
    }

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
