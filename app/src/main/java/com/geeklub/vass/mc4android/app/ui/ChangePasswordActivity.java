package com.geeklub.vass.mc4android.app.ui;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFSettingsManager;
import com.geeklub.vass.mc4android.app.R;
import com.umeng.analytics.MobclickAgent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;

/**
 * 
 * @author Administrator
 *
 */
public class ChangePasswordActivity extends Activity implements OnClickListener{

	private EditText mOldPassword;
	private EditText mPassword; //
	private EditText mRePassword;

	private Button mChangePswBtn;
	private Button mBackBtn;
	
	private String oldpassword;
	private String password;
	private String repassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		mOldPassword = (EditText) findViewById(R.id.change_password_edit);
		mPassword = (EditText) findViewById(R.id.change_new_passwd_edit);
		mRePassword = (EditText) findViewById(R.id.re_change_new_passwd_edit);
		
		mChangePswBtn = (Button) findViewById(R.id.change_change_btn);
		mChangePswBtn.setOnClickListener(this);
		mBackBtn = (Button) findViewById(R.id.change_reback_btn);
		mBackBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.change_change_btn:
			changePassword();
			break;
		case R.id.change_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}

	public void changePassword()
	{
		oldpassword = mOldPassword.getText().toString();
		password = mPassword.getText().toString();
		repassword = mRePassword.getText().toString();


		if ("".equals(oldpassword)
				|| "".equals(password)
				|| "".equals(repassword))// 判断 帐号和密码
		{
			new AlertDialog.Builder(ChangePasswordActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("错误").setMessage("不能为空")
					.create().show();
		}
		else if(!oldpassword.equals(KFSettingsManager.getSettingsManager(this).getPassword()))
		{
			Toast.makeText(this, "原密码不正确", Toast.LENGTH_SHORT).show();
		}
		else if(!password.equals(repassword))
		{
			new AlertDialog.Builder(ChangePasswordActivity.this)
					.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
					.setTitle("错误").setMessage("两次输入的密码不一致!")
					.create().show();
		}
		else
		{
			mChangePswBtn.setEnabled(false);

			//修改密码
			//changeThread();
			KFIMInterfaces.changePassword(password);

			mChangePswBtn.setEnabled(true);
		}
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











