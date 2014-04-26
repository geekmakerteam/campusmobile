package com.geeklub.vass.mc4android.app.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.classnews.ClassNews;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;
import com.geeklub.vass.mc4android.app.fragment.ClassNewsFragment;
import com.geeklub.vass.mc4android.app.fragment.SchoolNewsFragment;
import com.geeklub.vass.mc4android.app.fragment.student.SignInFragment;
import com.geeklub.vass.mc4android.app.fragment.student.StudentTimeTableFragment;
import com.geeklub.vass.mc4android.app.fragment.teacher.TeacherTimeTableFragment;
import com.geeklub.vass.mc4android.app.fragment.teacher.TodayCoursesFragment;
import com.geeklub.vass.mc4android.app.ui.teacher.CallNamesActivity;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        SchoolNewsFragment.OnFragmentInteractionListener,
        ClassNewsFragment.OnFragmentInteractionListener,
        TodayCoursesFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    public void onSectionAttached(int name_id) {
        mTitle = getString(name_id);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNavigationDrawerItemSelected(String name) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (name.equals("学院新闻")) {
            fragmentTransaction.replace(R.id.container, SchoolNewsFragment.newInstance());
        }
        if (name.equals("班级通知")) {
            fragmentTransaction.replace(R.id.container, ClassNewsFragment.newInstance());
        }
//        if (name.equals("课表")) {
//            fragmentTransaction.replace(R.id.container, TimeTableFragment.newInstance());
//
//        }
        if (name.equals("课表")) {
            fragmentTransaction.replace(R.id.container, StudentTimeTableFragment.newInstance());

        }
        if (name.equals("签到")) {
            fragmentTransaction.replace(R.id.container, SignInFragment.newInstance());
        }

        if (name.equals("发布通知")) {

        }
//        if (name.equals("点名")) {
//            fragmentTransaction.replace(R.id.container,TodayCoursesFragment.newInstance());
//
//        }
        if (name.equals("点名")) {
            fragmentTransaction.replace(R.id.container, TeacherTimeTableFragment.newInstance());
        }
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(EachNews eachNews) {
        Intent intent = new Intent(this, SchoolNewsDetailActivity.class);
        intent.putExtra("eachNews", eachNews);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(ClassNews classNews) {
        Intent intent = new Intent(this, ClassNewsDetailActivity.class);
        intent.putExtra("classNews", classNews);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Courses todayCourses) {
        Intent intent = new Intent(this, CallNamesActivity.class);
        intent.putExtra("todayCourses", todayCourses);
        startActivity(intent);
    }

}
