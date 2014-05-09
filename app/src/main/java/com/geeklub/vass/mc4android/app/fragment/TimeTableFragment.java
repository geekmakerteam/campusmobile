package com.geeklub.vass.mc4android.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.beans.courses.TermCourses;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import com.geeklub.vass.mc4android.app.utils.CourseUtil;
import com.geeklub.vass.mc4android.app.utils.FastJSONUtil;
import com.geeklub.vass.mc4android.app.utils.LoginUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.viewpagerindicator.TitlePageIndicator;
import java.util.ArrayList;
import java.util.Calendar;
import butterknife.ButterKnife;
import butterknife.InjectView;


public abstract class TimeTableFragment extends Fragment {


    @InjectView(R.id.pager)
    protected ViewPager mPager;
    @InjectView(R.id.indicator)
    protected TitlePageIndicator mIndicator;

    private MCApplication mApplication = MCApplication.getApplication();
    protected Calendar mCalendar = Calendar.getInstance();
//    private MyPagerAdapter mAdapter;


//    public static TimeTableFragment newInstance() {
//        TimeTableFragment fragment = new TimeTableFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public TimeTableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mAdapter = new MyPagerAdapter(getChildFragmentManager());
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, null);

        ButterKnife.inject(this, view);

        loadData();

        return view;
    }

    private void loadData() {
        if (CourseUtil.isSave(mApplication, LoginUtil.getUserName(mApplication))) {
            String jsonString = CourseUtil.getData(mApplication, LoginUtil.getUserName(mApplication));

            ArrayList<ArrayList<Courses>> datas = FastJSONUtil.getObject(jsonString, TermCourses.class).getCourse();

            initView(datas);

        } else {
            loadFromNet();
        }
    }

    private void loadFromNet() {
        MCRestClient.get(API.COURSE, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);

                CourseUtil.saveData(mApplication, LoginUtil.getUserName(mApplication), content);
                ArrayList<ArrayList<Courses>> datas = FastJSONUtil.getObject(content, TermCourses.class).getCourse();
                initView(datas);

            }

        }, mApplication);

    }

    protected abstract void initView(ArrayList<ArrayList<Courses>> datas) ;
//        mAdapter.bindData(datas);
//        mPager.setAdapter(mAdapter);
//        mIndicator.setViewPager(mPager);
//        mIndicator.setCurrentItem(mCalendar.get(Calendar.DAY_OF_WEEK) - 2);



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            ((MainActivity) getActivity()).onSectionAttached(R.string.time_table);
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }





//    private class MyPagerAdapter extends FragmentPagerAdapter {
//
//        private ArrayList<ArrayList<Courses>> mData;
//
//        private final String[] TITLES = {
//                "星期一",
//                "星期二",
//                "星期三",
//                "星期四",
//                "星期五",
//                "星期六",
//                "星期日"
//        };
//
//        public void bindData(ArrayList<ArrayList<Courses>> data){
//            mData = data;
//        }
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//
//
//
//        @Override
//        public Fragment getItem(int position) {
//            return TimeTableListFragment.newInstance(mData.get(position));
//        }
//
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLES[position];
//        }
//
//        @Override
//        public int getCount() {
//            return TITLES.length;
//        }
//    }


}
