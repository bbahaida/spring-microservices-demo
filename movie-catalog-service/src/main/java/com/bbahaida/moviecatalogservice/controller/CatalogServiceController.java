package com.bbahaida.moviecatalogservice.controller;

import com.bbahaida.moviecatalogservice.models.CatalogItem;
import com.bbahaida.moviecatalogservice.models.Movie;
import com.bbahaida.moviecatalogservice.models.Rating;
import com.bbahaida.moviecatalogservice.models.UserRating;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogServiceController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;


    @GetMapping("/{userId}")
    public ResponseEntity<List<CatalogItem>> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratings/" + userId, UserRating.class);


        if (userRating == null || userRating.getRatings() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Rating> ratings = userRating.getRatings();
        List<CatalogItem> catalogItems = ratings.stream()
                .map(rating -> {
                    // do the call using imperative programming using RestTemplate

                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);


                    // do the call using reactive programming using WebClient
                    /*
                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();
                    */

                    if (movie != null) {
                        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                catalogItems,
                HttpStatus.OK);
    }
}
