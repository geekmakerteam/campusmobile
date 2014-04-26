package com.geeklub.vass.mc4android.app.fragment.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.adapter.CallNamesAdapter;
import com.geeklub.vass.mc4android.app.beans.CallNamesResult;
import com.geeklub.vass.mc4android.app.beans.Student;
import com.geeklub.vass.mc4android.app.common.API;
import com.geeklub.vass.mc4android.app.common.APIParams;
import com.geeklub.vass.mc4android.app.utils.FastJSONUtil;
import com.geeklub.vass.mc4android.app.utils.MCApplication;
import com.geeklub.vass.mc4android.app.utils.MCRestClient;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CallNamesFragment extends Fragment implements OnDismissCallback,ContextualUndoAdapter.DeleteItemCallback {


    @InjectView(R.id.listView)
    ListView mListView;



    private TextView tv_total;



    private static final String ARG_COURSE_ID = "course_id";
    private static final String ARG_STATUS  = "status";
    private static final String ARG_CAN_DISMISS = "can_dismiss";

    private String mCourseID;
    private String mStatus;
    private boolean mDismiss;

    private List<Student> mList = new ArrayList<Student>();
    private CallNamesAdapter mAdapter;
    private SwipeDismissAdapter mSwipeDismissAdapter;
    private String tag = "CallNamesFragment";



    public static CallNamesFragment newInstance(String courseID, int status,boolean swipeToDismiss ) {
        CallNamesFragment fragment = new CallNamesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COURSE_ID, courseID);
        args.putString(ARG_STATUS, String.valueOf(status));
        args.putBoolean(ARG_CAN_DISMISS,swipeToDismiss);
        fragment.setArguments(args);
        return fragment;
    }
    public CallNamesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourseID = getArguments().getString(ARG_COURSE_ID);
            mStatus = getArguments().getString(ARG_STATUS);
            mDismiss = getArguments().getBoolean(ARG_CAN_DISMISS);

            mAdapter = new CallNamesAdapter(mList,getActivity());

            if (mDismiss) {
                mSwipeDismissAdapter = new SwipeDismissAdapter(mAdapter, this);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter.isEmpty()) {
            loadData();
        }
    }

    private void loadData() {

        MCRestClient.get(API.CALL_NAMES,new APIParams().with("courseId",mCourseID).with("status",mStatus),new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);



                CallNamesResult datas = FastJSONUtil.getObject(content, CallNamesResult.class);
                List<Student> students = datas.getUsers();

                initHeaderView(datas.getTotal(),students.size());

                mList.addAll(students);
                mAdapter.notifyDataSetChanged();

            }
        }, MCApplication.getApplication());

    }

    private void initHeaderView(int total, int size) {
        if (mDismiss) {
            tv_total.setText("全部人数:"+total+"        "+"未到人数:" + size);
        }else {
            tv_total.setText("全部人数:"+total+"        "+"已到人数:" + size);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_names,null);
        View headerView = inflater.inflate(R.layout.header_fragment_call_names,null);

        tv_total = (TextView) headerView.findViewById(R.id.total);


        ButterKnife.inject(this, view);

        mListView.addHeaderView(headerView);


        if (mDismiss) {
            mSwipeDismissAdapter.setAbsListView(mListView);
            mListView.setAdapter(mSwipeDismissAdapter);
            showNotification();
        }else {
            mListView.setAdapter(mAdapter);
        }
        return view;
    }

    private void showNotification() {
        SuperToast.create(MCApplication.getApplication(), "可以滑动补签哦...", SuperToast.Duration.SHORT,
                Style.getStyle(Style.GREEN, SuperToast.Animations.POPUP)).show();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void deleteItem(int position) {
        mAdapter.remove(position);
        Log.i(tag,"deleteItem ===>"+position);
    }

    @Override
    public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
        Log.i(tag,"reverseSortedPositions长度 ===>"+ reverseSortedPositions.length);

        for (int position : reverseSortedPositions) {
            Log.i(tag,"position ===>" + position);
            removeStudent(mAdapter.getItem(position));
            mAdapter.remove(position);
        }
    }

    private void removeStudent(final Object student) {

        MCRestClient.get(API.HELP_SIGN, new APIParams().with("signId", ((Student) student).getId()).with("status", "1"), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                Log.i("student", response.toString());
                try {
                    if (response.getBoolean("status")) {
                        SuperToast.create(MCApplication.getApplication(), "学生:"+((Student)student).getUserName() + "补签成功...", SuperToast.Duration.SHORT,
                                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("error", content.toString());
            }
        }, MCApplication.getApplication());
    }
}
