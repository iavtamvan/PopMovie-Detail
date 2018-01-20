package com.iavariav.root.popmovie.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iavariav.root.popmovie.Adapter.MovieAdapter;
import com.iavariav.root.popmovie.Model.ListMovieModel;
import com.iavariav.root.popmovie.Model.MovieModel;
import com.iavariav.root.popmovie.Model.MovieModelBaru;
import com.iavariav.root.popmovie.R;
import com.iavariav.root.popmovie.Rest.ApiClient;
import com.iavariav.root.popmovie.Rest.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment {
    public PopularFragment() {
        // Required empty public constructor
    }
    private RecyclerView recycler;

    private ArrayList<MovieModelBaru> listPopuler;
    private ArrayList<ListMovieModel> listMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO digunakan untuk menampilkan view Fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        recycler = view.findViewById(R.id.recycler);

        listMovie = new ArrayList<>();
        listPopuler = new ArrayList<>();

//        // TODO Data Dummy
//        for (int i=0; i<20; i++){
//            MovieModel movieModel = new MovieModel("Film bagus",
//                    "http://demo.themeum.com/wordpress/moview/live-demo/assets/images/logo.png");
//            movieModels.add(movieModel);
//        }

        // TODO set Adapter
        MovieAdapter movieAdapter = new MovieAdapter(getActivity(), listMovie);
        recycler.setAdapter(movieAdapter);
        // TODO mengatur tampilan layout
        recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        // TODO Ambil daftar popular movie dari movie.db
        ambilDataOnline();

        return view;

    }

    private void ambilDataOnline() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mohon Tunggu Sebentar");
        progressDialog.show();

        ApiClient apiClient = Config.getApiClient();
        Call<MovieModelBaru> call = apiClient.ambilFilmPopuler();
        call.enqueue(new Callback<MovieModelBaru>() {
            @Override
            public void onResponse(Call<MovieModelBaru> call, Response<MovieModelBaru> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    listMovie = response.body().getResults();
                    MovieAdapter adapter = new MovieAdapter(getActivity(),listMovie);
                    recycler.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieModelBaru> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
