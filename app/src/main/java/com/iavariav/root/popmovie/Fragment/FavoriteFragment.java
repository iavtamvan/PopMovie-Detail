package com.iavariav.root.popmovie.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iavariav.root.popmovie.Adapter.MovieAdapter;
import com.iavariav.root.popmovie.Helper.MovieContract;
import com.iavariav.root.popmovie.Model.ListMovieModel;
import com.iavariav.root.popmovie.Model.MovieModel;
import com.iavariav.root.popmovie.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView mrvFavorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    ArrayList<ListMovieModel> favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favorite = new ArrayList<>();
        mrvFavorite = view.findViewById(R.id.rv_favorite);
        mrvFavorite.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 100:
                return new CursorLoader(
                        getActivity(),
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Working");
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favorite.clear();
        if (data.getCount() > 0) {
            if (data.moveToFirst()) {
                do {
                    ListMovieModel movieModel = new ListMovieModel();
                    movieModel.setId(data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                    movieModel.setTitle(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_JUDUL)));
                    movieModel.setPosterPath(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
//                    model.setOverview(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                    favorite.add(movieModel);
                    MovieAdapter movieAdapter = new MovieAdapter(getActivity(), favorite);
                    mrvFavorite.setAdapter(movieAdapter);
                } while (data.moveToNext());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().initLoader(100, null, this);
    }
}
