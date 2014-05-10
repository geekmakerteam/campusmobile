package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.geeklub.vass.mc4android.app.beans.UserPassword;
import com.geeklub.vass.mc4android.app.utils.FileUtil;
import com.geeklub.vass.mc4android.app.utils.FilterUtil;
import com.geeklub.vass.mc4android.app.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
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

	public String webviewUrl;

	private WebView webview;


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
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	private void loadData() {

		String filename = getIntent().getStringExtra("filename");
		//object="/"+object+"/"+url.split("/",0)[url.split("/",0).length-1]
		FileUtil.object=filename;
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

				Log.e("-------webURL-------",FileUtil.weburl+webviewUrl);
				Log.e("-------webURL-------",FileUtil.weburl+webviewUrl);
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



























