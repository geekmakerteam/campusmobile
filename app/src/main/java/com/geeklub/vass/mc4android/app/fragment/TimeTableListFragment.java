package com.geeklub.vass.mc4android.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.CoursesAdapter;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class TimeTableListFragment extends Fragment {

    @InjectView(R.id.listView)  ListView mListView;

    protected ArrayList<Courses> mList = new ArrayList<Courses>();
    protected CoursesAdapter mAdapter;



    public static TimeTableListFragment newInstance(ArrayList<Courses> courses_data) {
        TimeTableListFragment fragment = new TimeTableListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("courses_data",courses_data);
        fragment.setArguments(args);
        return fragment;
    }
    public TimeTableListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_time_table_list, container, false);
        ButterKnife.inject(this, view);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
