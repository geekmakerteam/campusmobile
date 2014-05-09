package com.geeklub.vass.mc4android.app.fragment.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.CoursesAdapter;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class TodayCoursesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private OnFragmentInteractionListener mListener;
    private CoursesAdapter mAdapter;
    private ArrayList<Courses> mList = new ArrayList<Courses>();
//    private final Calendar mCalendar = Calendar.getInstance();

    @InjectView(R.id.listView)
    ListView mListView;
    @InjectView(R.id.imageView)
    ImageView mEmptyView;


    private String tag = "TodayCoursesFragment";


    public static TodayCoursesFragment newInstance(ArrayList<Courses> courses_data) {
        TodayCoursesFragment fragment = new TodayCoursesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("courses_data",courses_data);
        fragment.setArguments(args);
        return fragment;
    }
    public TodayCoursesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mList = getArguments().getParcelableArrayList("courses_data");
            mAdapter = new CoursesAdapter(mList,getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_courses,null);


        ButterKnife.inject(this, view);
        mListView.setOnItemClickListener(this);
        AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        animAdapter.setAbsListView(mListView);
        mListView.setAdapter(animAdapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (mAdapter.isEmpty()){
//            loadData();

            mEmptyView.setVisibility(View.VISIBLE);
            mListView.setEmptyView(mEmptyView);

        }
    }

//    private void loadData() {
//        Log.i(tag,"今天是星期？ ===>"+ (mCalendar.get(Calendar.DAY_OF_WEEK)-1));
//        MCRestClient.get(API.TODAY_CPURSRS + (mCalendar.get(Calendar.DAY_OF_WEEK)-1),null,new AsyncHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, String content) {
//                super.onSuccess(statusCode, content);
//
//                List<Courses> datas = FastJSONUtil.getObjects(content,Courses.class);
//                mList.addAll(datas);
//
//                if (mAdapter.isEmpty()){
//                    mListView.setEmptyView(mEmptyView);
//                }else{
//                    mAdapter.notifyDataSetChanged();
//                }
//
//
//            }
//        }, MCApplication.getApplication());
//
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((MainActivity)getActivity()).onSectionAttached(R.string.call_names);
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
            mListener.onFragmentInteraction((Courses) parent.getItemAtPosition(position));
        }
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Courses todayCourses);
    }

}
