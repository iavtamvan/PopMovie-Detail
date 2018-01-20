package com.iavariav.root.popmovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iavariav.root.popmovie.DetailMovieActivity;
import com.iavariav.root.popmovie.Model.ListMovieModel;
import com.iavariav.root.popmovie.Model.MovieModel;
import com.iavariav.root.popmovie.Model.MovieModelBaru;
import com.iavariav.root.popmovie.R;

import java.util.ArrayList;

/**
 * Created by root on 1/20/18.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context context ;
    //TODO 1
    private ArrayList<ListMovieModel> listPopuler;

    //    private ArrayList<MovieModel> listData;

    //TODO 2
    public MovieAdapter(Context context, ArrayList<ListMovieModel> listPopuler) {
        this.context = context;
        this.listPopuler = listPopuler;
    }

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdapter.MyViewHolder holder, final int position) {
        //TODO 3
        holder.tvJudulFilm.setText(listPopuler.get(position).getTitle());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+listPopuler.get(position).getPosterPath())
                .into(holder.ivGambarFilm);

        holder.ivGambarFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putParcelableArrayListExtra("list",listPopuler);
                i.putExtra("position",position);
                context.startActivity(i);
            }
        });
    }

    //TODO 4
    @Override
    public int getItemCount() {
        return listPopuler.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambarFilm;
        TextView tvJudulFilm;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGambarFilm = itemView.findViewById(R.id.iv_item_gambarfilm);
            tvJudulFilm = itemView.findViewById(R.id.tv_item_judulfilm);
        }
    }
}
