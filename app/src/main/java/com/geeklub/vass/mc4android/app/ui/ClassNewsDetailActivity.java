package com.geeklub.vass.mc4android.app.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.geeklub.vass.mc4android.app.R;
import com.geeklub.vass.mc4android.app.beans.classnews.ClassNews;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassNewsDetailActivity extends ActionBarActivity {
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.content)
    TextView tv_content;
    @InjectView(R.id.author)
    TextView tv_author;
    @InjectView(R.id.time)
    TextView tv_time;

    private ClassNews classNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_news_detail);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        classNews = getIntent().getParcelableExtra("classNews");


        initView();

    }

    private void initView() {

        tv_title.setText(classNews.getTitle());

        tv_content.setText(classNews.getContent());

        tv_author.setText(classNews.getAuthor());

        tv_time.setText(classNews.getTime());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_news_detail, menu);
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

}
