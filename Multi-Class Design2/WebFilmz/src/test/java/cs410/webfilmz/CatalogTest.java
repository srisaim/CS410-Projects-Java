package cs410.webfilmz;

/*
 *
 * ADD YOUR Catalog TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class CatalogTest {

    // An inner class example implementation of ILikeFilm interface for user preferences testing.
    public static class example implements ILikeFilm {
        private String likedGenre;

        public example(String likedGenre) {
            this.likedGenre = likedGenre;
        }

        // This is not needed for genre tests, so it always returns false.
        @Override
        public boolean isLikedDirector(String director) {
            return false;
        }

        // Checks if the user likes the given genre by comparing it to the user's preferred genre.
        @Override
        public boolean isLikedGenre(String genre) {
            return genre.equalsIgnoreCase(this.likedGenre);
        }
    }

    // Testing the recommendation function by genre.
    // This test will validate the functionality of the genre-based film recommendation system.
    // A Catalog object is created and three films with specific genres and ratings are added into it.
    // Assertions are made to check whether the correct number of SciFi and Romance films are recommended.
    // Below is creating a catalog and adding films with genres and ratings.
    @Test
    void testGetRecommendationsByGenre() {
        Catalog cat = new Catalog();
        cat.add("The Terminator", "James Cameron", "SciFi", 1984, Rating.R);
        cat.add("Titanic", "James Cameron", "Romance", 1997, Rating.PG13);
        cat.add("The Martian", "Ridley Scott", "SciFi", 2015, Rating.PG13);

        example exSciFi = new example("SciFi");
        Set<Film> sciFiRec = cat.getRecommendationsByGenre(exSciFi);
        assertEquals(2, sciFiRec.size(), "Recommendations should have 2 SciFi films.");

        example exRomance = new example("Romance");
        Set<Film> romanceRec = cat.getRecommendationsByGenre(exRomance);
        assertEquals(1, romanceRec.size(), "Recommendations should have 1 Romance film.");
    }

    // Testing the function’s behavior when requested for a genre that doesn't exist.
    // This test checks the recommendation system’s handling of non-existent genres.
    // A catalog is created with a film, then recommendations are requested for an unlisted genre.
    // Below is creating a catalog and adding a SciFi film with its rating.
    // Then it requests for recommendations for a non-existent genre (Horror).
    @Test
    void testGetRecommendationsByNonExistentGenre() {
        Catalog cat = new Catalog();
        cat.add("The Terminator", "James Cameron", "SciFi", 1984, Rating.R);

        example exHorror = new example("Horror");
        Set<Film> rec = cat.getRecommendationsByGenre(exHorror);
        assertTrue(rec.isEmpty(), "Recommendations should be empty for a genre that doesn't exist.");
    }

    // Testing how the function behaves when a catalog is empty.
    // An empty catalog is created and recommendations are requested, which then checks if it's correctly
    @Test
    void testGetRecommendationsByGenreWithNoFilms() {
        Catalog cat = new Catalog();

        example exSciFi = new example("SciFi");
        Set<Film> rec = cat.getRecommendationsByGenre(exSciFi);
        assertTrue(rec.isEmpty(), "Recommendations should be empty when there are no films in catalog.");
    }

    // Testing the case sensitivity of the genre in the recommendation function.
    // A Catalog is created with a film and recommendations are requested using a lowercase genre.
    // Verifying that the genre matching in recommendations is unaffected by text case.
    @Test
    void testGetRecommendationsByGenreCaseSensitivity() {
        Catalog cat = new Catalog();
        cat.add("The Terminator", "James Cameron", "SciFi", 1984, Rating.R);

        example exSciFi = new example("scifi");
        Set<Film> rec = cat.getRecommendationsByGenre(exSciFi);
        assertFalse(rec.isEmpty(), "Recommendations should not be case-sensitive.");
    }
}