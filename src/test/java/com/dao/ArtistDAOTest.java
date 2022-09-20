package com.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ArtistDAOTest {
    ArtistDAO artistDAO = null;
    @BeforeEach
    void setUp() {
        artistDAO = new ArtistDAO();
    }

    @AfterEach
    void tearDown() {
        artistDAO = null;
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

}