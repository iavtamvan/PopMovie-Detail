package com.iavariav.root.popmovie.Model;

/**
 * Created by root on 1/20/18.
 */

public class MovieModel {
    private String judulMovie;
    private String gambarMovie;

    //TODO buat Constructor

    public MovieModel(String judulMovie, String gambarMovie) {
        this.judulMovie = judulMovie;
        this.gambarMovie = gambarMovie;
    }

    // TODO buat Setter dan Getter
    public String getJudulMovie() {
        return judulMovie;
    }

    public void setJudulMovie(String judulMovie) {
        this.judulMovie = judulMovie;
    }

    public String getGambarMovie() {
        return gambarMovie;
    }

    public void setGambarMovie(String gambarMovie) {
        this.gambarMovie = gambarMovie;
    }
}
