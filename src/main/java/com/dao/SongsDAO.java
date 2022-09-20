package com.dao;

import com.data.Songs;
import com.main.JukeboxPlayer;
import com.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongsDAO {

    public static void printSongList(List<Songs> songsList) {
        System.out.format("%-10s %-30s %-30s %-30s %-30s %-30s\n",
                "Song Id", "Song Name", "Genre Id", "Artist Id", "Duration", "Release Year");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Songs song : songsList) {
            System.out.format("%-10s %-30s %-30s %-30s %-30s %-30s\n",
                    song.getSong_id(), song.getSong_name(), song.getGenre_id(), song.getArtist_id(), song.getDuration(), song.getRelease_year());
        }
    }

    public void getAllSongs() {
        List<Songs> songsList = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM songs_tbl";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                songsList.add(new Songs(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)));
            }
            printSongList(songsList);

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    public List<Songs> readSearchByName(String name) {
        List<Songs> searchByName = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM songs_tbl WHERE song_Name = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Song not Found");

            } else {
                while (resultSet.next()) {
                    searchByName.add(new Songs(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getString(7)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchByName;

    }

    public void displaySearchByNameSongs(String songName) {
        List<Songs> songsList = readSearchByName(songName);
        if (!songsList.isEmpty()) {
            System.out.format("%-30s %-30s %-30s %-30s %n", "Song id", "Song name", "Duration", "Released year");
            for (Songs songs : songsList) {
                System.out.format("%-30s %-30s %-30s %-30s %n", songs.getSong_id(), songs.getSong_name(), songs.getDuration(), songs.getRelease_year());
                JukeboxPlayer jukeboxPlayer = new JukeboxPlayer();
                jukeboxPlayer.SongPlayFromDB();
                jukeboxPlayer.SongsPlay();
            }
        } else {
            System.out.println("Song name containing " + songName + " not exist.");

        }
    }
}
