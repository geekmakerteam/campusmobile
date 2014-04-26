package com.geeklub.vass.mc4android.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.etsy.android.grid.StaggeredGridView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.ClassNewsAdapter;
import com.geeklub.vass.mc4android.app.beans.classnews.ClassNews;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.geeklub.vass.mc4android.app.utils.FastJSONUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ClassNewsFragment extends Fragment implements AdapterView.OnItemClickListener {


    @InjectView(R.id.grid_view)
    StaggeredGridView mGridView;

    private OnFragmentInteractionListener mListener;
    private List<ClassNews> mList = new ArrayList<ClassNews>();
    private ClassNewsAdapter mAdapter;


    public static ClassNewsFragment newInstance() {
        ClassNewsFragment fragment = new ClassNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ClassNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdapter = new ClassNewsAdapter(mList, getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_news, null);

        ButterKnife.inject(this, view);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

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
        MCRestClient.get(API.CLASS_NEWS, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                List<ClassNews> classNewses = FastJSONUtil.getObjects(content, ClassNews.class);
                mList.addAll(classNewses);
                mAdapter.notifyDataSetChanged();
            }
        }, MCApplication.getApplication());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((MainActivity) getActivity()).onSectionAttached(R.string.class_news);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            mListener.onFragmentInteraction((ClassNews) parent.getItemAtPosition(position));
        }
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(ClassNews classNews);
    }

}
