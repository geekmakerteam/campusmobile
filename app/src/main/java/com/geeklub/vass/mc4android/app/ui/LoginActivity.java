package com.geeklub.vass.mc4android.app.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.common.APIParams;
import com.geeklub.vass.mc4android.app.utils.LoginUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
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


    private String userName;
    private String passWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        initView();
    }

    private void initView() {
        btn_login.setOnClickListener(this);
    }


    public void login() {
        userName = et_userName.getText().toString();
        passWord = et_passWord.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            SuperToast.create(MCApplication.getApplication(), "密码或用户名不能为空...", SuperToast.Duration.SHORT,
                    Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();
        } else {
            verify();
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
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }
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
        login();
    }


    private void showSuperToast(){
        SuperToast.create(this, "登陆成功...", SuperToast.Duration.SHORT,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
    }


}
