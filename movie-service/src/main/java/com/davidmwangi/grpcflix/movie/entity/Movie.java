package com.davidmwangi.grpcflix.movie.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    private Integer id;
    private String title;
    private int year;
    private  int rating;
    private String genre;
}
