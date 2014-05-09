package com.geeklub.vass.mc4android.app.fragment.student;

import android.app.Activity;
import android.os.Bundle;
import com.geeklub.vass.mc4android.app.R;
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


public class SignInFragment extends BarCodeScannerFragment implements BarCodeScannerFragment.IResultCallback{




    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
       }
    public SignInFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            setmCallBack(this);
        }

    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((MainActivity)getActivity()).onSectionAttached(R.string.sign_in);
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
        MCRestClient.get_qrcode(lastResult.toString() ,null,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);

                try {
                    if (response.getBoolean("status")){
                        SuperToast.create(MCApplication.getApplication(), "签到成功...", SuperToast.Duration.SHORT,
                                Style.getStyle(Style.BLUE, SuperToast.Animations.FADE)).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, MCApplication.getApplication());

    }



}
