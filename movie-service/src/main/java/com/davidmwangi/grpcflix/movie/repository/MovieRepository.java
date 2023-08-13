package com.davidmwangi.grpcflix.movie.repository;

import com.davidmwangi.grpcflix.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository  extends JpaRepository<Movie,Integer> {
    List<Movie>getMovieByGenreOrderByYearDesc(String genre);
}
