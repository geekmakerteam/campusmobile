package com.geeklub.vass.mc4android.app.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.schoolnews.EachNews;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SchoolNewsDetailActivity extends ActionBarActivity {

    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.content)
    TextView tv_content;
    @InjectView(R.id.author)
    TextView tv_author;
    @InjectView(R.id.time)
    TextView tv_time;


    private EachNews eachNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_news_detail);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        eachNews = getIntent().getParcelableExtra("eachNews");

        initView();
    }


    private void initView() {
        tv_title.setText(eachNews.getTitle());
        tv_content.setText(eachNews.getContent());
        tv_author.setText(eachNews.getAuthor());
        tv_time.setText(eachNews.getTime());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.school_news_detail, menu);
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
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
