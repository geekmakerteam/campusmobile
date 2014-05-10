package com.geeklub.vass.mc4android.app.ui;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.actionprovider.SettingActionProvider;
import com.geeklub.vass.mc4android.app.beans.ApiEntity;
import com.geeklub.vass.mc4android.app.beans.classnews.ClassNews;
import com.geeklub.vass.mc4android.app.beans.courses.Courses;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;
import com.geeklub.vass.mc4android.app.fragment.ClassNewsFragment;
import com.geeklub.vass.mc4android.app.fragment.FeedbackFragment;
import com.geeklub.vass.mc4android.app.fragment.SchoolNewsFragment;
import com.geeklub.vass.mc4android.app.fragment.SettingFragment;
import com.geeklub.vass.mc4android.app.fragment.ShareFragment;
import com.geeklub.vass.mc4android.app.fragment.student.SignInFragment;
import com.geeklub.vass.mc4android.app.fragment.student.StudentTimeTableFragment;
import com.geeklub.vass.mc4android.app.fragment.teacher.CampusWeixingFragment;
import com.geeklub.vass.mc4android.app.fragment.teacher.TeacherTimeTableFragment;
import com.geeklub.vass.mc4android.app.fragment.teacher.TodayCoursesFragment;
import com.geeklub.vass.mc4android.app.ui.teacher.CallNamesActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import android.os.StrictMode;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        SchoolNewsFragment.OnFragmentInteractionListener,
        ClassNewsFragment.OnFragmentInteractionListener,
        TodayCoursesFragment.OnFragmentInteractionListener,CampusWeixingFragment.OnFragmentInteractionListener{
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

	private ActionBar actionBar;

	SpinnerAdapter adapter;

	ShareActionProvider mShareActionProvider;


	SettingActionProvider settingActionProvider;

	//protected MenuItem refreshItem;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

		 adapter = ArrayAdapter.createFromResource(this, R.array.systemsetting, android.R.layout.simple_spinner_dropdown_item);

    }

	public void onSectionAttached(int name_id) {
        mTitle = getString(name_id);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
      //  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	    actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

	    actionBar.setListNavigationCallbacks(adapter,new ActionBar.OnNavigationListener() {
		    String[] listNames = getResources().getStringArray(R.array.systemsetting);
		    @Override
		    public boolean onNavigationItemSelected(int i, long l) {
			    FragmentManager manager = getSupportFragmentManager();
			    FragmentTransaction transaction = manager.beginTransaction();
			    // 将Activity中的内容替换成对应选择的Fragment
			    //	transaction.replace(R.id.context, student, listNames[itemPosition]);
			    if(listNames[i].equals(":"))
			    {

			    }
			    if(listNames[i].equals("设置"))
			    {
				    transaction.replace(R.id.container, SettingFragment.newInstance());
			    }
			    if(listNames[i].equals("分享"))
			    {
                    transaction.replace(R.id.container, ShareFragment.newInstance());
			    }
			    if(listNames[i].equals("反馈"))
			    {
				    transaction.replace(R.id.container, FeedbackFragment.newInstance());
			    }
			    if(listNames[i].equals("关于"))
			    {
				    new AlertDialog.Builder(MainActivity.this)
						    .setTitle("关于")
						    .setMessage("面对传统校园教学的处理方式,你难道不想让校园教学变得方便?" +
								    "                       这是我们开发手机校园的原因,我们想让所有事情善始善终.")
						    .setPositiveButton("确定", null)
						    .show();
			    }
			    transaction.commit();
			    return true;
		    }
	    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
         //   restoreActionBar();

	        return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    FragmentManager manager = getSupportFragmentManager();
	    FragmentTransaction transaction = manager.beginTransaction();
	    switch (item.getItemId()) {
		    case R.id.menu_share:
			    transaction.replace(R.id.container, ShareFragment.newInstance());
			    transaction.commit();
			    return true;
		    case R.id.menu_setting:
			    transaction.replace(R.id.container, SettingFragment.newInstance());
			    transaction.commit();
			    return true;
		    case R.id.menu_feedback:
			    FeedbackAgent agent = new FeedbackAgent(this);
			    agent.startFeedbackActivity();
			    transaction.commit();
			    return true;
		    case R.id.menu_about:
			    new AlertDialog.Builder(MainActivity.this)
					    .setTitle("关于")
					    .setMessage("面对传统校园教学的处理方式,你难道不想让校园教学变得方便?" +
							    "                       这是我们开发手机校园的原因,我们想让所有事情善始善终.")
					    .setPositiveButton("确定", null)
					    .show();
		    default:
			    transaction.commit();
			    return super.onOptionsItemSelected(item);
	       }
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

	    if(name.equals("校园微信"))
	    {
            fragmentTransaction.replace(R.id.container,CampusWeixingFragment.newInstance());
	    }

		if(name.equals("退出"))
		{
			new AlertDialog.Builder(this)
					.setMessage("确认退出登录?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which)
								{
									//退出登录
									KFIMInterfaces.Logout(MainActivity.this);
									finish();
								}
							}).setNegativeButton("取消", null).create()
					.show();
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

	@Override
	public void onFragmentInteraction(ApiEntity entity) {
	//	Intent intent = new Intent(this, CallNamesActivity.class);
	//	intent.putExtra("todayCourses", todayCourses);
	//	startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private long exitTime = 0;
	private boolean flag=false;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK|| keyCode == KeyEvent.KEYCODE_HOME) {
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
				flag=false;
			} else {
				System.exit(0);
				android.os.Process.killProcess(android.os.Process
						.myPid());
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
