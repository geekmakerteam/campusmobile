package com.geeklub.vass.mc4android.app.fragment.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.google.zxing.Result;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 2014/4/24.
 */
public class LoginWebFragment extends BarCodeScannerFragment implements BarCodeScannerFragment.IResultCallback {

    private final static String ARG_COURSE_ID = "course_id";

    private String mCourseID;
    private String tag = "LoginWebFragment";


    public static LoginWebFragment newInstance(String courseId) {
        LoginWebFragment fragment = new LoginWebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COURSE_ID ,courseId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCourseID = getArguments().getString(ARG_COURSE_ID);
            setmCallBack(this);
        }

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            ((MainActivity)getActivity()).onSectionAttached(R.string.login_web);

        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void result(Result lastResult) {
        Log.i(tag, lastResult.toString() + "&courseId" + mCourseID);

        MCRestClient.get_qrcode( lastResult.toString() + "&courseId=" + mCourseID , null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);

                try {
                    if (response.getBoolean("status")) {
                        SuperToast.create(MCApplication.getApplication(), "登陆web端成功...", SuperToast.Duration.SHORT,
                                Style.getStyle(Style.BLACK, SuperToast.Animations.SCALE)).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, MCApplication.getApplication());

    }
}
