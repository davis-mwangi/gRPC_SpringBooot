package com.grpcflix.aggregator.service;

import com.davidmwangi.grpcflix.common.Genre;
import com.davidmwangi.grpcflix.movie.MovieSearchRequest;
import com.davidmwangi.grpcflix.movie.MovieSearchResponse;
import com.davidmwangi.grpcflix.movie.MovieServiceGrpc;
import com.davidmwangi.grpcflix.user.UserGenreUpdateRequest;
import com.davidmwangi.grpcflix.user.UserResponse;
import com.davidmwangi.grpcflix.user.UserSearchRequest;
import com.davidmwangi.grpcflix.user.UserServiceGrpc;
import com.grpcflix.aggregator.dtos.RecommendedMovie;
import com.grpcflix.aggregator.dtos.UserGenre;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId){
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);
        System.out.println("USER RESPONSE " +  userResponse);

        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse =  this.movieStub.getMovies(movieSearchRequest);

        return  movieSearchResponse.getMovieList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(),movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre){
        UserGenreUpdateRequest request = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();
        this.userStub.updateUserGenre(request);
    }
}
