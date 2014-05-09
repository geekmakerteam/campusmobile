package com.geeklub.vass.mc4android.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.SchoolNewsAdapter;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.geeklub.vass.mc4android.app.utils.FastJSONUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 学院新闻
 */
public class SchoolNewsFragment extends Fragment implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listView)
    ListView mListView;

    private OnFragmentInteractionListener mListener;
    private List<EachNews> mList = new ArrayList<EachNews>();
    private SchoolNewsAdapter mAdapter;
    private AnimationAdapter  animationAdapter;


    public static SchoolNewsFragment newInstance() {
        SchoolNewsFragment fragment = new SchoolNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SchoolNewsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAdapter = new SchoolNewsAdapter(mList, getActivity());

            animationAdapter = new SwingLeftInAnimationAdapter(mAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_news, null);
        ButterKnife.inject(this, view);

	    mListView.setOnItemClickListener(this);

	    animationAdapter.setAbsListView(mListView);
	    mListView.setAdapter(animationAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAdapter.isEmpty()) {
            loadData();
        }
    }


	private void loadData() {
        MCRestClient.get(API.SCHOOL_NEWS, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);

                List<EachNews> allnews = FastJSONUtil.getObjects(content, EachNews.class);
                mList.addAll(allnews);
                mAdapter.notifyDataSetChanged();
            }
        }, MCApplication.getApplication());
    }

    @Override
    public void onAttach(Activity activity) {
	    super.onAttach(activity);
        try {
            ((MainActivity) getActivity()).onSectionAttached(R.string.school_news);

            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

	@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            mListener.onFragmentInteraction((EachNews) parent.getItemAtPosition(position));
        }
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(EachNews eachNews);
    }
}
