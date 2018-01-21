package com.iavariav.root.popmovie;

import android.content.ContentUris;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iavariav.root.popmovie.Helper.MovieContract;
import com.iavariav.root.popmovie.Model.ListMovieModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {

//    private AppBarLayout appBar;
//    private CollapsingToolbarLayout toolbarLayout;

    private ImageView imgPoster;
    private Toolbar toolbar;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvRating;
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
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvRating = (TextView) findViewById(R.id.tv_rating);

        //Setting Shared Preference
        pref = PreferenceManager.getDefaultSharedPreferences(DetailMovieActivity.this);

        //Terima data
        list = new ArrayList<>();
        list = getIntent().getParcelableArrayListExtra("list");
        pos = getIntent().getIntExtra("position", 0);

        //Setting Value
        getSupportActionBar().setTitle(list.get(pos).getTitle());
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +
                list.get(pos).getPosterPath()).into(imgPoster);
        tvOverview.setText(list.get(pos).getOverview());
        tvReleaseDate.setText(list.get(pos).getReleaseDate());
        tvRating.setText("" + list.get(pos).getVoteAverage());

        //ambil nilai isFavorit pada shared preference
        isFavorit = pref.getBoolean("FAVORIT" + list.get(pos).getId(), false);
        updateFAB();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorit) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT" + list.get(pos).getId(), false);
                    editor.commit();
                    isFavorit = false;
                    hapusFavorit();
                    Toast.makeText(DetailMovieActivity.this, "" + isFavorit, Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Favorit dihapus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT" + list.get(pos).getId(), true);
                    editor.commit();
                    isFavorit = true;
                    tambahFavorit();
                    Toast.makeText(DetailMovieActivity.this, "" + isFavorit, Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Favorit ditambah", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
                updateFAB();
            }
        });
    }

    private void hapusFavorit() {
        getContentResolver()
                .delete(
                        MovieContract.MovieEntry.CONTENT_URI
                                .buildUpon()
                                .appendPath(String.valueOf(list.get(pos).getId())).build()
                        , null, null);
    }

    private void tambahFavorit() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_ID,
                list.get(pos).getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_JUDUL,
                list.get(pos).getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER,
                list.get(pos).getPosterPath());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,
                list.get(pos).getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                list.get(pos).getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RATING,
                list.get(pos).getVoteAverage());
        getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
//        if (ContentUris.parseId(uri)>0){
//            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, "Data Gagal disimpan", Toast.LENGTH_SHORT).show();
//        }

    }

    private void updateFAB() {
        if (isFavorit) {
            fab.setImageResource(R.drawable.ic_favorite);
        } else {
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
