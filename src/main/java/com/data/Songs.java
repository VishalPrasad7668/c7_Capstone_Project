package com.data;

public class Songs {
    private int song_id;
    private String song_name;
    private int genre_id;
    private int artist_id;
    private String duration;
    private int release_year;
    private String resource_path;

    public Songs() {
    }

    public Songs(int song_id, String song_name, int genre_id, int artist_id, String duration, int release_year, String resource_path) {
        this.song_id = song_id;
        this.song_name = song_name;
        this.genre_id = genre_id;
        this.artist_id = artist_id;
        this.duration = duration;
        this.release_year = release_year;
        this.resource_path = resource_path;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getResource_path() {
        return resource_path;
    }

    public void setResource_path(String resource_path) {
        this.resource_path = resource_path;
    }

}