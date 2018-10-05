package com.realdolmen.examen.examenprogrammeren2.services;

import com.realdolmen.examen.examenprogrammeren2.exceptions.NoQueryPossibleException;
import com.realdolmen.examen.examenprogrammeren2.services.MovieService;
import com.realdolmen.examen.examenprogrammeren2.repositories.MovieRepository;
import com.realdolmen.examen.examenprogrammeren2.domain.Movie;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {
    
    private MovieService movieService;
    private List<Movie>movies;
    private Movie movieObjectToTest;

    @Mock
    private MovieRepository movieRepository;

    @Before
    public void init() {
        movieService = new MovieService(movieRepository);
        movies = new ArrayList<>();
        movieObjectToTest = new Movie(1,"comedy", "Ace ventura");
    }

    @Test
    public void findAllMoviesTest() throws Exception {
        List<Movie> result = new ArrayList<>();
        when(movieRepository.find("SELECT * FROM movies")).thenReturn(movies);
        movieService.findAllMovies();
        assertEquals(movies, result);
        verify(movieRepository, times(1)).find("SELECT * FROM movies");

    }

    @Test(expected = NoQueryPossibleException.class)
    public void findAllMoviesTestNoQueryPossibleExceptionThrow() throws Exception{
        when(movieRepository.find("SELECT * FROM movies")).thenThrow(NoQueryPossibleException.class);
        movieService.findAllMovies();
        verify(movieRepository, times(1)).find("SELECT * FROM movies");
    }

    @Test
    public void findMovieByIdTest() throws Exception {
        //init
        int id = 1;
        Movie movieReturned = new Movie();
        List<Movie> result = new ArrayList<>();
        movies.add(movieObjectToTest);
        when(movieRepository.find("SELECT * FROM movies WHERE id = 1")).thenReturn(movies);
        //test
        movieReturned = movieService.findMovieById(id);
        result.add(movieReturned);
        //verify
        assertEquals(movies, result);
        verify(movieRepository, times(1)).find("SELECT * FROM movies WHERE id = 1");
    }

    @Test
    public void getAllPalindromeTitlesTestTitleAddedToList() throws NoQueryPossibleException {
        List<String> palind;
        Movie palindrome1 = new Movie(3,"comedy", "boob");
        Movie palindrome2 = new Movie(4,"comedy", "aka");
        Movie palindrome3 = new Movie(5,"comedy", "dad");
        movies.add(palindrome1);
        movies.add(palindrome2);
        movies.add(palindrome3);
        List <String> movieTitles = new ArrayList<>();
        movieTitles.add("boob");
        movieTitles.add("aka");
        movieTitles.add("dad");
        
        when(movieRepository.find("SELECT * FROM movies")).thenReturn(movies);
        palind = movieService.getAllPalindromeTitles();
        assertEquals(movieTitles, palind);
        verify(movieRepository, times(1)).find("SELECT * FROM movies");
    }
   
    //TODO
    //niet helemaal juist vgm
    @Test//(expected = NoQueryPossibleException.class)
    public void getAllPalindromeTitlesTestNoQueryPossibleExceptionThrownAndCatchedTitleNotAdded() throws NoQueryPossibleException {
        List<String> palind = new ArrayList<>();
        when(movieRepository.find("SELECT * FROM movies")).thenThrow(NoQueryPossibleException.class);
        movies.add(movieObjectToTest);
        palind = movieService.getAllPalindromeTitles();
        assertNotEquals(movies, palind);
        verify(movieRepository, times(1)).find("SELECT * FROM movies");
        
    }
    
    @Test
    public void isAPalinDromeTestTrue() {
        assertTrue(movieService.isAPalindrome("saippuakivikauppias"));
    }

}
