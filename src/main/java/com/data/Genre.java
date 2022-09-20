package com.data;

public class Genre {
    private int genre_id;
    private String genre_type;

    public Genre(int genre_id, String genre_type) {
        this.genre_id = genre_id;
        this.genre_type = genre_type;
    }

    public String getGenre_type() {
        return genre_type;
    }

    public void setGenre_type(String genre_type) {
        this.genre_type = genre_type;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

}
