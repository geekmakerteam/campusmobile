package com.geeklub.vass.mc4android.app.feedback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends Activity {

	private EditText mContactEdit = null;
	private EditText mContentEdit = null;
	private Button history_reback_btn = null;
	private Button mSubmitBtn = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void initView() {
    	mContactEdit = (EditText) findViewById(R.id.feedback_contact_edit);
    	mContentEdit = (EditText) findViewById(R.id.feedback_content_edit);
		history_reback_btn = (Button) findViewById(R.id.history_reback_btn);

		history_reback_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

    	mContentEdit.requestFocus();
    	

    	
    	mSubmitBtn = (Button) findViewById(R.id.submit_button);
    	mSubmitBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String content = mContentEdit.getText().toString().trim();
				String contact = mContactEdit.getText().toString().trim();
				if (content.equals("")) {
					Toast.makeText(FeedbackActivity.this, R.string.request_content, Toast.LENGTH_SHORT).show();
					return;
				}
				SendFeedbackTask task = new SendFeedbackTask(FeedbackActivity.this, content, contact);
				task.execute(MCRestClient.BASE_URL+ API.PUBIC);
				
			}
		});
    }

	private class SendFeedbackTask extends AsyncTask<Object, Object, Object> {

		private Context mContext = null;
		private String mContact = "";
		private String mContent = "";
		private ProgressDialog mProgDialog = null;

		public SendFeedbackTask(Context context, String content, String contact) {
			mContext = context;
			mContent = content;
			mContact = contact;
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

				params.add(new BasicNameValuePair("title",mContact));
				params.add(new BasicNameValuePair("content",mContent));

				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

				HttpResponse response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
				Toast.makeText(FeedbackActivity.this,
						R.string.feedback_success, Toast.LENGTH_SHORT);
				FeedbackActivity.this.finish();
			} else {
				Log.i("----ssss---", "4444");
				Toast.makeText(FeedbackActivity.this, R.string.feedback_failed,
						Toast.LENGTH_SHORT);
			}
			return;
		}

		@Override
		protected void onPreExecute() {
			mProgDialog = new ProgressDialog(FeedbackActivity.this);
			mProgDialog.setMessage(FeedbackActivity.this.getString(R.string.waiting));
			mProgDialog.setCancelable(false);
			mProgDialog.show();
		}

	}
}
