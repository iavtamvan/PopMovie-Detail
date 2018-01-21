package com.iavariav.root.popmovie.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iavariav.root.popmovie.Model.ListMovieModel;
import com.iavariav.root.popmovie.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
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



        return view;
    }

}
