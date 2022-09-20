package com.dao;

import com.data.Artist;
import com.data.Songs;
import com.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO {
    public List<Artist> searchArtist() throws SQLException {
        List<Artist> artistList = new ArrayList<>();
        Connection connection = DbConnection.getConnection();
        String sql = "SELECT * FROM artist_tbl";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            artistList.add(new Artist(resultSet.getInt(1),
                    resultSet.getString(2)));
        }

        return artistList;
    }

    public void displayArtist() throws SQLException {
        System.out.format("%-30s %-30s  %n", "Artist ID", "Artist name");
        List<Artist> artistList = searchArtist();
        for (Artist artist : artistList) {
            {
                System.out.format("%-30s %-30s  %n", artist.getArtist_id(), artist.getArtist_name());
            }
        }
    }

    public List<Songs> artistSearchResult(int artistId) throws SQLException {
        List<Songs> searchArtistResult = new ArrayList<>();

        Connection connection = DbConnection.getConnection();
        String sql = "SELECT * FROM songs_tbl WHERE artist_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, artistId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            searchArtistResult.add(new Songs(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7)));
        }
        return searchArtistResult;
    }

    public void displayArtistSongs(int artistId) throws SQLException {
        List<Songs> songsList = artistSearchResult(artistId);
        System.out.format("%-30s %-30s %-30s %-30s %n", "Song id", "Song name", "Duration", "Released year");
        for (Songs so : songsList) {
            System.out.format("%-30s %-30s %-30s %-30s %n", so.getSong_id(), so.getSong_name(), so.getDuration(), so.getRelease_year());
        }
    }

}
