package com.geeklub.vass.mc4android.app.feedback;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.common.APIParams;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.geeklub.vass.mc4android.app.utils.LoginUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.geeklub.vass.mc4android.app.utils.SharedPreferencesUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class FeedbackAction {
	
	private Context mContext = null;
	private static final int SUCCESS = 0;
	private static final int FAILURE = 1;
	private boolean flag=false;
	
	public FeedbackAction(Context context) {
		mContext = context;
	}

	/**
	 * @param content
	 * @param contact
	 * @return
	 */
	public int sendFeedbackMessage(String content, String contact) {

		MCRestClient.post(API.PUBIC, new APIParams().with("title", contact).with("content", content), new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
					    try
					    {
						    if (response.getBoolean("status"))
						    {
							    flag=true;
						    }
					    }
					    catch (Exception e)
					    {
							   flag=false;
					    }
					}
				}, MCApplication.getApplication());
		if(flag)
		{
			return SUCCESS;
		}
		else
		{
			return FAILURE;
		}
	}
}
