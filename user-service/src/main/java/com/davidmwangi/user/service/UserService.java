package com.davidmwangi.user.service;

import com.davidmwangi.grpcflix.common.Genre;
import com.davidmwangi.grpcflix.user.UserGenreUpdateRequest;
import com.davidmwangi.grpcflix.user.UserResponse;
import com.davidmwangi.grpcflix.user.UserSearchRequest;
import com.davidmwangi.grpcflix.user.UserServiceGrpc;
import com.davidmwangi.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder=  UserResponse.newBuilder();
        System.out.println("Login Id :: " +  request.getLoginId());
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {

                    builder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder=  UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());

                    builder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toLowerCase()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
