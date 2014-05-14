package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.utils.KFSLog;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jivesoftware.smack.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 
 * @author Administrator
 *
 */
public class HomeworkRemarkActivity extends Activity implements OnClickListener{

	@InjectView(R.id.reback_btn)
	Button mBackBtn;

	@InjectView(R.id.right_btn)
	Button remarkButton;

	@InjectView(R.id.homework_score)
	EditText homeEditText;

	public String filename="";
	public String author="";
	public String coursename="";
	public String teacherName="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remark);

		ButterKnife.inject(this);

		mBackBtn.setOnClickListener(this);

		remarkButton.setOnClickListener(this);

		Intent intent=getIntent();
		filename=intent.getStringExtra("filename");
		Log.w("filename",filename);
		author=intent.getStringExtra("author");
		Log.w("author",author);
		coursename=intent.getStringExtra("courename");
		Log.w("courename",coursename);
		teacherName=intent.getStringExtra("teacherName");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.reback_btn:
				finish();
				break;
			case R.id.right_btn:
                if(("").equals(homeEditText.getText().toString()))
                {
	                Toast.makeText(HomeworkRemarkActivity.this,"评分不能为空!",Toast.LENGTH_SHORT);
	               break;
                }
				SendbackTask task = new SendbackTask(HomeworkRemarkActivity.this,filename,author,coursename,teacherName,homeEditText.getText().toString());
				task.execute(MCRestClient.BASE_URL+ API.REMARK);
				break;
			default:
				break;
		}
	}

	private class SendbackTask extends AsyncTask<Object, Object, Object> {

		private Context mContext = null;
		private String mfilename="";
		private String mauthor="";
		private String mcoursename="";
		private String mteacherName="";
		private String mscore="";
		private ProgressDialog mProgDialog = null;

		public SendbackTask(Context context, String filename, String author,String coursename,
		                        String teacherName,String score)
		{
			mContext = context;
            mfilename=filename;
			mauthor=author;
			mcoursename=coursename;
			mteacherName=teacherName;
			mscore=score;
		}

		@Override
		protected Object doInBackground(Object... arg0) {
			Log.i("----ssss---", "doInBackground(Params... params) called");
			try {
				DefaultHttpClient client = new DefaultHttpClient();
				//	HttpGet get = new HttpGet(params[0]);
				HttpPost post=new HttpPost(arg0[0].toString());
				PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
				//asyncHttpClient.setCookieStore(cookieStore);
				client.setCookieStore(cookieStore);

				List<NameValuePair> params=new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("fileName",mfilename));
				Log.w("filename",mfilename);
				params.add(new BasicNameValuePair("author",mauthor));
				params.add(new BasicNameValuePair("courename",mcoursename));
				Log.w("courename",mcoursename);
				params.add(new BasicNameValuePair("teacherName",mteacherName));
				params.add(new BasicNameValuePair("score",mscore));

				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

				HttpResponse response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					Log.i("----ssss---", "111");
					return Integer.valueOf(0);
				}
				else
				{
					Log.i("----ssss---", "2222");
					return Integer.valueOf(1);
				}
			} catch (Exception e) {
				Log.e("---sss---", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			if (mProgDialog != null) {
				mProgDialog.dismiss();
			}
			int resultCode = ((Integer) result).intValue();
			if (resultCode == 0) {
				Log.i("----ssss---", "333");
				Toast.makeText(HomeworkRemarkActivity.this,
						R.string.remark_success, Toast.LENGTH_SHORT);
				HomeworkRemarkActivity.this.finish();
			} else {
				Log.i("----ssss---", "4444");
				Toast.makeText(HomeworkRemarkActivity.this, R.string.remark_failed,
						Toast.LENGTH_SHORT);
			}
			return;
		}

		@Override
		protected void onPreExecute() {
			mProgDialog = new ProgressDialog(HomeworkRemarkActivity.this);
			mProgDialog.setMessage(HomeworkRemarkActivity.this.getString(R.string.waiting));
			mProgDialog.setCancelable(false);
			mProgDialog.show();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}



	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}



























