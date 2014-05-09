package com.geeklub.vass.mc4android.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.utils.KFSettings;
import com.geeklub.vass.mc4android.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 */
public class ShareFragment extends Fragment{


    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ShareFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    Intent intent=new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
	    intent.putExtra(Intent.EXTRA_TEXT, "我正在使用校园必备神器-Oracle天眼手机校园系统.随时随地随你方便！快来下载吧！");
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(Intent.createChooser(intent, getActivity().getTitle()));
    }

}
