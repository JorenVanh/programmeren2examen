package com.realdolmen.examen.examenprogrammeren2.repositories;

import com.realdolmen.examen.examenprogrammeren2.repositories.MovieRepository;
import com.realdolmen.examen.examenprogrammeren2.domain.Movie;
import com.realdolmen.examen.examenprogrammeren2.exceptions.NoQueryPossibleException;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.objects.NativeError;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MovieRepositoryTest {
    
    private static String URL = "jdbc:mysql://localhost:3306/movie_db_test?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    private MovieRepository repository;
    
    @Before
    public void init() {
        repository = new MovieRepository(URL);
    }
    
    @Test
    public void findTestSucces() throws NoQueryPossibleException {
        String query = "SELECT * FROM movies";
        System.out.println(query);
        //double check, just in case
        assertFalse(repository.find(query).isEmpty());
        assertNotNull(repository.find(query));
    }
    
    @Test(expected = NoQueryPossibleException.class)
    public void findTestWrongQueryThrowsNoQueryPossibleException() throws NoQueryPossibleException{
        String query = "SELECT tittell FROM movies";
        repository.find(query);
    }
    
}
