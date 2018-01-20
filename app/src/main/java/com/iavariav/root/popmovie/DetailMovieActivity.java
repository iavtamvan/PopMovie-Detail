package com.iavariav.root.popmovie;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iavariav.root.popmovie.Model.ListMovieModel;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {

//    private AppBarLayout appBar;
//    private CollapsingToolbarLayout toolbarLayout;

    private ImageView imgPoster;
    private Toolbar toolbar;
    private TextView tvOverview;
    private FloatingActionButton fab;

    ArrayList<ListMovieModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgPoster = (ImageView) findViewById(R.id.img_poster);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        list = new ArrayList<>();
        list = getIntent().getParcelableArrayListExtra("list");
        int pos = getIntent().getIntExtra("position",0);

        getSupportActionBar().setTitle(list.get(pos).getTitle());
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +
                list.get(pos).getPosterPath()).into(imgPoster);
        tvOverview.setText(list.get(pos).getOverview());
    }

    private void initView() {
//        appBar = (AppBarLayout) findViewById(R.id.app_bar);
//        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        imgPoster = (ImageView) findViewById(R.id.img_poster);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
