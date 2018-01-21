package com.iavariav.root.popmovie;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.iavariav.root.popmovie.Helper.MovieContract;
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

    SharedPreferences pref;
    Boolean isFavorit = false;
    int pos;


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

        //Setting Shared Preference
        pref = PreferenceManager.getDefaultSharedPreferences(DetailMovieActivity.this);

        //Terima data
        list = new ArrayList<>();
        list = getIntent().getParcelableArrayListExtra("list");
        pos = getIntent().getIntExtra("position",0);

        //Setting Value
        getSupportActionBar().setTitle(list.get(pos).getTitle());
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +
                list.get(pos).getPosterPath()).into(imgPoster);
        tvOverview.setText(list.get(pos).getOverview());

        //
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorit){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT"+list.get(pos).getTitle(), false);
                    editor.commit();
                    isFavorit = false;
                    hapusFavorit();
                    Snackbar.make(view, "Favorit dihapus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT"+list.get(pos).getTitle(),true);
                    editor.commit();
                    isFavorit = true;
                    tambahFavorit();
                    Snackbar.make(view, "Favorit ditambah", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                updateFAB();
            }
        });

        pref.getBoolean("FAVORIT"+list.get(pos).getTitle(),false);
        updateFAB();
    }

    private void hapusFavorit() {
        getContentResolver()
                .delete(
                        MovieContract.MovieEntry.CONTENT_URI
                                .buildUpon()
                                .appendPath(String.valueOf(list.get(pos).getId())).build()
                        ,null,null);
    }

    private void tambahFavorit() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_ID,
                list.get(pos).getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_JUDUL,
                list.get(pos).getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER,
                list.get(pos).getPosterPath());

        getContentResolver()
                .insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
    }

    private void updateFAB() {
        if (isFavorit){
            fab.setImageResource(R.drawable.ic_favorite);
        }else {
            fab.setImageResource(R.drawable.ic_not_favorite);
        }
    }

//    private void initView() {
////        appBar = (AppBarLayout) findViewById(R.id.app_bar);
////        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        imgPoster = (ImageView) findViewById(R.id.img_poster);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tvOverview = (TextView) findViewById(R.id.tv_overview);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//    }
}
