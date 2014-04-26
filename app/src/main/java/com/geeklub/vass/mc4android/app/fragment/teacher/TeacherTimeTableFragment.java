package com.geeklub.vass.mc4android.app.fragment.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.fragment.TimeTableFragment;
import com.geeklub.vass.mc4android.app.ui.MainActivity;
import java.util.ArrayList;
import java.util.Calendar;


public class TeacherTimeTableFragment extends TimeTableFragment {

    private MyPagerAdapter mAdapter;

    public static TeacherTimeTableFragment newInstance() {
        TeacherTimeTableFragment fragment = new TeacherTimeTableFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdapter = new MyPagerAdapter(getChildFragmentManager());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((MainActivity) getActivity()).onSectionAttached(R.string.call_names);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    protected void initView(ArrayList<ArrayList<Courses>> datas) {
        mAdapter.bindData(datas);
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
        mIndicator.setCurrentItem(mCalendar.get(Calendar.DAY_OF_WEEK) - 2);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<ArrayList<Courses>> mData;

        private final String[] TITLES = {
                "星期一",
                "星期二",
                "星期三",
                "星期四",
                "星期五",
                "星期六",
                "星期日"
        };

        public void bindData(ArrayList<ArrayList<Courses>> data) {
            mData = data;
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return TodayCoursesFragment.newInstance(mData.get(position));
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
