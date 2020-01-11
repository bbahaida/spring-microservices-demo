package com.bbahaida.movieinfoservice.controller;

import com.bbahaida.movieinfoservice.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {
    Map<Long, Movie> movies = new HashMap<>();

    public MovieInfoController() {
        movies.put(1L, new Movie(1L, "Movie 1", "desc 1"));
        movies.put(2L, new Movie(2L, "Movie 2", "desc 2"));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable("movieId") Long movieId){
        return new ResponseEntity<>(movies.get(movieId), HttpStatus.OK);
    }
}
