package com.dao;

import com.data.Genre;
import com.data.Songs;
import com.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    public List<Genre> searchGenre() {
        List<Genre> genre = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM genre_tbl";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                genre.add(new Genre(resultSet.getInt(1),
                        resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genre;
    }
    public void displayGenre() {
        System.out.format("%-30s %-30s  %n", "Genre ID", "Genre Name");
        List<Genre> genre = searchGenre();
        for (Genre genre1 : genre) {
            {
                System.out.format("%-30s %-30s  %n", genre1.getGenre_id(), genre1.getGenre_type());
            }
        }
    }
    public List<Songs> genreSearchResult(int genreId) {
        List<Songs> genreSearchResult = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM songs_tbl WHERE genre_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, genreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                genreSearchResult.add(new Songs(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genreSearchResult;

    }
    public void displayGenreSongs(int num) {
        List<Songs> songsList = genreSearchResult(num);
        System.out.format("%-30s %-30s %-30s %-30s %n", "Song id", "Song name", "Duration", "Released year");
        for (Songs so : songsList) {
            System.out.format("%-30s %-30s %-30s %-30s %n", so.getSong_id(), so.getSong_name(), so.getDuration(), so.getRelease_year());
        }
    }
}
