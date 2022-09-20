package com.dao;

import com.data.Playlist;
import com.data.Songs;
import com.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistDAO {


    public static void displayPlaylists(List<Playlist> playlists) {
        System.out.format("%-30s %-30s\n", "Playlist Id", "Playlist Name");
        for (Playlist playlist : playlists) {
            System.out.format("%-30s %-30s\n", playlist.getPlaylistID(), playlist.getPlaylistName());
        }
    }

    public int createPlaylist() {
        int playlistId = 0;
        try {
            Scanner sc = new Scanner(System.in);
            Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO playlist_tbl (playlist_Name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            System.out.println("Enter Playlist Name");
            String name = sc.nextLine();

            preparedStatement.setString(1, name);
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Playlist created");
                    playlistId = resultSet.getInt(1);
                    System.out.println(playlistId);
                }
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return playlistId;
    }

    public void addSongInPlaylist(int playlistID, int songID) {

        try {
            Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO playlist_songs_tbl (playlist_id,songs_id) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, playlistID);
            preparedStatement.setInt(2, songID);
            preparedStatement.executeUpdate();
            System.out.println("Songs added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Songs> displaySongsFromPlaylist(int playlistID) {
        List<Songs> displaySongsList = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql1 = "SELECT playlist_id,playlist_Name FROM playlist_tbl";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultSet.getInt(1);
                resultSet.getString(2);
            }

            /*String sql2 = "SELECT songs_id FROM playlist_songs_tbl where playlist_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
            preparedStatement1.setInt(1,playlistID);
            ResultSet resultSet1 = preparedStatement1.executeQuery();*/

            String sql3 = "SELECT * FROM songs_tbl WHERE songs_id = (SELECT songs_id FROM playlist_songs_tbl where playlist_id = ?) ";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql3);
            preparedStatement1.setInt(1, playlistID);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            //while (resultSet.next()) {
            //int songId = resultSet1.getInt(1);
            //PreparedStatement preparedStatement2 = connection.prepareStatement(sql3);
            //preparedStatement2.setInt(1,songId);
            //ResultSet resultSet2 = preparedStatement2.executeQuery();

            while (resultSet1.next()) {
                    /*int songid = resultSet1.getInt("songs_id");
                    String songName = resultSet1.getString("song_Name");
                    String duration = resultSet1.getString("duration");
                    String year = resultSet1.getString("release_year");
                    System.out.println(songid+" "+songName+" "+duration+" "+year);*/
                displaySongsList.add(new Songs(resultSet1.getInt(1),
                        resultSet1.getString(2),
                        resultSet1.getInt(3),
                        resultSet1.getInt(4),
                        resultSet1.getString(5),
                        resultSet1.getInt(6),
                        resultSet1.getString(7)));
            }
            //}

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return displaySongsList;
    }

    public List<Playlist> displaySongsPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * from playlist_tbl";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               /* int playid = resultSet.getInt("playlist_id");
                String playName = resultSet.getString("playlist_Name");
                int sid = resultSet.getInt("songs_id");*/
                playlists.add(new Playlist(resultSet.getInt("playlist_id"),
                        resultSet.getString("playlist_Name")));
                //System.out.println(playid+" "+playName+" "+sid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public void displayPlaylistSongs(int playlistId) {
        List<Songs> songsList = displaySongsFromPlaylist(playlistId);
        System.out.format("%-30s %-30s %-30s %-30s %n", "Song id", "Song name", "Duration", "Released year");
        for (Songs so : songsList) {
            System.out.format("%-30s %-30s %-30s %-30s %n", so.getSong_id(), so.getSong_name(), so.getDuration(), so.getRelease_year());
        }
    }
}
