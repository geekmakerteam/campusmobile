package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.HomeworkFileAdapter;
import com.geeklub.vass.mc4android.app.beans.RemarkScore;
import com.geeklub.vass.mc4android.app.beans.UserPassword;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.utils.FastJSONUtil;
import com.geeklub.vass.mc4android.app.utils.FileUtil;
import com.geeklub.vass.mc4android.app.utils.FilterUtil;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.geeklub.vass.mc4android.app.utils.SharedPreferencesUtils;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 
 * @author Administrator
 *
 */
public class HomeworkFileDetailActivity extends Activity implements OnClickListener{

	@InjectView(R.id.reback_btn)
	Button mBackBtn;

    @InjectView(R.id.right_btn)
    Button remarkButton;

	@InjectView(R.id.filename)
	TextView fileName;

	@InjectView(R.id.size)
	TextView size;

	@InjectView(R.id.fileauthor)
	TextView fileauthor;

	@InjectView(R.id.button)
	Button downloadButton;

    @InjectView(R.id.fileonline)
    TextView fileonline;

	@InjectView(R.id.user_title)
	TextView userTitle;

	@InjectView(R.id.score)
    TextView scoreTextView;

	public String webviewUrl;

	private WebView webview;

	public String allurl="";

	private Handler handler = new Handler();




	private class SendbackTask extends AsyncTask<Object, Object, Object> {

		private Context mContext = null;
		private String mfilename="";
		private String mauthor="";
		private String mcoursename="";
		private String mteacherName="";
		private ProgressDialog mProgDialog = null;


		public SendbackTask(Context context, String filename, String author,String coursename,
		                    String teacherName)
		{
			mContext = context;
			mfilename=filename;
			mauthor=author;
			mcoursename=coursename;
			mteacherName=teacherName;
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

				params.add(new BasicNameValuePair("fileName", mfilename));
				Log.w("filename", mfilename);
				params.add(new BasicNameValuePair("author",mauthor));
				params.add(new BasicNameValuePair("courename", mcoursename));
				Log.w("courename", mcoursename);
				params.add(new BasicNameValuePair("teacherName",mteacherName));

				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

				HttpResponse response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					Log.w("----ssss---", "111");
					HttpEntity http=response.getEntity();
					//Log.w("----httpEntity---", http.);
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					long total = entity.getContentLength();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int count = 0;
					int length = -1;
					while ((length = is.read(buf)) != -1) {
						baos.write(buf, 0, length);
					}
					Log.w("----pppppppp---",new String(baos.toByteArray(), "gb2312"));

					String ss=new String(baos.toByteArray(), "gb2312");
					RemarkScore rs=FastJSONUtil.getObject(ss,RemarkScore.class);

					if(rs.getStatus())
					{
						scoreTextView.setText("得分:"+rs.getDatas());
						return Integer.valueOf(0);
					}
                    else
					{
						return Integer.valueOf(1);
					}

				}
				else
				{
					Log.i("----ssss---", "2222");
					return Integer.valueOf(1);
				}
			} catch (Exception e) {
				Log.e("---sss---", e.getMessage());
				return Integer.valueOf(1);
			}
		}

		@Override
		protected void onPostExecute(Object result) {

			int resultCode = ((Integer) result).intValue();
			if (resultCode == 0) {
				Log.w("----ssss---", "333");


			} else {
				Log.w("----ssss---", "4444");

			}
			return;
		}

		@Override
		protected void onPreExecute() {

		}

	}


	private Runnable runnable=new Runnable() {
		@Override
		public void run() {
			this.update();
			handler.postDelayed(this, 1000 * 1);// 间隔120秒
		}
		void update() {
			//刷新msg的内容
			Log.w("sqqqqq","--------------");
			SendbackTask task = new SendbackTask(HomeworkFileDetailActivity.this,fileName.getText().toString(),
					fileauthor.getText().toString().substring(4),allurl,"xiaowang");
			task.execute(MCRestClient.BASE_URL+ API.GETREMARK);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_homeworkfile_detail);
		ButterKnife.inject(this);

		mBackBtn.setOnClickListener(this);
		remarkButton.setOnClickListener(this);
		downloadButton.setOnClickListener(this);
        fileonline.setOnClickListener(this);
		loadData();
		runOnUiThread(runnable);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	private void loadData() {

		String filename = getIntent().getStringExtra("filename");

		//object="/"+object+"/"+url.split("/",0)[url.split("/",0).length-1]
		FileUtil.object=filename;
		allurl=filename.split("/",0)[1];
		filename=filename.split("/",0)[2];


		Log.i("==filename==",filename);

		String filesize = getIntent().getStringExtra("filesize");

		userTitle.setText("");
		fileName.setText(filename);
		size.setText(filesize);

		BCSCredentials credentials = new BCSCredentials(FileUtil.accessKey, FileUtil.secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, FileUtil.host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		try
		{
			Policy policy=FileUtil.getObjectPolicy(baiduBCS);
			String username=policy.getStatements().get(0).getUser().get(1);

			fileauthor.setText("上传者:"+username.substring(4));
			webviewUrl=FileUtil.generateUrl(baiduBCS);

		} catch (BCSServiceException e) {
			Log.w("===baidu==","Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
        handler.removeCallbacks(runnable);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.reback_btn:
				finish();
				break;
			case R.id.fileonline:
				/*//实例化WebView对象
				webview = new WebView(this);
				//设置WebView属性，能够执行Javascript脚本
				webview.getSettings().setJavaScriptEnabled(true);
				//加载需要显示的网页
				webview.loadUrl(FileUtil.weburl+webviewUrl);

				Log.e("-------webURL-------",FileUtilweburl+webviewUrl);
				Log.e("-------webURL-------",FileUtil..weburl+webviewUrl);
				Log.e("-------webURL-------",FileUtil.weburl+webviewUrl);
				Log.e("-------webURL-------",FileUtil.weburl+webviewUrl);
				Log.e("-------webURL-------",FileUtil.weburl+webviewUrl);
				//设置Web视图
				setContentView(webview);*/
				Intent intent=new Intent(HomeworkFileDetailActivity.this,WebViewActivity.class);
				intent.putExtra("urladdress",FileUtil.weburl+webviewUrl);
				startActivity(intent);
				break;
			case R.id.button:
				BCSCredentials credentials = new BCSCredentials(FileUtil.accessKey, FileUtil.secretKey);
				BaiduBCS baiduBCS = new BaiduBCS(credentials, FileUtil.host);
				// baiduBCS.setDefaultEncoding("GBK");
				baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
				try {
					FileUtil.getObjectWithDestFile(baiduBCS,fileName.getText().toString());
				    Toast.makeText(HomeworkFileDetailActivity.this,"文件保存在/storage/sdcard0/campusmobile目录下",Toast.LENGTH_SHORT).show();

				} catch (BCSServiceException e) {
					Log.w("-----baidu-----","Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
				    Toast.makeText(HomeworkFileDetailActivity.this,"内部错误,请妳反馈给我!",Toast.LENGTH_SHORT).show();
				} catch (BCSClientException e) {
					e.printStackTrace();
					Toast.makeText(HomeworkFileDetailActivity.this,"内部错误,请妳反馈给我!",Toast.LENGTH_SHORT).show();
				}
			case R.id.right_btn:
                Intent wintent=new Intent(HomeworkFileDetailActivity.this,HomeworkRemarkActivity.class);
				wintent.putExtra("filename", fileName.getText().toString());
				wintent.putExtra("author",fileauthor.getText().toString().substring(4));
                wintent.putExtra("courename",allurl);
                wintent.putExtra("teacherName","xiaowang");
				startActivity(wintent);
				break;
			default:
				break;
		}
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



























