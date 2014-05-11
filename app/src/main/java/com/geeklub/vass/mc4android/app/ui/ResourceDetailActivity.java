package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.utils.FileUtil;
import com.geeklub.vass.mc4android.app.utils.ResourceUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 
 * @author Administrator
 *
 */
public class ResourceDetailActivity extends Activity implements OnClickListener{

	@InjectView(R.id.reback_btn)
	Button mBackBtn;


	@InjectView(R.id.filename)
	TextView fileName;

	@InjectView(R.id.size)
	TextView size;

	@InjectView(R.id.fileauthor)
	TextView fileauthor;

	@InjectView(R.id.button)
	Button downloadButton;



	@InjectView(R.id.user_title)
	TextView userTitle;

	public String webviewUrl;

	private WebView webview;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_resource_detail);
		ButterKnife.inject(this);

		mBackBtn.setOnClickListener(this);
		downloadButton.setOnClickListener(this);


		loadData();
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	private void loadData() {

		String filename = getIntent().getStringExtra("filename");
		//object="/"+object+"/"+url.split("/",0)[url.split("/",0).length-1]
		ResourceUtil.object=filename;
		filename=filename.split("/",0)[2];

		Log.i("==filename==",filename);

		String filesize = getIntent().getStringExtra("filesize");

		userTitle.setText("");
		fileName.setText(filename);
		size.setText(filesize);

		BCSCredentials credentials = new BCSCredentials(ResourceUtil.accessKey, ResourceUtil.secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, ResourceUtil.host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		try
		{
			Policy policy=ResourceUtil.getObjectPolicy(baiduBCS);
			String username=policy.getStatements().get(0).getUser().get(1);

			fileauthor.setText("上传者:"+username.substring(4));
			webviewUrl=ResourceUtil.generateUrl(baiduBCS);

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
			case R.id.button:
				BCSCredentials credentials = new BCSCredentials(ResourceUtil.accessKey, ResourceUtil.secretKey);
				BaiduBCS baiduBCS = new BaiduBCS(credentials, ResourceUtil.host);
				// baiduBCS.setDefaultEncoding("GBK");
				baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
				try {
					ResourceUtil.getObjectWithDestFile(baiduBCS,fileName.getText().toString());
				    Toast.makeText(ResourceDetailActivity.this,"文件保存在/storage/sdcard0/campusmobile目录下",Toast.LENGTH_SHORT).show();

				} catch (BCSServiceException e) {
					Log.w("-----baidu-----","Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
				    Toast.makeText(ResourceDetailActivity.this,"内部错误,请妳反馈给我!",Toast.LENGTH_SHORT).show();
				} catch (BCSClientException e) {
					e.printStackTrace();
					Toast.makeText(ResourceDetailActivity.this,"内部错误,请妳反馈给我!",Toast.LENGTH_SHORT).show();
				}
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



























