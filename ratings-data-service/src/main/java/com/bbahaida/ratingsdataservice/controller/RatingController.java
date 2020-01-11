package com.bbahaida.ratingsdataservice.controller;

import com.bbahaida.ratingsdataservice.model.Rating;
import com.bbahaida.ratingsdataservice.model.UserRating;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    Map<String, List<Rating>> ratings = new HashMap<>();

    @PostConstruct
    public void init(){
        ratings.put("bbahaida", Arrays.asList(
                new Rating(1L, 3),
                new Rating(2L, 4)
        ));
        ratings.put("xyz", Collections.singletonList(new Rating(1L, 5)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRating> getRating(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(new UserRating(userId, ratings.get(userId)), HttpStatus.OK);
    }
}
