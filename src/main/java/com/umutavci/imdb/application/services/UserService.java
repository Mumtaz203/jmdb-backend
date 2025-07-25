package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getSingle(Long id);
    List<UserResponse> getAll();
    UserResponse create(UserInput input);
    boolean delete(Long id);
    UserResponse update(Long id, UserInput input);


    List<MovieResponse> getWatchlist(Long userId);
    void addMovieToWatchlist(Long userId, Long movieId);
    void removeMovieFromWatchlist(Long userId, Long movieId);
}