package com.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GenreDAOTest {
    GenreDAO genreDAO = null;
    @BeforeEach
    void setUp() {
        genreDAO = new GenreDAO();
    }

    @AfterEach
    void tearDown() {
        genreDAO = null;
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