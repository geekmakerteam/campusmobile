package com.geeklub.vass.mc4android.app.ui.teacher;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.fragment.teacher.CallNamesFragment;
import com.geeklub.vass.mc4android.app.fragment.teacher.LoginWebFragment;
import butterknife.ButterKnife;

public class CallNamesActivity extends ActionBarActivity implements ActionBar.TabListener{

    private Courses mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_names);

        ButterKnife.inject(this);

        mData =  getIntent().getParcelableExtra("todayCourses");

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        actionBar.addTab(actionBar.newTab()
                            .setText("已到")
                            .setTabListener(this)
        );

        actionBar.addTab(actionBar.newTab()
                        .setText("未到")
                        .setTabListener(this)
        );

        actionBar.addTab(actionBar.newTab()
                        .setText("扫一扫")
                        .setTabListener(this)
        );



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call_names, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return true;

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        switch (tab.getPosition()){
            case 0:
                fragmentTransaction.replace(R.id.container,CallNamesFragment.newInstance(mData.getId(),0,false));
                break;
            case 1:
                fragmentTransaction.replace(R.id.container,CallNamesFragment.newInstance(mData.getId(),1,true));
                break;
            case 2:
                fragmentTransaction.replace(R.id.container, LoginWebFragment.newInstance(mData.getId()));
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }



}
