package com.grpcflix.aggregator.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RecommendedMovie {
    private String title;
    private int year;
    private int rating;

    public RecommendedMovie(String title, int year, int rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }
}
