package com.davidmwangi.grpcflix.movie.service;

import com.davidmwangi.grpcflix.movie.MovieDto;
import com.davidmwangi.grpcflix.movie.MovieSearchRequest;
import com.davidmwangi.grpcflix.movie.MovieSearchResponse;
import com.davidmwangi.grpcflix.movie.MovieServiceGrpc;
import com.davidmwangi.grpcflix.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    private  MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList =  this.movieRepository.getMovieByGenreOrderByYearDesc(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getYear())
                        .setRating(movie.getRating())
                        .build()
                ).collect(Collectors.toList());

        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovie(movieDtoList).build());
        responseObserver.onCompleted();


    }
}
