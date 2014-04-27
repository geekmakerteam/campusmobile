package com.geeklub.vass.mc4android.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class ClassNewsFragment extends Fragment implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {


    @InjectView(R.id.grid_view)
    StaggeredGridView mGridView;

    private OnFragmentInteractionListener mListener;
    private List<ClassNews>  mList = new ArrayList<ClassNews>();
    private ClassNewsAdapter mAdapter;
    private AnimationAdapter animationAdapter;

    private int     mPageNum = 1;
    private boolean mHasRequestedMore = false;

    private String tag = "ClassNewsFragment";

    public static ClassNewsFragment newInstance() {
        ClassNewsFragment fragment = new ClassNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ClassNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAdapter = new ClassNewsAdapter(mList, getActivity());
            animationAdapter = new SwingLeftInAnimationAdapter(mAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_news, null);

        ButterKnife.inject(this, view);
        animationAdapter.setAbsListView(mGridView);
        mGridView.setAdapter(animationAdapter);
//        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAdapter.isEmpty()) {
            loadData(mPageNum);
        }
    }

    private void loadData(int pageNum) {
        MCRestClient.get(API.CLASS_NEWS + pageNum , null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);

                List<ClassNews> classNewses = FastJSONUtil.getObjects(content, ClassNews.class);
                Log.i(tag,"一共拉取了" + classNewses.size()+"条数据");

                if (classNewses.isEmpty()){
                    mHasRequestedMore = false;
                }else {
                    mHasRequestedMore = true;
                    mPageNum ++;
                    mList.addAll(classNewses);
                    mAdapter.notifyDataSetChanged();
                }

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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && mHasRequestedMore){
            loadData(mPageNum);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mHasRequestedMore = (firstVisibleItem + visibleItemCount == totalItemCount);
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(ClassNews classNews);
    }

}
