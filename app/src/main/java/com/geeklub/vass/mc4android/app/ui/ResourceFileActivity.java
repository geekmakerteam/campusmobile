package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.HomeworkFileAdapter;
import com.geeklub.vass.mc4android.app.beans.UserPassword;
import com.geeklub.vass.mc4android.app.utils.FilterUtil;
import com.geeklub.vass.mc4android.app.utils.ResourceUtil;
import com.geeklub.vass.mc4android.app.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 
 * @author Administrator
 *
 */
public class ResourceFileActivity extends Activity implements OnClickListener{

	@InjectView(R.id.file_reback_btn)
	Button mBackBtn;

    @InjectView(R.id.file_publish)
    Button fileButton;

	@InjectView(R.id.api_list_view)
	ListView mListView;

	List<String> data;

	static String oj="";

	private HomeworkFileAdapter mAdapter;


	List<ObjectSummary> mList=new ArrayList<ObjectSummary>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_resource);
		ButterKnife.inject(this);

		mBackBtn.setOnClickListener(this);
		fileButton.setOnClickListener(this);
		String a = getIntent().getStringExtra("object");
		Log.i("---baidu----",a);
		oj=a;
		ResourceUtil.object=a;

		loadData();

		mAdapter = new HomeworkFileAdapter(mList,this);

		mListView.setAdapter(mAdapter);

		mAdapter.notifyDataSetChanged();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   ObjectSummary objectSum=mList.get(i);
				Intent intent=new Intent(ResourceFileActivity.this,ResourceDetailActivity.class);
				intent.putExtra("filename",objectSum.getName());
				intent.putExtra("filesize", FilterUtil.sizeConver(objectSum.getSize()));
				startActivity(intent);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	private void loadData() {

		BCSCredentials credentials = new BCSCredentials(ResourceUtil.accessKey, ResourceUtil.secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, ResourceUtil.host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8

		try {
			mList=ResourceUtil.listObject(baiduBCS);

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
			case R.id.file_reback_btn:
				finish();
				break;
			case R.id.file_publish:
				showFileChooser();
				break;
			default:
				break;
		}
	}
	/** 调用文件选择软件来选择文件 **/
	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
					1);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {
			Log.e("---path----", "onActivityResult() error, resultCode: " + resultCode);
			super.onActivityResult(requestCode, resultCode, data);
			return;
		}
		if (requestCode == 1) {
			Uri uri = data.getData();
			Log.i("-------path-----", "------->" + uri.getPath());
			UserPassword userPassword= SharedPreferencesUtils.readSharedPreferences(getApplicationContext());
			BCSCredentials credentials = new BCSCredentials(ResourceUtil.accessKey, ResourceUtil.secretKey);
			BaiduBCS baiduBCS = new BaiduBCS(credentials, ResourceUtil.host);
			baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
			ResourceUtil.object=oj;
			ResourceUtil.putObjectByFile(baiduBCS,uri.getPath(),userPassword.getUserName());
			Toast.makeText(ResourceFileActivity.this,"作业发布成功！",Toast.LENGTH_SHORT).show();
			//loadData();
            mAdapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
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



























