package com.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JukeboxTest {
    ArtistDAO artistDAO = null;
    GenreDAO genreDAO = null;

    @BeforeEach
    void setUp() {
        artistDAO = new ArtistDAO();
        genreDAO = new GenreDAO();
    }

    @AfterEach
    void tearDown() {
        artistDAO = null;
        genreDAO = null;
    }

    @Test
    void searchArtist() throws SQLException {
        assertEquals(7, artistDAO.searchArtist().size());
        assertEquals("Sam", artistDAO.searchArtist().get(5).getArtist_name());
        assertNotEquals(5, artistDAO.searchArtist().size());
        assertNotEquals("Samantha", artistDAO.searchArtist().get(5).getArtist_name());
    }

    @Test
    void artistSearchResult() throws SQLException {
        assertEquals(2, artistDAO.artistSearchResult(2).size());
        assertEquals("Fight in Nowhereland", artistDAO.artistSearchResult(3).get(1).getSong_name());
        assertNotEquals(1, artistDAO.artistSearchResult(2).size());
        assertNotEquals("Drive", artistDAO.artistSearchResult(3).get(1).getSong_name());
    }

    @Test
    void searchGenre() {
        assertEquals(6, genreDAO.searchGenre().size());
        assertEquals("Pop", genreDAO.searchGenre().get(1).getGenre_type());
        assertNotEquals("Pop", genreDAO.searchGenre().get(2).getGenre_type());
        assertNotEquals(2, genreDAO.searchGenre().size());

    }

    @Test
    void genreSearchResult() {
        assertEquals(3, genreDAO.genreSearchResult(5).size());
        assertEquals("To the Moon", genreDAO.genreSearchResult(2).get(1).getSong_name());
        assertNotEquals(9, genreDAO.genreSearchResult(5).size());
        assertNotEquals("Brother", genreDAO.genreSearchResult(2).get(1).getSong_name());
    }

}
